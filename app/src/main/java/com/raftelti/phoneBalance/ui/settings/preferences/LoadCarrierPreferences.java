package com.raftelti.phoneBalance.ui.settings.preferences;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;

import com.raftelti.phoneBalance.carriers.CarrierDialog;

/**
 * Created by Rafael on 02/04/2015.
 */
public class LoadCarrierPreferences extends Preference {
    public LoadCarrierPreferences(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onClick() {
        CarrierDialog.show(getContext());
    }
}
