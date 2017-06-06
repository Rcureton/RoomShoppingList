package com.bignerdranch.android.roomshoppinglist;

import android.arch.persistence.room.Room;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.bignerdranch.android.roomshoppinglist.database.AppDatabase;
import com.bignerdranch.android.roomshoppinglist.database.ShoppingItems;
import com.bignerdranch.android.roomshoppinglist.databinding.FragmentShoppingItemBinding;

public class ShoppingItemFragment extends Fragment {

    private static final String ARG_ITEM_ID = "item_id";

    private FragmentShoppingItemBinding mItemBinding;
    private ShoppingItems mShoppingItems;
    private AppDatabase mDatabase;
    private Callbacks mCallbacks;

    public interface Callbacks {
        void onItemUpdated(ShoppingItems shoppingItems);
    }

    public static ShoppingItemFragment newInstance(int itemId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ITEM_ID, itemId);

        ShoppingItemFragment fragment = new ShoppingItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = Room.databaseBuilder(getContext(), AppDatabase.class, "shopping-list").build();
        int itemId = (int) getArguments().getSerializable(ARG_ITEM_ID);
//        mShoppingItems = mDatabase.shoppingItemsDao().getItem(itemId);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mItemBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_shopping_item, container, false);

        mItemBinding.fragmentShoppingTextView.setText(mShoppingItems.getItem());
        mItemBinding.fragmentShoppingEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mShoppingItems.setItem(s.toString());
                updateItem();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mItemBinding.fragmentShoppingCheckBox.setChecked(mShoppingItems.isPurchased());
        mItemBinding.fragmentShoppingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mShoppingItems.setPurchased(isChecked);
                updateItem();
            }
        });

        return mItemBinding.getRoot();
    }

    private void updateItem() {
        mDatabase.shoppingItemsDao().updateItem(mShoppingItems);
        mCallbacks.onItemUpdated(mShoppingItems);
    }
}
