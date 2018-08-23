package com.raftelti.phoneBalance.ui.settings;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.raftelti.phoneBalance.App;
import com.raftelti.phoneBalance.R;
import com.raftelti.phoneBalance.utils.UiUtils;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SettingsFragment mSettingsFragment = new SettingsFragment();
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, mSettingsFragment)
                    .commit();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        UiUtils.applyToolbarPadding(this, toolbar);
    }

    @Override
    protected void onDestroy() {
        App.runningTutorial = false;
        super.onDestroy();
    }
}
