package com.bignerdranch.android.roomshoppinglist;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.roomshoppinglist.database.AppDatabase;
import com.bignerdranch.android.roomshoppinglist.database.ShoppingItems;
import com.bignerdranch.android.roomshoppinglist.databinding.FragmentShoppingItemBinding;

import java.util.Date;
import java.util.UUID;

public class ShoppingItemFragment extends Fragment {

    private static final String ARG_ITEM_ID = "item_id";

    private FragmentShoppingItemBinding mItemBinding;
    private ShoppingItems mShoppingItems;
    private AppDatabase mDatabase;
    private Callbacks mCallbacks;
    private Date mDate;

    public interface Callbacks {
        void onItemUpdated(ShoppingItems shoppingItems);
    }

    public static ShoppingItemFragment newInstance(int uuid) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ITEM_ID, uuid);

        ShoppingItemFragment fragment = new ShoppingItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = Room.databaseBuilder(getContext(), AppDatabase.class, "shopping-list")
                .build();
//        UUID itemId = (UUID) getArguments().getSerializable(ARG_ITEM_ID);
//        mShoppingItems = mDatabase.shoppingItemsDao().getItem(itemId);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mItemBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_shopping_item, container, false);

        mDate = new Date();
        mItemBinding.fragmentShoppingDateButton.setOnClickListener(v -> {
            mItemBinding.fragmentShoppingDateButton.setText(String.valueOf(mDate.getTime()));
        });

//        mItemBinding.fragmentShoppingCheckBox.setChecked(mShoppingItems.isPurchased());
        mItemBinding.fragmentShoppingCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mShoppingItems.setPurchased(isChecked);
            updateItem();
        });

        mItemBinding.fragmentShoppingSaveButton.setOnClickListener(v -> {
            new DatabaseAsyc().execute();
            Intent intent = new Intent(getContext(), ShoppingListActivity.class);
            startActivity(intent);
        });

        return mItemBinding.getRoot();
    }

    private void updateItem() {
        mDatabase.shoppingItemsDao()
                .updateItem(mShoppingItems);
        mCallbacks.onItemUpdated(mShoppingItems);
    }


    private class DatabaseAsyc extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            ShoppingItems shoppingItems = new ShoppingItems();
            shoppingItems.setId(UUID.randomUUID()
                    .hashCode());
            shoppingItems.setItem(mItemBinding.fragmentShoppingEditText.getText()
                    .toString());
            shoppingItems.setStore(mItemBinding.fragmentShoppingStoreEditText.getText()
                    .toString());
            shoppingItems.setDate(mDate.toString());

            mDatabase.shoppingItemsDao()
                    .insertItems(shoppingItems);
            return null;
        }
    }
}
