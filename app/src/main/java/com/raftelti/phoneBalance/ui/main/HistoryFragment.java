package com.raftelti.phoneBalance.ui.main;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.raftelti.phoneBalance.R;
import com.raftelti.phoneBalance.data.BalanceContract;
import com.raftelti.phoneBalance.utils.UiUtils;

public class HistoryFragment extends PageFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 1;
    private static final String INDEX_KEY = "index";
    private static final String TOP_KEY = "top";
    private ListView mHistoryListView;
    private BalanceListAdapter mAdapter;
    private int savedIndex;
    private int savedTop;
    private View contentView;
    private View emptyView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        contentView = rootView.findViewById(R.id.history_content);
        emptyView = rootView.findViewById(R.id.history_empty);

        mHistoryListView = (ListView) rootView.findViewById(R.id.history_listview);

        UiUtils.setListViewGlowColor(getActivity(), getActivity().getResources().getColor(R.color.primary));

        return rootView;
    }

    @Override
    public CharSequence getTitle(Context context) {
        return context.getResources().getString(R.string.title_history_tab);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new BalanceListAdapter(getActivity());
        mHistoryListView.setAdapter(mAdapter);

        getLoaderManager().initLoader(LOADER_ID, null, this);

        if (savedInstanceState != null && savedInstanceState.containsKey(INDEX_KEY)) {
            savedIndex = savedInstanceState.getInt(INDEX_KEY);
            savedTop = savedInstanceState.getInt(TOP_KEY);
            mHistoryListView.clearFocus();

            //mHistoryListView.setSelection(savedIndex);
            mHistoryListView.setSelectionFromTop(savedIndex, savedTop);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = BalanceContract.BalanceEntry.CONTENT_URI;
        return new CursorLoader(
                getActivity(),
                uri,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data.getCount() > 0) {
            mAdapter.swapCursor(data);
            mHistoryListView.setSelectionFromTop(savedIndex, savedTop);

            contentView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.INVISIBLE);
        } else {
            contentView.setVisibility(View.INVISIBLE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(INDEX_KEY, mHistoryListView.getFirstVisiblePosition());
        View v = mHistoryListView.getChildAt(0);
        outState.putInt(TOP_KEY, (v == null) ? 0 : (v.getTop() - mHistoryListView.getPaddingTop()));

        super.onSaveInstanceState(outState);
    }
}