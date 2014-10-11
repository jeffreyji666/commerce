package mobile.intellect.commerceapp.util;

import android.app.Activity;
import com.github.kevinsawicki.wishlist.Toaster;

/**
 * Utilities for displaying toast notifications
 */
public class ToastUtils {

    /**
     * Show the given message in a {@link android.widget.Toast}
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
     * Show the message with the given resource id in a {@link android.widget.Toast}
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
}
