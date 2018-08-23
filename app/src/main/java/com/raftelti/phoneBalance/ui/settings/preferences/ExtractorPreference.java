package com.raftelti.phoneBalance.ui.settings.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.raftelti.phoneBalance.R;
import com.raftelti.phoneBalance.core.BalanceExtractor;
import com.raftelti.phoneBalance.core.BalanceManager;
import com.raftelti.phoneBalance.utils.preferences.Prefs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rafael on 30/03/2015.
 */
public class ExtractorPreference extends Preference {
    private static int i = 0;
    private int rightPlace;

    public ExtractorPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
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
                        editor.putLong(getKey(), getRightPlace());
                        editor.apply();
                    }
                })
                .show();
    }

    private View createDialogView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.pref_extractor, null);

        TextView textView = (TextView) view.findViewById(R.id.message);

        Pattern pattern = new BalanceExtractor().getPattern();
        String lastMessage = BalanceManager.get().getBalance().getMessage();

        Matcher matcher = pattern.matcher(lastMessage);
        textView.setText("");

        int start = 0;
        i = 0;
        while (true) {
            if (matcher.find()) {
                int end = matcher.start();
                textView.append(lastMessage.substring(start, end));

                SelectableString spannableString = new SelectableString(matcher.group());
                textView.append(spannableString);

                start = matcher.end();
            } else {
                textView.append(lastMessage.substring(start));
                break;
            }
        }

        makeLinksFocusable(textView);

        return view;
    }

    private void makeLinksFocusable(TextView tv) {
        MovementMethod m = tv.getMovementMethod();
        if ((m == null) || !(m instanceof LinkMovementMethod)) {
            if (tv.getLinksClickable()) {
                tv.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
    }

    private int getRightPlace() {
        return rightPlace;
    }

    private void setRightPlace(int place) {
        rightPlace = place;
    }

    class SelectableString extends SpannableString {
        public SelectableString(CharSequence source) {
            super(source);
            setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    setRightPlace(place);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);

                    ds.setUnderlineText(false);
                }
            }, 0, length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.primary)), 0, length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }        private int place = i++;


    }

}