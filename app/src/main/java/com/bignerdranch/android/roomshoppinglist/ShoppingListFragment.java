package com.bignerdranch.android.roomshoppinglist;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class ShoppingListFragment extends LifecycleFragment {

    private FragmentShoppingListBinding mBinding;
    private ShoppingListAdapter mAdapter;
    private List<ShoppingItem> mShoppingItems = new ArrayList<>();
    private ShoppingListViewModel mShoppingListViewModel;

    private View.OnClickListener deleteClickListener = v -> {
        ShoppingItem shoppingItem = (ShoppingItem) v.getTag();
        mShoppingListViewModel.deleteItem(shoppingItem);
    };

    public static ShoppingListFragment newInstance() {
        return new ShoppingListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_shopping_list, container, false);
        mBinding.shoppingListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ShoppingListAdapter(new ArrayList<>());
        mBinding.shoppingListRecyclerView.setAdapter(mAdapter);

        mShoppingListViewModel = ViewModelProviders.of(this)
                .get(ShoppingListViewModel.class);
        mShoppingListViewModel.getItems()
                .observe(this, items -> {
                    Log.d("EVENT", "list #" + items);
                    mAdapter.setShoppingItems(items);
                });

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
                ShoppingItem shoppingItem = new ShoppingItem();
                Intent intent = ShoppingItemActivity.newIntent(getContext(), shoppingItem.getId());
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }

    private class ShoppingListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ListShoppingItemBinding mItemBinding;
        private ShoppingItem mShoppingItem;

        public ShoppingListItemHolder(ListShoppingItemBinding binding) {
            super(binding.getRoot());
            itemView.setOnClickListener(this);

            mItemBinding = binding;
        }

        public void bind(ShoppingItem shoppingItem) {
            mShoppingItem = shoppingItem;
            mItemBinding.listItemTitleTextView.setText(mShoppingItem.getItem());
            mItemBinding.listItemStoreTextView.setText(mShoppingItem.getStore());
            mItemBinding.listItemDateTextView.setText(mShoppingItem.getDate()
                    .toString());
        }

        @Override
        public void onClick(View v) {
            Intent intent = ShoppingItemActivity.newIntent(getActivity(), mShoppingItem.getId());
            startActivity(intent);
        }
    }

    private class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListItemHolder> {
        private List<ShoppingItem> mShoppingItems;
        private ListShoppingItemBinding mItemBinding;

        public ShoppingListAdapter(List<ShoppingItem> shoppingItems) {
            mShoppingItems = shoppingItems;
        }

        @Override
        public ShoppingListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            ListShoppingItemBinding binding = DataBindingUtil.inflate(inflater,
                    R.layout.list_shopping_item,
                    parent,
                    false);
            return new ShoppingListItemHolder(binding);
        }

        @Override
        public void onBindViewHolder(ShoppingListItemHolder shoppingListItemHolder, int i) {
            ShoppingItem shoppingItem = mShoppingItems.get(i);
            shoppingListItemHolder.mItemBinding.listItemDeleteButton.setTag(shoppingItem);
            shoppingListItemHolder.mItemBinding.listItemDeleteButton.setOnClickListener(deleteClickListener);
            shoppingListItemHolder.bind(shoppingItem);
        }

        @Override
        public int getItemCount() {
            return mShoppingItems.size();
        }

        public void setShoppingItems(List<ShoppingItem> items) {
            mShoppingItems.clear();
            mShoppingItems.addAll(items);
            notifyDataSetChanged();
        }
    }

}
