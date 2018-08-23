package com.raftelti.phoneBalance.ui.main;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.raftelti.phoneBalance.R;
import com.raftelti.phoneBalance.data.BalanceRecord;
import com.raftelti.phoneBalance.ui.main.components.BalanceTextView;
import com.raftelti.phoneBalance.ui.main.components.DifferenceTextView;
import com.raftelti.phoneBalance.ui.main.components.TimestampTextView;

public class BalanceListAdapter extends CursorAdapter {

    public BalanceListAdapter(Context context) {
        super(context, null, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_list_item, parent, false);
        ViewHolder.setup(view);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final BalanceRecord record = BalanceRecord.getFromCursor(cursor);
        final ViewHolder holder = ((ViewHolder) view.getTag(R.id.history_item_viewholder));

        holder.setRecord(record);
    }

    static class ViewHolder implements View.OnClickListener {
        private final View mView;
        BalanceTextView balanceView;
        DifferenceTextView differenceView;
        TimestampTextView timestampView;
        private BalanceRecord mRecord;

        public ViewHolder(View view) {
            mView = view;
        }

        static void setup(View view) {
            ViewHolder holder = new ViewHolder(view);
            holder.balanceView = (BalanceTextView) view.findViewById(R.id.list_item_balance);
            holder.differenceView = (DifferenceTextView) view.findViewById(R.id.list_item_difference);
            holder.timestampView = (TimestampTextView) view.findViewById(R.id.list_item_timestamp);

            view.setOnClickListener(holder);
            view.setTag(R.id.history_item_viewholder, holder);
        }

        void setRecord(final BalanceRecord record) {
            mRecord = record;

            differenceView.setRecord(record);
            balanceView.setRecord(record);
            timestampView.setRecord(record);
        }

        @Override
        public void onClick(final View v) {
            final Context context = v.getContext();
            MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
            builder
                    .title(mRecord.getFormattedValue())
                    .content(mRecord.getMessage())
                    .negativeText(R.string.delete)
                    .negativeColor(context.getResources().getColor(R.color.balance_down))
                    .positiveText(android.R.string.ok)
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            super.onPositive(dialog);
                        }

                        @Override
                        public void onNegative(MaterialDialog dialog) {
                            mRecord.delete(context);
                        }
                    })
                    .show();
        }
    }

}