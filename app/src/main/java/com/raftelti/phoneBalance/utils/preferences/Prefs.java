package com.raftelti.phoneBalance.utils.preferences;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.raftelti.phoneBalance.App;
import com.raftelti.phoneBalance.R;

public class Prefs {
    public static final StringPreference
            requestMode = new StringPreference(
            R.string.pref_request_mode_key,
            R.string.pref_request_mode_default),
            ussdCode = new StringPreference(
                    R.string.pref_request_ussd_code_key,
                    ""),
            requestSmsNumber = new StringPreference(
                    R.string.pref_request_sms_number_key,
                    ""),
            responseSmsNumber = new StringPreference(
                    R.string.pref_response_sms_number_key,
                    ""),
            smsText = new StringPreference(
                    R.string.pref_sms_text_key,
                    ""),
            simSlot = new StringPreference(
                    R.string.pref_sim_slot_key,
                    "0");
    public static final BooleanPreference
            updatePeriodically = new BooleanPreference(
            R.string.pref_update_periodically_key,
            true),
            updateOnCall = new BooleanPreference(
                    R.string.pref_update_on_call_key,
                    false),
            firstStart = new BooleanPreference(
                    R.string.pref_first_start_key,
                    true);

    public static final LongPreference
            lastRequestTimestamp = new LongPreference(
            R.string.pref_last_request_timestamp_key,
            0L),
            updateInterval = new LongPreference(
                    R.string.pref_update_interval_key,
                    1000 * 60 * 60 * 3L),
            extractorRightPlace = new LongPreference(
                    R.string.pref_extractor_right_place_key,
                    0L);


    public static SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(App.get());
    }
}
