package com.intellect.commerce.util;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import com.github.kevinsawicki.wishlist.Toaster;

/**
 * Utilities for displaying toast notifications
 */
public class ToastUtils {

    /**
     * Show the given message in a {@link Toast}
     * <p>
     * This method may be called from any thread
     *
     * @param activity
     * @param message
     */
    public static void show(final Activity activity, final String message) {
        Toaster.showLong(activity, message);
    }

    /**
     * Show the message with the given resource id in a {@link Toast}
     * <p>
     * This method may be called from any thread
     *
     * @param activity
     * @param resId
     */
    public static void show(final Activity activity, final int resId) {
        if (activity == null)
            return;

        show(activity, activity.getString(resId));
    }

    /**
     * Show {@link Toast} for exception
     * <p>
     * This given default message will be used if an message can not be derived
     * from the given {@link Exception}
     * <p>
     * This method may be called from any thread
     *
     * @param activity
     * @param e
     * @param defaultMessage
     */
    public static void show(final Activity activity, final Exception e,
            final int defaultMessage) {
        if (activity == null)
            return;

        String message = e.getMessage();

        if (TextUtils.isEmpty(message))
            message = activity.getString(defaultMessage);

        show(activity, message);
    }
}
