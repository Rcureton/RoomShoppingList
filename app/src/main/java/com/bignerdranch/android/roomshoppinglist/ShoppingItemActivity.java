package com.bignerdranch.android.roomshoppinglist;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class ShoppingItemActivity extends SLSingleFragmentActivity {

    private static final String EXTRA_ITEM_ID = "com.bignerdranch.android.roomshoppinglist.item.id";

    public static Intent newIntent(Context context, int uuid) {
        Intent intent = new Intent(context, ShoppingItemActivity.class);
        intent.putExtra(EXTRA_ITEM_ID, uuid);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int uuid = (int) getIntent().getSerializableExtra(EXTRA_ITEM_ID);
        return ShoppingItemFragment.newInstance(uuid);
    }

}
