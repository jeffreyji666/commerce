package com.intellect.mobile.commerceApp.util;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static java.lang.Integer.MAX_VALUE;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html.ImageGetter;
import android.text.TextUtils;
import android.widget.TextView;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;
import com.google.inject.Inject;
import com.intellect.mobile.commerceApp.R.drawable;

/**
 * Getter for an image
 */
public class HttpImageGetter implements ImageGetter {

    private static class LoadingImageGetter implements ImageGetter {

	private final Drawable image;

	private LoadingImageGetter(final Context context, final int size) {
	    int imageSize = ServiceUtils.getIntPixels(context, size);
	    image = context.getResources().getDrawable(
		    drawable.image_loading_icon);
	    image.setBounds(0, 0, imageSize, imageSize);
	}

	@Override
	public Drawable getDrawable(String source) {
	    return image;
	}
    }

    private static boolean containsImages(final String html) {
	return html.contains("<img");
    }

    private final LoadingImageGetter loading;

    private final Context context;

    private final File dir;

    private final int width;

    private final Map<Object, CharSequence> rawHtmlCache = new HashMap<Object, CharSequence>();

    private final Map<Object, CharSequence> fullHtmlCache = new HashMap<Object, CharSequence>();

    /**
     * Create image getter for context
     * 
     * @param context
     * @param service
     */
    @Inject
    public HttpImageGetter(Context context) {
	this.context = context;
	dir = context.getCacheDir();
	width = ServiceUtils.getDisplayWidth(context);
	loading = new LoadingImageGetter(context, 24);
    }

    private HttpImageGetter show(final TextView view, final CharSequence html) {
	if (TextUtils.isEmpty(html))
	    return hide(view);

	view.setText(html);
	view.setVisibility(VISIBLE);
	view.setTag(null);
	return this;
    }

    private HttpImageGetter hide(final TextView view) {
	view.setText(null);
	view.setVisibility(GONE);
	view.setTag(null);
	return this;
    }

    /**
     * Encode given HTML string and map it to the given id
     * 
     * @param id
     * @param html
     * @return this image getter
     */
    public HttpImageGetter encode(final Object id, final String html) {
	if (TextUtils.isEmpty(html))
	    return this;

	CharSequence encoded = HtmlUtils.encode(html, loading);
	// Use default encoding if no img tags
	if (containsImages(html)) {

	    CharSequence currentEncoded = rawHtmlCache.put(id, encoded);
	    // Remove full html if raw html has changed
	    if (currentEncoded == null
		    || !currentEncoded.toString().equals(encoded.toString()))
		fullHtmlCache.remove(id);
	} else {
	    rawHtmlCache.remove(id);
	    fullHtmlCache.put(id, encoded);
	}
	return this;
    }

    /**
     * Bind text view to HTML string
     * 
     * @param view
     * @param html
     * @param id
     * @return this image getter
     */
    public HttpImageGetter bind(final TextView view, final String html,
	    final Object id) {
	if (TextUtils.isEmpty(html))
	    return hide(view);

	CharSequence encoded = fullHtmlCache.get(id);
	if (encoded != null)
	    return show(view, encoded);

	encoded = rawHtmlCache.get(id);
	if (encoded == null) {
	    encoded = HtmlUtils.encode(html, loading);
	    if (containsImages(html))
		rawHtmlCache.put(id, encoded);
	    else {
		rawHtmlCache.remove(id);
		fullHtmlCache.put(id, encoded);
		return show(view, encoded);
	    }
	}

	if (TextUtils.isEmpty(encoded))
	    return hide(view);

	show(view, encoded);
	view.setTag(id);
	return this;
    }

    @Override
    public Drawable getDrawable(final String source) {
	File output = null;
	try {
	    output = File.createTempFile("image", ".jpg", dir);
	    HttpRequest request = HttpRequest.get(source);
	    if (!request.ok())
		throw new IOException("Unexpected response code: "
			+ request.code());
	    request.receive(output);
	    Bitmap bitmap = ImageUtils.getBitmap(output, width, MAX_VALUE);
	    if (bitmap == null)
		return loading.getDrawable(source);

	    BitmapDrawable drawable = new BitmapDrawable(
		    context.getResources(), bitmap);
	    drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
	    return drawable;
	} catch (IOException e) {
	    return loading.getDrawable(source);
	} catch (HttpRequestException e) {
	    return loading.getDrawable(source);
	} finally {
	    if (output != null)
		output.delete();
	}
    }
}
