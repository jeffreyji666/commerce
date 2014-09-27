package com.intellect.mobile.commerceApp.ui;

import static com.actionbarsherlock.view.Window.FEATURE_INDETERMINATE_PROGRESS;
import android.os.Bundle;

import com.github.kevinsawicki.wishlist.ViewFinder;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;

import java.io.Serializable;

/**
 * Activity that display dialogs
 */
public abstract class DialogFragmentActivity extends
		RoboSherlockFragmentActivity implements DialogResultListener {

	/**
	 * Finder bound to this activity's view
	 */
	protected ViewFinder finder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(FEATURE_INDETERMINATE_PROGRESS);

		super.onCreate(savedInstanceState);

		finder = new ViewFinder(this);
	}

	/**
	 * Get intent extra
	 * 
	 * @param name
	 * @return serializable
	 */
	@SuppressWarnings("unchecked")
	protected <V extends Serializable> V getSerializableExtra(final String name) {
		return (V) getIntent().getSerializableExtra(name);
	}

	/**
	 * Get intent extra
	 * 
	 * @param name
	 * @return int
	 */
	protected int getIntExtra(final String name) {
		return getIntent().getIntExtra(name, -1);
	}

	/**
	 * Get intent extra
	 * 
	 * @param name
	 * @return int array
	 */
	protected int[] getIntArrayExtra(final String name) {
		return getIntent().getIntArrayExtra(name);
	}

	/**
	 * Get intent extra
	 * 
	 * @param name
	 * @return boolean array
	 */
	protected boolean[] getBooleanArrayExtra(final String name) {
		return getIntent().getBooleanArrayExtra(name);
	}

	/**
	 * Get intent extra
	 * 
	 * @param name
	 * @return string
	 */
	protected String getStringExtra(final String name) {
		return getIntent().getStringExtra(name);
	}

	/**
	 * Get intent extra
	 * 
	 * @param name
	 * @return string array
	 */
	protected String[] getStringArrayExtra(final String name) {
		return getIntent().getStringArrayExtra(name);
	}

	/**
	 * Get intent extra
	 * 
	 * @param name
	 * @return char sequence array
	 */
	protected CharSequence[] getCharSequenceArrayExtra(final String name) {
		return getIntent().getCharSequenceArrayExtra(name);
	}

	@Override
	public void onDialogResult(int requestCode, int resultCode, Bundle arguments) {
		// Intentionally left blank
	}
}
