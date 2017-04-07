package com.fragmenttabsdemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by varunairon on 5/11/2015.
 */
public class ApplicationUtils {
    private static int screenWidth = 0;
    private static int screenHeight = 0;
    public static int itemHeight = 50;

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }

    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }

    public static int getActionBarHeight(Context c) {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (c.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, c.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }


    /**
     * Hides the keyboard.
     *
     * @param context the context
     * @param view the view that is making request.
     * @param flags Provides additional operating flags. Currently may be 0 or have the HIDE_IMPLICIT_ONLY bit set.
     */
    public static void hideSoftKeyboard(Context context, View view, int flags) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), flags);
        } else {
            inputManager.hideSoftInputFromWindow(null, flags);
        }
    }

}
