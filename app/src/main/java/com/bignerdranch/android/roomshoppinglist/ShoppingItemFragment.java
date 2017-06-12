package com.bignerdranch.android.roomshoppinglist;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.roomshoppinglist.database.ShoppingItem;
import com.bignerdranch.android.roomshoppinglist.databinding.FragmentShoppingItemBinding;

import java.util.Date;
import java.util.UUID;

public class ShoppingItemFragment extends LifecycleFragment {

    private static final String ARG_ITEM_ID = "item_id";

    private FragmentShoppingItemBinding mItemBinding;
    private ShoppingItem mShoppingItem;
    private Date mDate;
    private String mItemTitle;
    private String mStoreName;
    private ShoppingListViewModel mShoppingListViewModel;


    public static ShoppingItemFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ITEM_ID, id);

        ShoppingItemFragment fragment = new ShoppingItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShoppingListViewModel = ViewModelProviders.of(this)
                .get(ShoppingListViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mItemBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_shopping_item, container, false);

        mDate = new Date();

        mItemBinding.fragmentShoppingDateButton.setOnClickListener(v -> {

            mItemTitle = mItemBinding.fragmentShoppingEditText.getText()
                    .toString();
            mStoreName = mItemBinding.fragmentShoppingStoreEditText.getText()
                    .toString();

            mItemBinding.fragmentShoppingDateButton.setText(String.valueOf(mDate.getTime()));
        });

        mItemBinding.fragmentShoppingSaveButton.setOnClickListener(v -> {
            ShoppingItem shoppingItem = new ShoppingItem(UUID.randomUUID()
                    .hashCode(), mItemTitle, mStoreName, mDate);
            mShoppingListViewModel.addItem(shoppingItem);
            getActivity().finish();
        });

        return mItemBinding.getRoot();
    }

}
