package com.intellect.mobile.commerceApp.ui;

import java.util.HashSet;
import java.util.Set;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.intellect.mobile.commerceApp.R.string;

/**
 * Pager adapter for a user's different views
 */
public class HomePagerAdapter extends FragmentPagerAdapter {
    private final FragmentManager fragmentManager;

    private final Resources resources;

    private final Set<String> tags = new HashSet<String>();

    /**
     * @param activity
     */
    public HomePagerAdapter(final SherlockFragmentActivity activity) {
	super(activity);

	fragmentManager = activity.getSupportFragmentManager();
	resources = activity.getResources();
    }

    @Override
    public Fragment getItem(int position) {
	switch (position) {
	case 0:
	    return new CommentsFragment();
	case 1:
	    return new CommentsFragment();
	case 2:
	    return new CommentsFragment();
	case 3:
	    return new CommentsFragment();
	default:
	    return null;
	}
    }

    /**
     * This methods clears any fragments that may not apply to the newly
     * selected org.
     * 
     * @param isDefaultUser
     * @return this adapter
     */
    public HomePagerAdapter clearAdapter() {
	if (tags.isEmpty())
	    return this;

	FragmentTransaction transaction = fragmentManager.beginTransaction();
	for (String tag : tags) {
	    Fragment fragment = fragmentManager.findFragmentByTag(tag);
	    if (fragment != null)
		transaction.remove(fragment);
	}
	transaction.commit();
	tags.clear();

	return this;
    }

    @Override
    public int getItemPosition(Object object) {
	return POSITION_NONE;
    }

    public Object instantiateItem(ViewGroup container, int position) {
	Object fragment = super.instantiateItem(container, position);
	if (fragment instanceof Fragment)
	    tags.add(((Fragment) fragment).getTag());
	return fragment;
    }

    @Override
    public int getCount() {
	return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
	switch (position) {
	case 0:
	    return resources.getString(string.tab_news);
	case 1:
	    return resources.getString(string.tab_repositories);
	case 2:
	    return resources.getString(string.tab_members);
	case 3:
	    return resources.getString(string.tab_following_self);
	default:
	    return null;
	}
    }
}
