package com.raftelti.phoneBalance.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Rafael on 02/04/2015.
 */
public class MainFragmentPageAdapter extends FragmentPagerAdapter {
    final private PageFragment[] mFragments = new PageFragment[]{new OverviewFragment(), new HistoryFragment()};
    private MainActivity mainActivity;

    public MainFragmentPageAdapter(MainActivity mainActivity) {
        super(mainActivity.getSupportFragmentManager());
        this.mainActivity = mainActivity;

        for (int i = 0; i < mFragments.length; i++) {
            mFragments[i].setPage(i);
        }
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments[position].getTitle(mainActivity);
    }
}
