package com.raftelti.phoneBalance.ui.main.components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * Created by Rafael on 30/03/2015.
 */
public class OverviewDifferenceTextView extends DifferenceTextView {
    public OverviewDifferenceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setIcon(Drawable icon) {
        setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);
    }
}
