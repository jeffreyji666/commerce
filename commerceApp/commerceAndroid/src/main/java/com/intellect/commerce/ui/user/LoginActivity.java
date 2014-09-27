package com.intellect.commerce.ui.user;

import android.accounts.Account;
import android.util.Log;

import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockAccountAuthenticatorActivity;

/**
 * Activity to login
 */
public class LoginActivity extends RoboSherlockAccountAuthenticatorActivity {

	private static final String TAG = "LoginActivity";

	public static void configureSyncFor(Account account) {
		Log.d(TAG, "Configuring account sync");
	}
}