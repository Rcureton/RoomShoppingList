package com.bignerdranch.android.roomshoppinglist;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class ShoppingItemActivity extends SLSingleFragmentActivity {

    private static final String EXTRA_ITEM_ID = "com.bignerdranch.android.roomshoppinglist.item.id";

    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, ShoppingItemActivity.class);
        intent.putExtra(EXTRA_ITEM_ID, id);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int uuid = (int) getIntent().getSerializableExtra(EXTRA_ITEM_ID);
        return ShoppingItemFragment.newInstance(uuid);
    }

}
