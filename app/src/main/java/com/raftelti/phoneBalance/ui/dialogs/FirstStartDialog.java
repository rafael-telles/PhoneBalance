package com.raftelti.phoneBalance.ui.dialogs;

import android.content.Context;
import android.content.Intent;
import android.text.Html;

import com.afollestad.materialdialogs.MaterialDialog;
import com.raftelti.phoneBalance.App;
import com.raftelti.phoneBalance.R;
import com.raftelti.phoneBalance.ui.settings.SettingsActivity;

/**
 * Created by Rafael on 02/04/2015.
 */
public class FirstStartDialog {
    public static void show(final Context context) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder
                .title(context.getString(R.string.firststart_dialog_title))
                .content(Html.fromHtml(context.getString(R.string.firststart_dialog_html)))
                .iconRes(R.mipmap.ic_launcher)
                .positiveText(android.R.string.ok)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        App.runningTutorial = true;

                        Intent intent = new Intent(context, SettingsActivity.class);
                        context.startActivity(intent);
                    }
                })
                .show();
    }
}
