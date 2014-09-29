package com.intellect.mobile.commerceApp.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class CommerceAPI {
    private static final int SLEEP_TIME_IN_SECOND = 1000;
    private static final int RETRY_TIME = 3;

    public static String getJsonFromUrl(String url, Map<String, String> params) {
	HttpClient client = getHttpClient();
	HttpPost post = new HttpPost(url);
	post = initRequest(post);
	int time = 0;
	do {
	    try {
		if (params != null && params.size() > 0) {
		    Iterator<String> keys = params.keySet().iterator();
		    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		    while (keys.hasNext()) {
			String key = keys.next();
			nvps.add(new BasicNameValuePair(key, params.get(key)));
		    }
		    post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		}
		HttpResponse response = client.execute(post);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK
			&& response.getEntity().getContentEncoding().getValue()
				.equalsIgnoreCase("gzip")) {
		    response.setEntity(new GzipDecompressingEntity(response
			    .getEntity()));
		    return EntityUtils.toString(response.getEntity());
		}
	    } catch (Exception e) {
		time++;
		if (time < RETRY_TIME) {
		    try {
			Thread.sleep(SLEEP_TIME_IN_SECOND);
		    } catch (InterruptedException e1) {
		    }
		    continue;
		}
		Log.e("", "error happened in sending post request:", e);
	    }
	} while (time < RETRY_TIME);
	return "";
    }

    private static HttpClient getHttpClient() {
	// add the basic authentication
	DefaultHttpClient client = new DefaultHttpClient();
	client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
		HttpVersion.HTTP_1_1);
	client.getParams().setParameter(
		CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
	client.getParams().setParameter(
		CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);

	return client;
    }

    private static HttpPost initRequest(HttpPost post) {
	post.addHeader("Connection", "Keep-Alive");
	post.addHeader("User-Agent", AppConfig.getString("userAgent"));
	post.addHeader("Accept-Encoding", "gzip");
	return post;
    }
}
