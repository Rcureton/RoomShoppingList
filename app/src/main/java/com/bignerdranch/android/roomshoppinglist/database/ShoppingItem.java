package com.bignerdranch.android.roomshoppinglist.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

@Entity
@TypeConverters(DateConverter.class)
public class ShoppingItem {

    @PrimaryKey(autoGenerate = true) private int id;

    @ColumnInfo(name = "date") private Date date;

    @ColumnInfo(name = "item") private String item;

    @ColumnInfo(name = "store") private String store;

    @ColumnInfo(name = "purchased") private boolean purchased;

    public ShoppingItem() {

    }

    @Ignore
    public ShoppingItem(int id) {
        this.id = id;
    }

    @Ignore
    public ShoppingItem(int id, String item, String store, Date date) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
