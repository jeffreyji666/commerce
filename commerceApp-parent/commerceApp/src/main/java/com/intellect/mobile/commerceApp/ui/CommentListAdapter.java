package com.intellect.mobile.commerceApp.ui;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.intellect.mobile.commerceApp.R.id;
import com.intellect.mobile.commerceApp.R.layout;
import com.intellect.mobile.commerceApp.bean.Comment;

/**
 * Adapter for a list of {@link Comment} objects
 */
public class CommentListAdapter extends SingleTypeAdapter<Comment> {

    public CommentListAdapter(LayoutInflater inflater, Comment[] elements,
	    ImageLoader avatars) {
	super(inflater, layout.comment_item);
	setItems(elements);
    }

    @Override
    protected void update(int position, Comment comment) {
    }

    @Override
    public long getItemId(final int position) {
	return getItem(position).getId();
    }

    @Override
    protected View initialize(View view) {
	view = super.initialize(view);

	textView(view, 0).setMovementMethod(LinkMovementMethod.getInstance());
	return view;
    }

    @Override
    protected int[] getChildViewIds() {
	return new int[] { id.tv_comment_body, id.tv_comment_author,
		id.tv_comment_date, id.iv_avatar };
    }
}
