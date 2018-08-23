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
import android.widget.TextView;

import com.raftelti.phoneBalance.R;
import com.raftelti.phoneBalance.data.BalanceContract;
import com.raftelti.phoneBalance.data.BalanceRecord;
import com.raftelti.phoneBalance.ui.main.components.BalanceTextView;
import com.raftelti.phoneBalance.ui.main.components.DifferenceTextView;
import com.raftelti.phoneBalance.ui.main.components.OverviewChart;
import com.raftelti.phoneBalance.ui.main.components.TimestampTextView;
import com.raftelti.phoneBalance.utils.preferences.Prefs;

public class OverviewFragment extends PageFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 0;

    private BalanceTextView balanceView;
    private DifferenceTextView differenceView;
    private TimestampTextView lastUpdateView;
    private TimestampTextView lastVerificationView;
    private OverviewChart mChart;

    private BalanceRecord mRecord;
    private TextView balanceDescView;
    private View contentView;
    private View emptyView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_overview, container, false);


        contentView = mRootView.findViewById(R.id.overview_content);
        emptyView = mRootView.findViewById(R.id.overview_empty);

        balanceView = (BalanceTextView) mRootView.findViewById(R.id.current_balance);
        differenceView = (DifferenceTextView) mRootView.findViewById(R.id.difference);
        lastUpdateView = (TimestampTextView) mRootView.findViewById(R.id.last_update);
        lastVerificationView = (TimestampTextView) mRootView.findViewById(R.id.last_verification);
        balanceDescView = ((TextView) mRootView.findViewById(R.id.current_balance_desc));

        lastUpdateView.setFormat(getResources().getString(R.string.last_update_prefix) + " %s");
        lastVerificationView.setFormat(getResources().getString(R.string.last_verification_prefix) + " %s");

        mChart = (OverviewChart) mRootView.findViewById(R.id.chart);
        mChart.setVisibility(View.INVISIBLE);
        return mRootView;
    }

    @Override
    public CharSequence getTitle(Context context) {
        return context.getResources().getString(R.string.title_overview_tab);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(LOADER_ID, null, this);
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
        if (data.getCount() > 0) {
            data.moveToFirst();
            mRecord = BalanceRecord.getFromCursor(data);

            balanceDescView.setText(getString(R.string.balance_presentation));
            balanceView.setRecord(mRecord);
            differenceView.setRecord(mRecord);

            lastUpdateView.setRecord(mRecord);
            Long time = Prefs.lastRequestTimestamp.get();
            if (time > 0) {
                lastVerificationView.setTimestamp(time);
            }

            if (data.getCount() > 1) {
                mChart.setCursor(data);
                mChart.setVisibility(View.VISIBLE);
            }

            emptyView.setVisibility(View.INVISIBLE);
            contentView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.VISIBLE);
            contentView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
