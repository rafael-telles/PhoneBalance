package com.raftelti.phoneBalance.core.requests;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.raftelti.phoneBalance.services.UssdReceiverService;
import com.raftelti.phoneBalance.utils.preferences.Prefs;

/**
 * Created by Rafael on 19/03/2015.
 */
public class UssdBalanceRequest implements BalanceRequest {
    @Override
    public void execute(Context context) {
        if (UssdReceiverService.isEnabled()) {
            UssdReceiverService.waitResponse();
            String ussdCode = Uri.encode(Prefs.ussdCode.get());
            if (!ussdCode.isEmpty()) {
                callUssd(context, ussdCode);
            }
        } else {
            UssdReceiverService.showAlert(context);
        }
    }

    private void callUssd(Context context, String code) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + code));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        String simSlot = Prefs.simSlot.get();
        intent.putExtra("com.android.phone.extra.slot", simSlot);
        intent.putExtra("simSlot", simSlot);
        context.startActivity(intent);
    }
}
