package com.raftelti.phoneBalance.ui.dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.raftelti.phoneBalance.R;

/**
 * Created by Rafael on 23/03/2015.
 */
public class UssdAlertDialogActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title(getString(R.string.ussd_alert_dialog_title))
                .iconRes(R.mipmap.ic_launcher)
                .content(getString(R.string.ussd_alert_dialog_content))
                .positiveText(getString(R.string.ussd_alert_dialog_positive))
                .positiveColorRes(R.color.primary)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        startActivity(intent);
                        Toast.makeText(UssdAlertDialogActivity.this, getString(R.string.ussd_alert_dialog_toast), Toast.LENGTH_LONG).show();
                        UssdAlertDialogActivity.this.finish();
                    }
                })
                .cancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        UssdAlertDialogActivity.this.finish();
                    }
                });
        builder.show();
    }
}
