package com.bignerdranch.android.roomshoppinglist;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class ShoppingListActivity extends SLSingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return ShoppingListFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
