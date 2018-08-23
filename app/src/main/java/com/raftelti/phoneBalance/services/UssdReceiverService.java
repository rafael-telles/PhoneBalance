package com.raftelti.phoneBalance.services;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.raftelti.phoneBalance.core.BalanceManager;
import com.raftelti.phoneBalance.core.requests.UssdBalanceRequest;
import com.raftelti.phoneBalance.ui.dialogs.UssdAlertDialogActivity;
import com.raftelti.phoneBalance.utils.preferences.Prefs;

import java.util.List;

public class UssdReceiverService extends AccessibilityService {

    public static final String LOG_TAG = UssdReceiverService.class.getSimpleName();
    private static boolean isEnabled = false;
    private static boolean waitingResponse = false;

    public static boolean isEnabled() {
        return isEnabled;
    }

    public static void showAlert(final Context context) {
        if (!UssdReceiverService.isEnabled() && Prefs.requestMode.get().equals("ussd")) {
            Intent intent = new Intent(context, UssdAlertDialogActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public static void waitResponse() {
        waitingResponse = true;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (!waitingResponse) {
            return;
        }
        BalanceManager balanceManager = BalanceManager.get();
        if (balanceManager.getRequest() instanceof UssdBalanceRequest) {
            boolean valid = isValid(event);
            if (valid) {
                for (CharSequence text : event.getText()) {
                    boolean messageValid = balanceManager.isMessageValid(text);
                    if (messageValid) {
                        balanceManager.saveRecord(text.toString());
                        break;
                    }
                }
                closeDialog(event);
                waitingResponse = false;
            }
        }
    }

    private void closeDialog(AccessibilityEvent event) {
        AccessibilityNodeInfo source = event.getSource();
        List<AccessibilityNodeInfo> possibleOkButtons = source.findAccessibilityNodeInfosByText(getString(android.R.string.ok));
        if (possibleOkButtons.size() > 0) {
            possibleOkButtons.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }

    private boolean isValid(AccessibilityEvent event) {
        return event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED &&
                "com.android.phone".equals(event.getPackageName()) &&
                "android.app.AlertDialog".equals(event.getClassName());
    }

    @Override
    public void onInterrupt() {
        isEnabled = false;
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        isEnabled = true;
    }
}