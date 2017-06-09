package com.bignerdranch.android.roomshoppinglist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.bignerdranch.android.roomshoppinglist.database.AppDatabase;
import com.bignerdranch.android.roomshoppinglist.database.ShoppingItem;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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

    public void addItem(ShoppingItem shoppingItem) {
        new AddItemAsyncTask(mDatabase).execute(shoppingItem);
    }

    public void deleteItem(ShoppingItem shoppingItem) {
        new DeleteItemAsyncTask(mDatabase).execute(shoppingItem);
    }

    private class AddItemAsyncTask extends AsyncTask<ShoppingItem, Void, Void> {
        private AppDatabase mDatabase;

        public AddItemAsyncTask(AppDatabase appDatabase) {
            mDatabase = appDatabase;
        }

        @Override
        protected Void doInBackground(ShoppingItem... shoppingItems) {
            mDatabase.shoppingItemsDao().insertItems(shoppingItems[0]);
            return null;
        }
    }

    private class DeleteItemAsyncTask extends AsyncTask<ShoppingItem, Void, Void> {

        private AppDatabase mDatabase;

        public DeleteItemAsyncTask(AppDatabase appDatabase) {
            mDatabase = appDatabase;
        }
        @Override
        protected Void doInBackground(ShoppingItem... shoppingItems) {
            mDatabase.shoppingItemsDao().delete(shoppingItems[0]);
            return null;
        }
    }
}
