package com.raftelti.phoneBalance.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public void setPage(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        setArguments(args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
        }
    }

    public CharSequence getTitle(Context context) {
        return "Tab " + mPage;
    }
}