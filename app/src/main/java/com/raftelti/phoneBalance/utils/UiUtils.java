package com.raftelti.phoneBalance.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

public class UiUtils {

    public static void setListViewGlowColor(Context context, int brandColor) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            try {
                int glowDrawableId = context.getResources().getIdentifier("overscroll_glow", "drawable", "android");
                Drawable androidGlow = context.getResources().getDrawable(glowDrawableId);
                androidGlow.setColorFilter(brandColor, PorterDuff.Mode.SRC_ATOP);

                int edgeDrawableId = context.getResources().getIdentifier("overscroll_edge", "drawable", "android");
                Drawable androidEdge = context.getResources().getDrawable(edgeDrawableId);
                androidEdge.setColorFilter(brandColor, PorterDuff.Mode.SRC_ATOP);
            } catch (Resources.NotFoundException e) {

            }
        }
    }

    public static void applyToolbarPadding(Activity activity, Toolbar toolbar) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            toolbar.setPadding(0, UiUtils.getStatusBarHeight(toolbar.getContext()), 0, 0);
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
