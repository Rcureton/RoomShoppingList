package com.bignerdranch.android.roomshoppinglist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.persistence.room.Room;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.bignerdranch.android.roomshoppinglist.database.AppDatabase;
import com.bignerdranch.android.roomshoppinglist.database.ShoppingItem;

import java.util.List;

public class ShoppingListViewModel extends AndroidViewModel {

    private List<ShoppingItem> mShoppingItems;
    private AppDatabase mDatabase;

    public ShoppingListViewModel(Application application) {
        super(application);

        createDb();
       new DatabaseAsyc().execute();

        mShoppingItems = mDatabase.shoppingItemsDao()
                .getAllItems();
    }

    public List<ShoppingItem> getItems() {
        return mShoppingItems;
    }

    private void createDb() {
        Resources resources = getApplication().getResources();
        //TODO: Have to make this an Async process to get items out of the database
        mDatabase = Room.databaseBuilder(getApplication(), AppDatabase.class, resources.getString(R.string.database_name))
                .build();
    }

    public void addItem(ShoppingItem shoppingItem) {
        mDatabase.shoppingItemsDao().insertItems(shoppingItem);
    }

    private class DatabaseAsyc extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }
}
