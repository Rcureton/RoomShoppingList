package com.bignerdranch.android.roomshoppinglist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.persistence.room.Room;

import com.bignerdranch.android.roomshoppinglist.database.AppDatabase;
import com.bignerdranch.android.roomshoppinglist.database.ShoppingItems;

import java.util.List;

public class ShoppingListViewModel extends AndroidViewModel {

    private List<ShoppingItems> mShoppingItems;
    private AppDatabase mDatabase;

    public ShoppingListViewModel(Application application) {
        super(application);

        createDb();

       mShoppingItems = mDatabase.shoppingItemsDao().getAllItems();
    }

    public List<ShoppingItems> getItems() {
        return mShoppingItems;
    }

    private void createDb() {
        mDatabase = Room.databaseBuilder(getApplication(), AppDatabase.class, "shopping-list").build();
    }
}
