package com.raftelti.phoneBalance.ui.main.components;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.raftelti.phoneBalance.R;
import com.raftelti.phoneBalance.data.BalanceRecord;
import com.raftelti.phoneBalance.utils.BalanceFormatter;

/**
 * Created by Rafael on 30/03/2015.
 */
public class DifferenceTextView extends TextView {
    public DifferenceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRecord(BalanceRecord record) {
        double difference = record.getDifference();
        if (difference == 0) {
            setVisibility(INVISIBLE);
        } else {
            setVisibility(VISIBLE);
            String text = BalanceFormatter.formatBalance(Math.abs(difference));

            setText(text);

            Drawable icon;
            int color;
            if (difference >= 0) {
                icon = getContext().getResources().getDrawable(R.drawable.ic_arrow_up);
                color = getContext().getResources().getColor(R.color.balance_up);
            } else {
                icon = getContext().getResources().getDrawable(R.drawable.ic_arrow_down);
                color = getContext().getResources().getColor(R.color.balance_down);
            }
            icon.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

            setIcon(icon);
            setTextColor(color);
        }
    }

    public void setIcon(Drawable icon) {
        setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
    }
}
