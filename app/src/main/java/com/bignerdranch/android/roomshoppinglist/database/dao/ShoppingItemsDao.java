package com.bignerdranch.android.roomshoppinglist.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bignerdranch.android.roomshoppinglist.database.ShoppingItems;

import java.util.List;

@Dao
public interface ShoppingItemsDao {
    @Query("SELECT * FROM shoppingitems")
    List<ShoppingItems> getAllItems();

    @Query("SELECT * FROM shoppingItems WHERE purchased != 0")
    List<ShoppingItems> getPurchasedItems();

//    @Query("SELECT * FROM shoppingItems WHERE Uuid=:Uuid")
//    ShoppingItems getItem(UUID uuid);

    @Update
    public void updateItem(ShoppingItems shoppingItems);

    @Insert
    public void insertItems(ShoppingItems items);

    @Delete
    public void delete(ShoppingItems items);

}
