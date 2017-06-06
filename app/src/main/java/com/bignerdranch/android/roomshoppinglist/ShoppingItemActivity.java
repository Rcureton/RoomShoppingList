package com.bignerdranch.android.roomshoppinglist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class ShoppingItemActivity extends SLSingleFragmentActivity {

    private static final String EXTRA_ITEM_ID = "com.bignerdranch.android.roomshoppinglist.item.id";

    public static Intent newIntent(Context context, int itemId) {
        Intent intent = new Intent(context, ShoppingItemActivity.class);
        intent.putExtra(EXTRA_ITEM_ID, itemId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int itemId = (int) getIntent().getSerializableExtra(EXTRA_ITEM_ID);
        return ShoppingItemFragment.newInstance(itemId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_item);
    }
}
