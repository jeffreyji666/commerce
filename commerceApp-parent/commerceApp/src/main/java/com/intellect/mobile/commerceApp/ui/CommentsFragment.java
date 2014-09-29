package com.intellect.mobile.commerceApp.ui;

import java.util.List;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.google.inject.Inject;
import com.intellect.mobile.commerceApp.R.string;
import com.intellect.mobile.commerceApp.account.ThrowableLoader;
import com.intellect.mobile.commerceApp.bean.Comment;
import com.intellect.mobile.commerceApp.model.CommentModel;

/**
 * Fragment to display the members of an org.
 */
public class CommentsFragment extends ItemListFragment<Comment> {
    @Inject
    private ImageLoader avatars;

    public void onSaveInstanceState(Bundle outState) {
	super.onSaveInstanceState(outState);

    }

    @Override
    public void onDetach() {

	super.onDetach();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

	super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<List<Comment>> onCreateLoader(int id, Bundle args) {
	return new ThrowableLoader<List<Comment>>(getActivity(), items) {
	    @Override
	    public List<Comment> loadData() throws Exception {
		return CommentModel.getComments();
	    }
	};
    }

    @Override
    protected SingleTypeAdapter<Comment> createAdapter(List<Comment> items) {
	Comment[] comments = items.toArray(new Comment[items.size()]);
	return new CommentListAdapter(getActivity().getLayoutInflater(),
		comments, avatars);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
	Comment comment = (Comment) l.getItemAtPosition(position);
    }

    @Override
    protected int getErrorMessage(Exception exception) {
	return string.error_members_load;
    }
}
