package com.raftelti.phoneBalance.carriers;

import android.content.Context;
import android.text.Html;
import android.widget.Toast;

import com.raftelti.phoneBalance.R;
import com.raftelti.phoneBalance.utils.preferences.Prefs;

/**
 * Created by Rafael on 01/04/2015.
 */
public abstract class Carrier {

    private String mOperatorName;
    private String mCountry;
    private int mSimSlot = 0;

    public Carrier(String country, String operatorName) {
        mCountry = country.toUpperCase();
        mOperatorName = operatorName;
    }

    public CharSequence getName(boolean singleSim) {
        String name = mOperatorName;
        if (!singleSim) {
            name += " - SIM " + (mSimSlot + 1);
        }
        return name;
    }

    public boolean match(String country, String operatorName) {
        return country.toUpperCase().contains(mCountry) && operatorName.toUpperCase().contains(mOperatorName.toUpperCase());
    }

    public void applyPreferences(Context context) {
        Prefs.requestSmsNumber.set(getRequestSmsNumber());
        Prefs.responseSmsNumber.set(getResponseSmsNumber());
        Prefs.smsText.set(getSmsText());
        Prefs.ussdCode.set(getUssdCode());
        Prefs.simSlot.set(mSimSlot + "");

        Prefs.requestMode.set(getRequestMode());

        Toast.makeText(
                context,
                String.format(context.getString(R.string.carrier_toast_text), getName(true)),
                Toast.LENGTH_LONG
        ).show();

    }

    public CharSequence getConfirmDialogContent(Context context) {
        String html = context.getString(R.string.pref_request_mode_title) + ": <b>" + getRequestMode().toUpperCase() + "</b><br/>";
        if ("ussd".equals(getRequestMode())) {
            html += context.getString(R.string.pref_request_ussd_code_title) + ": <b>" + getUssdCode() + "</b><br/>";
        }
        if ("sms".equals(getRequestMode())) {
            html += context.getString(R.string.pref_request_sms_number_title) + ": <b>" + getRequestSmsNumber() + "</b><br/>";
            html += context.getString(R.string.pref_response_sms_number_title) + ": <b>" + getResponseSmsNumber() + "</b><br/>";
            html += context.getString(R.string.pref_sms_text_title) + ": <b>" + getSmsText() + "</b><br/>";
        }
        html += "<br/>" + context.getString(R.string.carrier_dialog_bottomline);
        return Html.fromHtml(html);
    }

    public String getRequestMode() {
        return "";
    }

    public String getRequestSmsNumber() {
        return "";
    }

    public String getResponseSmsNumber() {
        return "";
    }

    public String getSmsText() {
        return "";
    }

    public String getUssdCode() {
        return "";
    }

    public void setSimSlot(int simSlot) {
        mSimSlot = simSlot;
    }
}
