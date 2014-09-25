package com.intellect.commerce.accounts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountsException;
import android.app.Activity;
import android.content.Context;

import com.github.kevinsawicki.wishlist.AsyncLoader;
import com.google.inject.Inject;

import java.io.IOException;

import roboguice.RoboGuice;
import roboguice.inject.ContextScope;

/**
 * Base loader class that ensures an authenticated account exists before
 * {@link #load(Account)} is called
 *
 * @param <D>
 */
public abstract class AuthenticatedUserLoader<D> extends AsyncLoader<D> {

    @Inject
    private ContextScope contextScope;

    @Inject
    private AccountScope accountScope;

    /**
     * Activity using this loader
     */
    @Inject
    protected Activity activity;

    /**
     * Create loader for context
     *
     * @param context
     */
    public AuthenticatedUserLoader(final Context context) {
        super(context);

        RoboGuice.injectMembers(context, this);
    }

    /**
     * Get data to display when obtaining an account fails
     *
     * @return data
     */
    protected abstract D getAccountFailureData();

    @Override
    public final D loadInBackground() {
        final AccountManager manager = AccountManager.get(activity);
        final Account account;
        try {
            account = AccountUtils.getAccount(manager, activity);
        } catch (IOException e) {
            return getAccountFailureData();
        } catch (AccountsException e) {
            return getAccountFailureData();
        }

        accountScope.enterWith(account, manager);
        try {
            contextScope.enter(getContext());
            try {
                return load(account);
            } finally {
                contextScope.exit(getContext());
            }
        } finally {
            accountScope.exit();
        }
    }

    /**
     * Load data
     *
     * @param account
     * @return data
     */
    public abstract D load(Account account);
}