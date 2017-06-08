package com.bignerdranch.android.roomshoppinglist;

import android.arch.persistence.room.Room;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.roomshoppinglist.database.AppDatabase;
import com.bignerdranch.android.roomshoppinglist.database.ShoppingItem;
import com.bignerdranch.android.roomshoppinglist.databinding.FragmentShoppingItemBinding;

import java.util.Date;
import java.util.UUID;

public class ShoppingItemFragment extends Fragment {

    private static final String ARG_ITEM_ID = "item_id";

    private FragmentShoppingItemBinding mItemBinding;
    private ShoppingItem mShoppingItem;
    private AppDatabase mDatabase;
    private Date mDate;
    private String mItemTitle;
    private String mStoreName;


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
        mDatabase = Room.databaseBuilder(getContext(), AppDatabase.class, getString(R.string.database_name))
                .build();
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

        mItemBinding.fragmentShoppingCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mShoppingItem.setPurchased(isChecked);
            updateItem();
        });

        mItemBinding.fragmentShoppingSaveButton.setOnClickListener(v -> {
            new DatabaseAsyc().execute();
            getActivity().finish();
        });

        return mItemBinding.getRoot();
    }

    private void updateItem() {
        mDatabase.shoppingItemsDao()
                .updateItem(mShoppingItem);
    }

    private class DatabaseAsyc extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            ShoppingItem shoppingItem = new ShoppingItem(UUID.randomUUID()
                    .hashCode(), mItemTitle, mStoreName, mDate.toString());

            mDatabase.shoppingItemsDao()
                    .insertItems(shoppingItem);
            return null;
        }
    }
}
