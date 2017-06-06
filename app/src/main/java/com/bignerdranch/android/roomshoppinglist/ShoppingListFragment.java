package com.bignerdranch.android.roomshoppinglist;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.roomshoppinglist.databinding.FragmentShoppingListBinding;

public class ShoppingListFragment extends Fragment {

    private FragmentShoppingListBinding mBinding;
    private ShoppingListViewModel mShoppingListViewModel;

    public static ShoppingListFragment newInstance() {
        return new ShoppingListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: Have to fix this call I think it's database related
//        mShoppingListViewModel = ViewModelProviders.of(this).get(ShoppingListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_shopping_list, container, false);
        mBinding.shoppingListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.shoppingListRecyclerView.setAdapter(new ShoppingListAdapter());

        return mBinding.getRoot();
    }
}
