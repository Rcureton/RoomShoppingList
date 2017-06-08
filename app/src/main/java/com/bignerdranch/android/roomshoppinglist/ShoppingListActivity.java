package com.bignerdranch.android.roomshoppinglist;

import android.support.v4.app.Fragment;

public class ShoppingListActivity extends SLSingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return ShoppingListFragment.newInstance();
    }

}
