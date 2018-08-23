package com.raftelti.phoneBalance.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.preference.SwitchPreference;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.raftelti.phoneBalance.App;
import com.raftelti.phoneBalance.R;
import com.raftelti.phoneBalance.core.BalanceManager;
import com.raftelti.phoneBalance.receivers.AlarmReceiver;
import com.raftelti.phoneBalance.services.UssdReceiverService;
import com.raftelti.phoneBalance.utils.TelephonyUtils;
import com.raftelti.phoneBalance.utils.preferences.Prefs;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private Preference smsRequestNumberPreference;
    private Preference smsTextPreference;
    private Preference smsResponseNumberPreference;
    private Preference ussdCodePreference;
    private ListPreference simSlotPreference;
    private int simCount;
    private Preference extractorRightPlacePreference;
    private Preference requestModePreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_settings, container, false);

        smsRequestNumberPreference = findPreference(getString(R.string.pref_request_sms_number_key));
        smsTextPreference = findPreference(getString(R.string.pref_sms_text_key));
        smsResponseNumberPreference = findPreference(getString(R.string.pref_response_sms_number_key));
        ussdCodePreference = findPreference(getString(R.string.pref_request_ussd_code_key));
        simSlotPreference = (ListPreference) findPreference(getString(R.string.pref_sim_slot_key));
        extractorRightPlacePreference = findPreference(getString(R.string.pref_extractor_right_place_key));
        requestModePreference = findPreference(getString(R.string.pref_request_mode_key));

        setSimSlotPreference();

        initSummary(getPreferenceScreen());

        if (BalanceManager.get().getBalance() == null) {
            extractorRightPlacePreference.setEnabled(false);
            extractorRightPlacePreference.setSummary(getActivity().getString(R.string.pref_extractor_right_place_unabled_summary));
        }

        if (App.runningTutorial) {
            MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
            builder
                    .title(R.string.title_activity_settings)
                    .content(R.string.firststart_settings_dialog_content)
                    .iconRes(R.mipmap.ic_launcher)
                    .positiveText(android.R.string.ok)
                    .show();
        }

        return rootView;
    }

    private void setSimSlotPreference() {
        if (TelephonyUtils.checkAvaliability(getActivity())) {
            simCount = TelephonyUtils.getSimCount(getActivity());
            if (simCount > 1) {
                String[] entries = new String[simCount];
                String[] entryValues = new String[simCount];
                for (int i = 0; i < simCount; i++) {
                    String carrierName = TelephonyUtils.getCarrierName(getActivity(), i + 1);
                    if (carrierName.isEmpty()) {
                        entries[i] = String.format("SIM %d", i + 1);
                    } else {
                        entries[i] = String.format("SIM %d (%s)", i + 1, carrierName);
                    }
                    entryValues[i] = i + "";
                }
                simSlotPreference.setEntries(entries);
                simSlotPreference.setEntryValues(entryValues);
            } else {
                getPreferenceScreen().removePreference(simSlotPreference);
                Prefs.simSlot.set("0");
            }
        } else {
            getPreferenceScreen().removePreference(simSlotPreference);
            Prefs.simSlot.set("0");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);

        hideOrShow(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        hideOrShow(true);

        Preference preference = findPreference(key);
        updatePrefSummary(preference);
        if (getString(R.string.pref_request_mode_key).equals(key)) {
            UssdReceiverService.showAlert(getActivity());
        }
        if (getString(R.string.pref_update_periodically_key).equals(key)) {
            findPreference(getString(R.string.pref_update_interval_key)).setEnabled(((SwitchPreference) preference).isChecked());
        }
        AlarmReceiver.startAlarm(getActivity());
    }

    private void initSummary(Preference p) {
        if (p instanceof PreferenceGroup) {
            PreferenceGroup pGrp = (PreferenceGroup) p;
            for (int i = 0; i < pGrp.getPreferenceCount(); i++) {
                initSummary(pGrp.getPreference(i));
            }
        } else {
            updatePrefSummary(p);
        }
    }

    private void updatePrefSummary(Preference p) {
        if (p instanceof ListPreference) {
            ListPreference listPref = (ListPreference) p;
            p.setSummary(listPref.getEntry());
        }
        if (p instanceof EditTextPreference) {
            EditTextPreference editTextPref = (EditTextPreference) p;
            p.setSummary(editTextPref.getText());
        }
        if (p instanceof MultiSelectListPreference) {
            MultiSelectListPreference listPreference = (MultiSelectListPreference) p;
            p.setSummary(listPreference.getEntries()[0]);
        }

        hideOrShow(false);
    }

    private void hideOrShow(boolean update) {
        PreferenceCategory group = ((PreferenceCategory) findPreference(getString(R.string.pref_request_settings_group_key)));

        //It updates the request mode summary
        group.removePreference(requestModePreference);
        group.addPreference(requestModePreference);

        group.removePreference(smsTextPreference);
        group.removePreference(smsRequestNumberPreference);
        group.removePreference(smsResponseNumberPreference);
        group.removePreference(ussdCodePreference);
        group.removePreference(simSlotPreference);

        String requestMode = Prefs.requestMode.get();
        if ("ussd".equals(requestMode)) {
            group.addPreference(ussdCodePreference);
        } else {
            group.addPreference(smsTextPreference);
            group.addPreference(smsRequestNumberPreference);
            group.addPreference(smsResponseNumberPreference);
        }
        if (simCount > 1) group.addPreference(simSlotPreference);

        if (update) {
            updatePrefSummary(requestModePreference);
            updatePrefSummary(ussdCodePreference);
            updatePrefSummary(simSlotPreference);
            updatePrefSummary(smsTextPreference);
            updatePrefSummary(smsRequestNumberPreference);
            updatePrefSummary(smsResponseNumberPreference);
        }
    }
}