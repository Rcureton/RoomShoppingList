package com.bignerdranch.android.roomshoppinglist.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bignerdranch.android.roomshoppinglist.database.ShoppingItem;

import java.util.List;

@Dao
public interface ShoppingItemsDao {
    @Query("SELECT * FROM shoppingitem")
    List<ShoppingItem> getAllItems();

    @Query("SELECT * FROM shoppingitem WHERE purchased != 0")
    List<ShoppingItem> getPurchasedItems();

    @Query("SELECT * FROM shoppingitem WHERE id=:id")
    ShoppingItem getItem(int id);

    @Update
    public void updateItem(ShoppingItem shoppingItem);

    @Insert
    public void insertItems(ShoppingItem items);

    @Delete
    public void delete(ShoppingItem items);

}
