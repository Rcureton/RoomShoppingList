package com.bignerdranch.android.roomshoppinglist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.bignerdranch.android.roomshoppinglist.database.AppDatabase;
import com.bignerdranch.android.roomshoppinglist.database.ShoppingItem;

import java.util.List;

public class ShoppingListViewModel extends AndroidViewModel {

    private LiveData<List<ShoppingItem>> mShoppingItems;
    private AppDatabase mDatabase;

    public ShoppingListViewModel(Application application) {
        super(application);
        mDatabase = AppDatabase.getDatabase(this.getApplication());
        mShoppingItems = mDatabase.shoppingItemsDao().getAllItems();
    }

    public LiveData<List<ShoppingItem>> getItems() {
        return mShoppingItems;
    }

    public void deleteItem(ShoppingItem shoppingItem) {

    }
}
