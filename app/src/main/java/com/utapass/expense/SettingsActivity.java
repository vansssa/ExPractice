package com.utapass.expense;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

/**
 * Created by vanessatsai on 2017/9/28.
 */

public class SettingsActivity extends Activity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);



    }

    @Override
    protected void onStart() {
        super.onStart();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        SettingsFragment prefFragment = new SettingsFragment();
        transaction.add(android.R.id.content, prefFragment);
        transaction.commit();
    }


}
