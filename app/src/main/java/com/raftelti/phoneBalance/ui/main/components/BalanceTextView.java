package com.raftelti.phoneBalance.ui.main.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.raftelti.phoneBalance.data.BalanceRecord;
import com.raftelti.phoneBalance.utils.BalanceFormatter;

/**
 * Created by Rafael on 30/03/2015.
 */
public class BalanceTextView extends TextView {
    public BalanceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRecord(BalanceRecord record) {
        double value = record.getValue();
        String text = BalanceFormatter.formatBalance(value);
        setText(text);
    }
}
