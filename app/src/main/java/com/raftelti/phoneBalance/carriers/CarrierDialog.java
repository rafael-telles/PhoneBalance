package com.raftelti.phoneBalance.carriers;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.raftelti.phoneBalance.R;
import com.raftelti.phoneBalance.utils.TelephonyUtils;

/**
 * Created by Rafael on 02/04/2015.
 */
public class CarrierDialog {

    public static boolean show(final Context context) {
        CarrierAdapter carrierAdapter = new CarrierAdapter(context);
        int count = carrierAdapter.getCount();

        if (count == 0) {
            Toast.makeText(
                    context,
                    String.format(context.getString(R.string.no_carrier_settings_toast_text), TelephonyUtils.getCarrierName(context)),
                    Toast.LENGTH_LONG
            ).show();
            return false;
        } else if (count == 1) {
            Carrier carrier = carrierAdapter.getItem(0);
            carrier.applyPreferences(context);
        } else {
            showCarriers(context, carrierAdapter);
        }
        return true;
    }

    private static void showCarrier(final Context context, final Carrier carrier, final MaterialDialog selectionDialog) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder
                .title(carrier.getName(true))
                .content(carrier.getConfirmDialogContent(context))
                .positiveText(android.R.string.yes)
                .negativeText(android.R.string.no)
                .negativeColor(context.getResources().getColor(android.R.color.darker_gray))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        carrier.applyPreferences(context);
                        if (selectionDialog != null) {
                            selectionDialog.dismiss();
                        }
                    }
                });
        builder.show();
    }

    public static void showCarriers(final Context context, final CarrierAdapter carrierAdapter) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder
                .title(context.getString(R.string.select_carrier_dialog_title))
                .negativeText(android.R.string.cancel)
                .negativeColor(context.getResources().getColor(android.R.color.darker_gray))
                .adapter(carrierAdapter, new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        Carrier carrier = carrierAdapter.getItem(i);
                        showCarrier(context, carrier, materialDialog);
                    }
                });

        builder.show();
    }
}
