package com.bignerdranch.android.roomshoppinglist.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.bignerdranch.android.roomshoppinglist.database.dao.ShoppingItemsDao;

@Database(entities = {ShoppingItems.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ShoppingItemsDao shoppingItemsDao();
}
