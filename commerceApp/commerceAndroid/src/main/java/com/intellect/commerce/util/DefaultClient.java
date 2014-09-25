package com.intellect.commerce.util;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.FROYO;

import com.github.kevinsawicki.http.HttpRequest;

import java.net.HttpURLConnection;

import org.eclipse.egit.github.core.client.GitHubClient;

/**
 * Default client used to communicate with GitHub API
 */
public class DefaultClient extends GitHubClient {

    private static final String USER_AGENT = "GitHubAndroid/1.6";

    static {
        // Disable http.keepAlive on Froyo and below
        if (SDK_INT <= FROYO)
            HttpRequest.keepAlive(false);
    }

    /**
     * Create client
     */
    public DefaultClient() {
        super();

        setSerializeNulls(false);
        setUserAgent(USER_AGENT);
    }

    @Override
    protected HttpURLConnection configureRequest(HttpURLConnection request) {
        super.configureRequest(request);

        request.setRequestProperty(HEADER_ACCEPT,
                "application/vnd.github.beta.full+json");

        return request;
    }
}
