package com.intellect.mobile.commerceApp.account;

import android.accounts.Account;
import android.content.Context;
import android.util.Log;

/**
 * Loader that support throwing an exception when loading in the background
 * 
 * @param <D>
 */
public abstract class ThrowableLoader<D> extends AuthenticatedUserLoader<D> {

	private static final String TAG = "ThrowableLoader";

	private final D data;

	private Exception exception;

	/**
	 * Create loader for context and seeded with initial data
	 * 
	 * @param context
	 * @param data
	 */
	public ThrowableLoader(Context context, D data) {
		super(context);

		this.data = data;
	}

	@Override
	protected D getAccountFailureData() {
		return data;
	}

	@Override
	public D load(final Account account) {
		exception = null;
		try {
			return loadData();
		} catch (Exception e) {
			if (AccountUtils.isUnauthorized(e)
					&& AccountUtils.updateAccount(account, activity))
				try {
					return loadData();
				} catch (Exception e2) {
					e = e2;
				}
			Log.d(TAG, "Exception loading data", e);
			exception = e;
			return data;
		}
	}

	/**
	 * @return exception
	 */
	public Exception getException() {
		return exception;
	}

	/**
	 * Clear the stored exception and return it
	 * 
	 * @return exception
	 */
	public Exception clearException() {
		final Exception throwable = exception;
		exception = null;
		return throwable;
	}

	/**
	 * Load data
	 * 
	 * @return data
	 * @throws Exception
	 */
	public abstract D loadData() throws Exception;
}
