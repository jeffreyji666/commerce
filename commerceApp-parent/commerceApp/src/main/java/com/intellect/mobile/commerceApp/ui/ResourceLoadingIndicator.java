package com.intellect.mobile.commerceApp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.intellect.mobile.commerceApp.R.id;
import com.intellect.mobile.commerceApp.R.layout;

/**
 * Helper for showing more items are being loaded at the bottom of a list via a
 * custom footer view
 */
public class ResourceLoadingIndicator {

	private HeaderFooterListAdapter<?> adapter;

	private boolean showing;

	private final View view;

	private final TextView textView;

	/**
	 * Create indicator using given inflater
	 * 
	 * @param context
	 * @param loadingResId
	 *            string resource id to show when loading
	 */
	public ResourceLoadingIndicator(final Context context,
			final int loadingResId) {
		view = LayoutInflater.from(context).inflate(layout.loading_item, null);
		textView = (TextView) view.findViewById(id.tv_loading);
		textView.setText(loadingResId);
	}

	/**
	 * Set the adapter that this indicator should be added as a footer to
	 * 
	 * @param adapter
	 * @return this indicator
	 */
	public ResourceLoadingIndicator setList(
			final HeaderFooterListAdapter<?> adapter) {
		this.adapter = adapter;
		adapter.addFooter(view);
		showing = true;
		return this;
	}

	/**
	 * Set visibility of entire indicator view
	 * 
	 * @param visible
	 * @return this indicator
	 */
	public ResourceLoadingIndicator setVisible(final boolean visible) {
		if (showing != visible && adapter != null)
			if (visible)
				adapter.addFooter(view);
			else
				adapter.removeFooter(view);
		showing = visible;
		return this;
	}
}
