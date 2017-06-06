package com.bignerdranch.android.roomshoppinglist.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.bignerdranch.android.roomshoppinglist.database.ShoppingItems;

import java.util.List;

@Dao
public interface ShoppingItemsDao {
    @Query("SELECT * FROM shoppingitems") List<ShoppingItems> getAllItems();

    @Query("SELECT * FROM shoppingItems WHERE purchased != 0") List<ShoppingItems> getPurchasedItems();

    @Insert
    void insertAll(ShoppingItems items);

    @Delete
    void delete(ShoppingItems items);
}
