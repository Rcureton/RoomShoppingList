package com.bignerdranch.android.roomshoppinglist;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.roomshoppinglist.database.AppDatabase;
import com.bignerdranch.android.roomshoppinglist.database.ShoppingItem;
import com.bignerdranch.android.roomshoppinglist.databinding.FragmentShoppingListBinding;
import com.bignerdranch.android.roomshoppinglist.databinding.ListShoppingItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListFragment extends Fragment {

    private FragmentShoppingListBinding mBinding;
    private ShoppingListAdapter mAdapter;
    private AppDatabase mDatabase;
    private List<ShoppingItem> mShoppingItems;


    public static ShoppingListFragment newInstance() {
        return new ShoppingListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mDatabase = Room.databaseBuilder(getContext(), AppDatabase.class, getString(R.string.database_name))
                .build();
        mShoppingItems = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_shopping_list, container, false);
        mBinding.shoppingListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateUI();

        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        new DatabaseAsyc().execute();

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_shopping, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_shopping_item:
                ShoppingItem shoppingItem = new ShoppingItem();
                Intent intent = ShoppingItemActivity.newIntent(getContext(), shoppingItem.getId());
                startActivity(intent);
                updateUI();
        }
        return super.onOptionsItemSelected(item);

    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new ShoppingListAdapter(mShoppingItems);
        } else {
            mAdapter.setShoppingItems(mShoppingItems);
            mAdapter.notifyDataSetChanged();
        }

        mBinding.shoppingListRecyclerView.setAdapter(mAdapter);
    }

    private class ShoppingListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ListShoppingItemBinding mItemBinding;
        private ShoppingItem mShoppingItem;

        public ShoppingListItemHolder(ListShoppingItemBinding binding) {
            super(binding.getRoot());
            mItemBinding = binding;
        }

        public void bind(ShoppingItem shoppingItem) {
            mShoppingItem = shoppingItem;
            mItemBinding.listItemTitleTextView.setText(mShoppingItem.getItem());
            mItemBinding.listItemStoreTextView.setText(mShoppingItem.getStore());
            mItemBinding.listItemDateTextView.setText(mShoppingItem.getDate().toString());
            mItemBinding.listItemPurchasedCheckbox.setVisibility(mShoppingItem.isPurchased() ? View.VISIBLE : View.GONE);

        }

        @Override
        public void onClick(View v) {
            Intent intent = ShoppingItemActivity.newIntent(getActivity(), mShoppingItem.getId());
            startActivity(intent);
        }
    }

    private class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListItemHolder> {
        private List<ShoppingItem> mShoppingItems;

        public ShoppingListAdapter(List<ShoppingItem> shoppingItems) {
            mShoppingItems = shoppingItems;
        }

        @Override
        public ShoppingListItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            ListShoppingItemBinding binding = ListShoppingItemBinding.inflate(inflater, viewGroup, false);
            return new ShoppingListItemHolder(binding);
        }

        @Override
        public void onBindViewHolder(ShoppingListItemHolder shoppingListItemHolder, int i) {
            ShoppingItem shoppingItem = mShoppingItems.get(i);
            shoppingListItemHolder.bind(shoppingItem);
        }

        @Override
        public int getItemCount() {
            return mShoppingItems.size();
        }

        public void setShoppingItems(List<ShoppingItem> items) {
            mShoppingItems = items;
        }
    }

    private class DatabaseAsyc extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            mShoppingItems = mDatabase.shoppingItemsDao()
                    .getAllItems();

            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            updateUI();
        }
    }
}
