package com.bignerdranch.android.roomshoppinglist.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.bignerdranch.android.roomshoppinglist.database.dao.ShoppingItemsDao;

@Database(entities = {ShoppingItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sAppDatabase;
    public abstract ShoppingItemsDao shoppingItemsDao();

    public static AppDatabase getDatabase(Context context) {
        if (sAppDatabase == null) {
            sAppDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "shopping-list").build();
        }
        return sAppDatabase;
    }
}
