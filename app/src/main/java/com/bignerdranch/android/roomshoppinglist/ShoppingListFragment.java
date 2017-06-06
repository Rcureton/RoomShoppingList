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
import com.bignerdranch.android.roomshoppinglist.database.ShoppingItems;
import com.bignerdranch.android.roomshoppinglist.databinding.FragmentShoppingListBinding;
import com.bignerdranch.android.roomshoppinglist.databinding.ListShoppingItemBinding;

import java.util.List;
import java.util.UUID;

public class ShoppingListFragment extends Fragment {

    private FragmentShoppingListBinding mBinding;
    private ShoppingListAdapter mAdapter;
    private AppDatabase mDatabase;
    private List<ShoppingItems> mShoppingItems;


    public static ShoppingListFragment newInstance() {
        return new ShoppingListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mDatabase = Room.databaseBuilder(getContext(), AppDatabase.class, "shopping-list")
                .build();

        new DatabaseAsyc().execute();

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_shopping, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_shopping_item:
                ShoppingItems shoppingItems = new ShoppingItems();
                Intent intent = ShoppingItemActivity.newIntent(getContext(), shoppingItems.getId());
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

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


    private class ShoppingListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ListShoppingItemBinding mItemBinding;
        private ShoppingItems mShoppingItems;

        public ShoppingListItemHolder(ListShoppingItemBinding binding) {
            super(binding.getRoot());
            mItemBinding = binding;
        }


        public void bind(ShoppingItems shoppingItems) {
            mShoppingItems = shoppingItems;
            mItemBinding.listItemTitleTextView.setText(mShoppingItems.getItem());
            mItemBinding.listItemStoreTextView.setText(mShoppingItems.getStore());
            mItemBinding.listItemDateTextView.setText(mShoppingItems.getDate());
            mItemBinding.listItemPurchasedCheckbox.setVisibility(mShoppingItems.isPurchased() ? View.VISIBLE : View.GONE);

        }

        @Override
        public void onClick(View v) {
            Intent intent = ShoppingItemActivity.newIntent(getActivity(), mShoppingItems.getId());
            startActivity(intent);
        }
    }

    private class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListItemHolder> {
        private List<ShoppingItems> mShoppingItems;

        public ShoppingListAdapter(List<ShoppingItems> shoppingItems) {
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
            ShoppingItems shoppingItems = mShoppingItems.get(i);
            shoppingListItemHolder.bind(shoppingItems);
        }

        @Override
        public int getItemCount() {
            return mShoppingItems.size();
        }

        public void setShoppingItems(List<ShoppingItems> items) {
            mShoppingItems = items;
        }
    }

    private class DatabaseAsyc extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ShoppingItems shoppingItems = new ShoppingItems();
            shoppingItems.setId(UUID.randomUUID()
                    .hashCode());
            shoppingItems.setItem("Deodorant");
            shoppingItems.setStore("Target");
            shoppingItems.setDate("June 6th 2017");

            mDatabase.shoppingItemsDao()
                    .insertItems(shoppingItems);
            mShoppingItems = mDatabase.shoppingItemsDao()
                    .getAllItems();
            return null;
        }
    }
}
