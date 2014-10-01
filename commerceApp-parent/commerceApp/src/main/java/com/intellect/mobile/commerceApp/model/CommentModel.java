package com.intellect.mobile.commerceApp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.intellect.mobile.commerceApp.bean.Comment;

public class CommentModel extends Model {
    private static final String getCommentsUrl = "/getComments";

    @SuppressWarnings("unchecked")
    public static List<Comment> getComments() {
	Map<String, String> params = new HashMap<String, String>();
	return (List<Comment>) doRequest(getCommentsUrl, params,
		List.class);
    }
}
