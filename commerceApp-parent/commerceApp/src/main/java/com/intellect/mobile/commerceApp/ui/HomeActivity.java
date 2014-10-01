package com.intellect.mobile.commerceApp.ui;

import static com.actionbarsherlock.app.ActionBar.NAVIGATION_MODE_LIST;
import static com.intellect.mobile.commerceApp.util.TypefaceUtils.ICON_FOLLOW;
import static com.intellect.mobile.commerceApp.util.TypefaceUtils.ICON_NEWS;
import static com.intellect.mobile.commerceApp.util.TypefaceUtils.ICON_PUBLIC;
import static com.intellect.mobile.commerceApp.util.TypefaceUtils.ICON_TEAM;

import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.google.inject.Inject;
import com.intellect.mobile.commerceApp.bean.Comment;

public class HomeActivity extends TabPagerActivity<HomePagerAdapter> implements
	OnNavigationListener, LoaderCallbacks<List<Comment>> {

    @Inject
    private ImageLoader avatars;

    private List<Comment> comments = Collections.emptyList();

    private HomeDropdownListAdapter homeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onResume() {
	super.onResume();
    }

    private void configureActionBar() {
	ActionBar actionBar = getSupportActionBar();
	actionBar.setDisplayShowHomeEnabled(false);
	actionBar.setDisplayShowTitleEnabled(false);
	actionBar.setNavigationMode(NAVIGATION_MODE_LIST);

	homeAdapter = new HomeDropdownListAdapter(this, comments, avatars);
	actionBar.setListNavigationCallbacks(homeAdapter, this);
    }

    @Override
    public Loader<List<Comment>> onCreateLoader(int id, Bundle args) {
	return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Comment>> listLoader,
	    List<Comment> comments) {
	this.comments = comments;

	if (homeAdapter != null)
	    homeAdapter.setOrgs(comments);
	else
	    configureActionBar();

	ActionBar actionBar = getSupportActionBar();
	actionBar.setSelectedNavigationItem(0);
    }

    @Override
    public void onLoaderReset(Loader<List<Comment>> loader) {

    }

    @Override
    public boolean onNavigationItemSelected(int arg0, long arg1) {
	return false;
    }

    @Override
    protected HomePagerAdapter createAdapter() {
	return new HomePagerAdapter(this);
    }

    @Override
    protected String getIcon(int position) {
	switch (position) {
	case 0:
	    return ICON_NEWS;
	case 1:
	    return ICON_PUBLIC;
	case 2:
	    return ICON_TEAM;
	case 3:
	    return ICON_FOLLOW;
	default:
	    return super.getIcon(position);
	}
    }
}
