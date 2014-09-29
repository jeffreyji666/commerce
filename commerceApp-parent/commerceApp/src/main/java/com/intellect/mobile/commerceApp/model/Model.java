package com.intellect.mobile.commerceApp.model;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.util.Log;

import com.intellect.mobile.commerceApp.app.CommerceAPI;

public class Model {
    private static final ObjectMapper json = new ObjectMapper();

    protected static boolean doRequest(String url, Map<String, String> params) {
	String jsonStr = CommerceAPI.getJsonFromUrl(url, params);
	JsonResult res = readValue(jsonStr, JsonResult.class);
	return res.getRes();
    }

    protected static <T> T doRequest(String url, Map<String, String> params,
	    Class<T> valueType) {
	String jsonStr = CommerceAPI.getJsonFromUrl(url, params);
	return readValue(jsonStr, valueType);
    }

    private static <T> T readValue(String content, Class<T> valueType) {
	try {
	    return json.readValue(content, valueType);
	} catch (JsonParseException e) {
	    Log.e("", "error happend in parse json string with content:"
		    + content, e);
	} catch (JsonMappingException e) {
	    Log.e("", "error happend in parse json string with content:"
		    + content, e);
	} catch (IOException e) {
	    Log.e("", "error happend in parse json string with content:"
		    + content, e);
	}

	return null;
    }
}
