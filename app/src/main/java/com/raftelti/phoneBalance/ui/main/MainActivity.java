package com.raftelti.phoneBalance.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;
import com.raftelti.phoneBalance.R;
import com.raftelti.phoneBalance.carriers.Carrier;
import com.raftelti.phoneBalance.carriers.CarrierFactory;
import com.raftelti.phoneBalance.core.BalanceManager;
import com.raftelti.phoneBalance.receivers.AlarmReceiver;
import com.raftelti.phoneBalance.ui.about.AboutActivity;
import com.raftelti.phoneBalance.ui.dialogs.FirstStartDialog;
import com.raftelti.phoneBalance.ui.settings.SettingsActivity;
import com.raftelti.phoneBalance.utils.DeviceUtils;
import com.raftelti.phoneBalance.utils.TelephonyUtils;
import com.raftelti.phoneBalance.utils.UiUtils;
import com.raftelti.phoneBalance.utils.preferences.Prefs;

import java.util.List;
import java.util.Locale;

public class MainActivity extends ActionBarActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();

        if (DeviceUtils.isTablet(this)) {
            setupForLargeScreen();
        } else {
            setupForSmallScreen();
        }

        if (savedInstanceState == null) {
            boolean telephony = checkTelephony();
            if (telephony) {
                checkFirstStart();
            }
        }
    }

    private boolean checkFirstStart() {
        Boolean firstStart = Prefs.firstStart.get();
        if (firstStart) {
            CarrierFactory carrierFactory = new CarrierFactory(this);
            List<Carrier> carriers = carrierFactory.getList();
            if (carriers.size() > 0) {
                carriers.get(0).applyPreferences(this);
            }

            FirstStartDialog.show(this);
            Prefs.firstStart.set(false);
        } else {
            AlarmReceiver.startAlarm(this);
        }

        return firstStart;
    }

    private boolean checkTelephony() {
        boolean telephony = TelephonyUtils.checkAvaliability(this);
        if (!telephony) {
            MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
            builder
                    .title(getString(R.string.no_telephony_dialog_title))
                    .content(getString(R.string.no_telephony_dialog_content))
                    .iconRes(R.mipmap.ic_launcher)
                    .positiveText(android.R.string.ok)
                    .show();
        }

        return telephony;
    }

    private void setupForLargeScreen() {
        getSupportFragmentManager().beginTransaction().replace(
                R.id.overview_fragment,
                new OverviewFragment()
        ).commit();
        getSupportFragmentManager().beginTransaction().replace(
                R.id.history_fragment,
                new HistoryFragment()
        ).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                openSettings();
                break;
            case R.id.action_refresh:
                BalanceManager.get().requestUpdate();
                //BalanceManager.get().saveRecord(String.format(Locale.US, "Seu saldo é R$%.2f. Expira em 01/01/2016.", Math.random() * 10));
                break;
            case R.id.action_about:
                openAbout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);

        mToolbar.setTitle(getString(R.string.app_name));
        UiUtils.applyToolbarPadding(this, mToolbar);
    }

    private void setupForSmallScreen() {
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mViewPager.setAdapter(new MainFragmentPageAdapter(this));

        SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.accent);
            }
        });
    }

    private void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void openAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

}
