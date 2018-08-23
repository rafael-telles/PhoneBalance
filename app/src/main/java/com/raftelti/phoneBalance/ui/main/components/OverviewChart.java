package com.raftelti.phoneBalance.ui.main.components;

import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.raftelti.phoneBalance.R;
import com.raftelti.phoneBalance.data.BalanceRecord;
import com.raftelti.phoneBalance.utils.BalanceFormatter;

import java.util.ArrayList;

/**
 * Created by Rafael on 30/03/2015.
 */
public class OverviewChart extends LineChart {

    public static final int RECORD_QUANTITY = 20;

    public OverviewChart(Context context, AttributeSet attrs) {
        super(context, attrs);

        setDescription("");
        setPinchZoom(false);
        setClickable(false);
        offsetLeftAndRight(10);

        setDrawGridBackground(false);

        XAxis xAxis = getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);
        xAxis.setDrawLabels(false);

        YAxis axisLeft = getAxisLeft();
        axisLeft.setDrawLabels(false);
        axisLeft.setDrawGridLines(false);

        YAxis axisRight = getAxisRight();
        axisRight.setDrawLabels(true);
        axisRight.setLabelCount(5);
        axisRight.setValueFormatter(new BalanceFormatter());
    }

    public void setCursor(Cursor cursor) {
        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<Entry> yVals = new ArrayList<>();

        int count = (cursor.getCount() > RECORD_QUANTITY ? RECORD_QUANTITY : cursor.getCount()) - 1;
        for (int i = count; i >= 0; i--) {
            xVals.add(i + "");
            if (cursor.moveToPosition(count - i)) {
                BalanceRecord record = BalanceRecord.getFromCursor(cursor);
                yVals.add(new BarEntry(((float) record.getValue()), i));
            }
        }

        LineDataSet dataSet = new LineDataSet(yVals, "");
        dataSet.setColor(getResources().getColor(R.color.primary));
        dataSet.setFillColor(getResources().getColor(R.color.primary));
        dataSet.setCircleColor(getResources().getColor(R.color.primary));
        dataSet.setLineWidth(3);

        dataSet.setDrawFilled(true);

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        LineData data = new LineData(xVals, dataSets);
        data.setValueTextSize(0f);

        setData(data);
        getLegend().setEnabled(false);
        invalidate();
    }
}
