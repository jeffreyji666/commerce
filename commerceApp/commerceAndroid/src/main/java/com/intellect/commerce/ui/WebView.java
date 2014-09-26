package com.intellect.commerce.ui;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH;
import android.content.Context;
import android.util.AttributeSet;

/**
 * Web view extension with scrolling fixes
 */
public class WebView extends android.webkit.WebView {

    /**
     * @param context
     * @param attrs
     * @param defStyle
     * @param privateBrowsing
     */
    public WebView(final Context context, final AttributeSet attrs,
            final int defStyle, final boolean privateBrowsing) {
        super(context, attrs, defStyle, privateBrowsing);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public WebView(final Context context, final AttributeSet attrs,
            final int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * @param context
     * @param attrs
     */
    public WebView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context
     */
    public WebView(final Context context) {
        super(context);
    }

    private boolean canScrollCodeHorizontally(final int direction) {
        final int range = computeHorizontalScrollRange()
                - computeHorizontalScrollExtent();
        if (range == 0)
            return false;

        if (direction < 0)
            return computeHorizontalScrollOffset() > 0;
        else
            return computeHorizontalScrollOffset() < range - 1;
    }

    @Override
    public boolean canScrollHorizontally(final int direction) {
        if (SDK_INT >= ICE_CREAM_SANDWICH)
            return super.canScrollHorizontally(direction);
        else
            return canScrollCodeHorizontally(direction);
    }
}
