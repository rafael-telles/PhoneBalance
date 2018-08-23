package com.raftelti.phoneBalance.ui.settings.preferences;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;

import com.afollestad.materialdialogs.MaterialDialog;
import com.raftelti.phoneBalance.R;
import com.raftelti.phoneBalance.core.BalanceManager;
import com.raftelti.phoneBalance.data.BalanceContract;

/**
 * Created by Rafael on 30/03/2015.
 */
public class ClearHistoryPreference extends Preference {
    public ClearHistoryPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onClick() {
        super.onClick();

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getContext());
        builder
                .title(R.string.pref_clear_history_title)
                .content(R.string.pref_clear_history_content)
                .positiveText(android.R.string.yes)
                .negativeText(android.R.string.no)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);

                        getContext().getContentResolver().delete(BalanceContract.BalanceEntry.CONTENT_URI,
                                BalanceContract.BalanceEntry._ID + " <> " + BalanceManager.get().getBalance().getId(),
                                null);
                    }
                })
                .show();
    }
}
