package com.raftelti.phoneBalance.ui.settings.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.afollestad.materialdialogs.MaterialDialog;
import com.raftelti.phoneBalance.R;
import com.raftelti.phoneBalance.utils.preferences.Prefs;

/**
 * Created by Rafael on 25/03/2015.
 */
public class UpdateIntervalPreference extends Preference {

    final private String[] periods;
    final private long[] periodsDuration = new long[]{1000 * 60, 1000 * 60 * 60, 1000 * 60 * 60 * 24};
    final private int[] periodsLimit = new int[]{59, 23, 7};
    final private int[] periodsPlurals = new int[]{R.plurals.every_n_minutes, R.plurals.every_n_hours, R.plurals.every_n_days};
    private NumberPicker qntPicker;
    private NumberPicker periodPicker;

    public UpdateIntervalPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPersistent(false);
        setTitle(R.string.pref_update_interval_title);
        //setDialogLayoutResource(R.layout.pref_update_interval);
        periods = new String[]{
                context.getString(R.string.minutes),
                context.getString(R.string.hours),
                context.getString(R.string.days),
        };
    }

    @Override
    protected void onClick() {
        super.onClick();

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getContext());
        builder
                .title(getTitle())
                .customView(createDialogView(), false)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        SharedPreferences.Editor editor = Prefs.getPreferences().edit();
                        editor.putLong(getKey(), getValueMs());
                        editor.apply();
                    }
                })
                .show();
    }

    private View createDialogView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View view = inflater.inflate(R.layout.pref_update_interval, null);

        qntPicker = (NumberPicker) view.findViewById(R.id.numberPicker1);
        periodPicker = (NumberPicker) view.findViewById(R.id.numberPicker2);

        qntPicker.setMinValue(1);
        qntPicker.setMaxValue(23);

        periodPicker.setDisplayedValues(periods);
        periodPicker.setMinValue(0);
        periodPicker.setMaxValue(periods.length - 1);
        periodPicker.setValue(1);

        periodPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                qntPicker.setMaxValue(periodsLimit[newVal]);
            }
        });

        long storedValue = getStoredValue();

        qntPicker.setValue(getQuantity(storedValue));
        periodPicker.setValue(getPeriod(storedValue));
        return view;
    }

    @Override
    public CharSequence getSummary() {
        long value = getStoredValue();
        return getContext().getResources().getQuantityString(periodsPlurals[getPeriod(value)], getQuantity(value), getQuantity(value));
    }

    private long getStoredValue() {
        return Prefs.getPreferences().getLong(getKey(), 6 * 60 * 60 * 1000);
    }

    private long getValueMs() {
        long value = qntPicker.getValue();
        long mul;
        switch (periodPicker.getValue()) {
            case 0:
                mul = 1000 * 60;
                break;
            default:
            case 1:
                mul = 1000 * 60 * 60;
                break;
            case 2:
                mul = 1000 * 60 * 60 * 24;
                break;
        }
        return value * mul;
    }

    private int getQuantity(long value) {
        for (int n = periods.length - 1; n >= 0; n--) {
            if (periodsDuration[n] <= value) return (int) (value / periodsDuration[n]);
        }
        return 0;
    }

    private int getPeriod(long value) {
        for (int n = periods.length - 1; n >= 0; n--) {
            if (periodsDuration[n] <= value) return n;
        }
        return 0;
    }
}

