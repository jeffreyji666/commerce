package com.intellect.mobile.commerceApp.util;

import static android.content.Context.MODE_PRIVATE;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.GINGERBREAD;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Utility class for working with {@link SharedPreferences}
 */
public class PreferenceUtils {

	/**
	 * Preference to wrap lines of code
	 */
	public static final String WRAP = "wrap";

	/**
	 * Get code browsing preferences
	 * 
	 * @param context
	 * @return preferences
	 */
	public static SharedPreferences getCodePreferences(final Context context) {
		return context.getSharedPreferences("code", MODE_PRIVATE);
	}

	private static boolean isEditorApplyAvailable() {
		return SDK_INT >= GINGERBREAD;
	}

	/**
	 * Save preferences in given editor
	 * 
	 * @param editor
	 */
	public static void save(final Editor editor) {
		if (isEditorApplyAvailable())
			editor.apply();
		else
			editor.commit();
	}
}
