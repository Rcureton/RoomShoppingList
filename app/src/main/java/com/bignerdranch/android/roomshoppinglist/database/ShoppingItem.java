package com.bignerdranch.android.roomshoppinglist.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ShoppingItem {

    @PrimaryKey private int id;

    @ColumnInfo(name = "date") private String date;

    @ColumnInfo(name = "item") private String item;

    @ColumnInfo(name = "store") private String store;

    @ColumnInfo(name = "purchased") private boolean purchased;

    public ShoppingItem() {
    }

    @Ignore
    public ShoppingItem(int id, String item, String store, String date) {
        this.id = id;
        this.item = item;
        this.store = store;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;

    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
}