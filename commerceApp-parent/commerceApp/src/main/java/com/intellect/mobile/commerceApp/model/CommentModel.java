package com.intellect.mobile.commerceApp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.intellect.mobile.commerceApp.bean.Comment;

public class CommentModel extends Model {
    private static final String getCommentsUrl = "/api/auth/login";

    @SuppressWarnings("unchecked")
    public static List<Comment> getComments() {
	Map<String, String> params = new HashMap<String, String>();
	return (List<Comment>) doRequest(getCommentsUrl, params,
		ArrayList.class);
    }
}
