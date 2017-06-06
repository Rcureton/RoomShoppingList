package com.bignerdranch.android.roomshoppinglist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bignerdranch.android.roomshoppinglist.database.ShoppingItems;
import com.bignerdranch.android.roomshoppinglist.databinding.ListShoppingItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingViewHolder> {

    private List<ShoppingItems> mShoppingItems;

    public ShoppingListAdapter() {
        mShoppingItems = new ArrayList<>();
    }

    public void setShoppingItems(List<ShoppingItems> shoppingItems) {
        mShoppingItems = shoppingItems;
        notifyDataSetChanged();
    }

    @Override
    public ShoppingViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return ShoppingViewHolder.create(LayoutInflater.from(viewGroup.getContext()), viewGroup);
    }

    @Override
    public void onBindViewHolder(ShoppingViewHolder shoppingViewHolder, int i) {
        shoppingViewHolder.onBind(mShoppingItems.get(i));
    }

    @Override
    public int getItemCount() {
        return mShoppingItems.size();
    }

    static class ShoppingViewHolder extends RecyclerView.ViewHolder {
        ListShoppingItemBinding mBinding;

        public static ShoppingViewHolder create(LayoutInflater inflater, ViewGroup parent) {
            ListShoppingItemBinding mBinding = ListShoppingItemBinding.inflate(inflater, parent, false);
            return new ShoppingViewHolder(mBinding);
        }

        public ShoppingViewHolder(ListShoppingItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;

        }

        public void onBind(ShoppingItems items) {
            
        }
    }
}
