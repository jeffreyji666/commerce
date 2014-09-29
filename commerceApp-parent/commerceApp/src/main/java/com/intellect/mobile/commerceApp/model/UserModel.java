package com.intellect.mobile.commerceApp.model;

import java.util.HashMap;
import java.util.Map;

import com.intellect.mobile.commerceApp.app.AppContext;

public class UserModel extends Model {
    private static final String loginUrl = "/api/auth/login";
    private static final String registerUrl = "/api/auth/login";

    public static boolean login() {
	Map<String, String> params = new HashMap<String, String>();

	return doRequest(loginUrl, params);
    }

    public static boolean register(AppContext ctx) {
	Map<String, String> params = new HashMap<String, String>();

	return doRequest(registerUrl, params);
    }

}
