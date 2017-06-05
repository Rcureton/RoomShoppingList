package com.bignerdranch.android.roomshoppinglist;

import android.databinding.DataBindingUtil;
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

import com.bignerdranch.android.roomshoppinglist.database.ShoppingItems;
import com.bignerdranch.android.roomshoppinglist.databinding.FragmentShoppingListBinding;
import com.bignerdranch.android.roomshoppinglist.databinding.ListShoppingItemBinding;

import java.util.List;

public class ShoppingListFragment extends Fragment {

    private FragmentShoppingListBinding mBinding;
    private ShoppingListViewModel mShoppingListViewModel;
    private ShoppingListAdapter mAdapter;

    public static ShoppingListFragment newInstance() {
        return new ShoppingListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //TODO: Have to fix this call I think it's database related
//        mShoppingListViewModel = ViewModelProviders.of(this).get(ShoppingListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_shopping_list, container, false);
        mBinding.shoppingListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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

        }
        return super.onOptionsItemSelected(item);

    }

    private void updateUI() {


        mBinding.shoppingListRecyclerView.setAdapter(mAdapter);

    }

    private static class ShoppingListItemHolder extends RecyclerView.ViewHolder {
        private ListShoppingItemBinding mItemBinding;
        private ShoppingItems mShoppingItems;

        public static ShoppingListItemHolder create(LayoutInflater inflater, ViewGroup parent) {
            ListShoppingItemBinding mBinding = ListShoppingItemBinding.inflate(inflater, parent, false);
            return new ShoppingListItemHolder(mBinding);
        }

        public ShoppingListItemHolder(ListShoppingItemBinding binding) {
            super(binding.getRoot());
            mItemBinding = binding;
        }


        public void bind(ShoppingItems shoppingItems) {
            mShoppingItems = shoppingItems;
            mItemBinding.listItemTitleTextView.setText(mShoppingItems.getItem());
            mItemBinding.listItemStoreTextView.setText(mShoppingItems.getStore());
            mItemBinding.listItemDateTextView.setText(mShoppingItems.getDate().toString());
            mItemBinding.listItemPurchasedCheckbox.setVisibility(mShoppingItems.isPurchased() ? View.VISIBLE : View.GONE);

        }
    }

    private class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListItemHolder> {
        private List<ShoppingItems> mShoppingItems;

        public ShoppingListAdapter(List<ShoppingItems> shoppingItems) {
            mShoppingItems = shoppingItems;
        }

        @Override
        public ShoppingListItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return ShoppingListItemHolder.create(LayoutInflater.from(viewGroup.getContext()), viewGroup);
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
}
