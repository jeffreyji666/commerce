package com.intellect.mobile.commerceApp.ui;

import static android.graphics.Bitmap.CompressFormat.PNG;
import static android.graphics.Bitmap.Config.ARGB_8888;
import static android.view.View.VISIBLE;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import roboguice.util.RoboAsyncTask;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.actionbarsherlock.app.ActionBar;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.inject.Inject;
import com.intellect.mobile.commerceApp.R.drawable;
import com.intellect.mobile.commerceApp.R.id;
import com.intellect.mobile.commerceApp.bean.User;
import com.intellect.mobile.commerceApp.util.GravatarUtils;
import com.intellect.mobile.commerceApp.util.ImageUtils;

/**
 * image utilities
 */
public class ImageLoader {

    private static final String TAG = "ImageLoader";

    private static final float CORNER_RADIUS_IN_DIP = 3;

    private static final int CACHE_SIZE = 75;

    private static abstract class FetchImageTask extends
	    RoboAsyncTask<BitmapDrawable> {

	private static final Executor EXECUTOR = Executors
		.newFixedThreadPool(1);

	private FetchImageTask(Context context) {
	    super(context, EXECUTOR);
	}

	@Override
	protected void onException(Exception e) throws RuntimeException {
	    Log.d(TAG, "Image load failed", e);
	}
    }

    private final float cornerRadius;

    private final Map<Object, BitmapDrawable> loaded = new LinkedHashMap<Object, BitmapDrawable>(
	    CACHE_SIZE, 1.0F) {

	private static final long serialVersionUID = -4191624209581976720L;

	@Override
	protected boolean removeEldestEntry(
		Map.Entry<Object, BitmapDrawable> eldest) {
	    return size() >= CACHE_SIZE;
	}
    };

    private final Context context;

    private final File ImageDir;

    private final Drawable loadingImage;

    private final Options options;

    /**
     * Create Image helper
     * 
     * @param context
     */
    @Inject
    public ImageLoader(final Context context) {
	this.context = context;

	loadingImage = context.getResources().getDrawable(
		drawable.gravatar_icon);

	ImageDir = new File(context.getCacheDir(), "Images/github.com");
	if (!ImageDir.isDirectory())
	    ImageDir.mkdirs();

	float density = context.getResources().getDisplayMetrics().density;
	cornerRadius = CORNER_RADIUS_IN_DIP * density;

	options = new Options();
	options.inDither = false;
	options.inPreferredConfig = ARGB_8888;
    }

    private BitmapDrawable getImageBy(final String userId, final String filename) {
	File ImageFile = new File(ImageDir + "/" + userId, filename);

	if (!ImageFile.exists() || ImageFile.length() == 0)
	    return null;

	Bitmap bitmap = decode(ImageFile);
	if (bitmap != null)
	    return new BitmapDrawable(context.getResources(), bitmap);
	else {
	    ImageFile.delete();
	    return null;
	}
    }

    private void deleteCachedUserImages(final File userImageDir) {
	if (userImageDir.isDirectory())
	    for (File userImage : userImageDir.listFiles())
		userImage.delete();
	userImageDir.delete();
    }

    private Bitmap decode(final File file) {
	return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    private String getImageFilenameForUrl(final String ImageUrl) {
	return GravatarUtils.getHash(ImageUrl);
    }

    /**
     * Fetch Image from URL
     * 
     * @param url
     * @param cachedImageFilename
     * @return bitmap
     */
    protected BitmapDrawable fetchImage(final String url, final String userId,
	    final String cachedImageFilename) {
	File userImageDir = new File(ImageDir, userId);
	deleteCachedUserImages(userImageDir);
	userImageDir.mkdirs();

	File rawImage = new File(userImageDir, cachedImageFilename + "-raw");
	HttpRequest request = HttpRequest.get(url);
	if (request.ok())
	    request.receive(rawImage);

	if (!rawImage.exists() || rawImage.length() == 0)
	    return null;

	Bitmap bitmap = decode(rawImage);
	if (bitmap == null) {
	    rawImage.delete();
	    return null;
	}

	bitmap = ImageUtils.roundCorners(bitmap, cornerRadius);
	if (bitmap == null) {
	    rawImage.delete();
	    return null;
	}

	File roundedImage = new File(userImageDir, cachedImageFilename);
	FileOutputStream output = null;
	try {
	    output = new FileOutputStream(roundedImage);
	    if (bitmap.compress(PNG, 100, output))
		return new BitmapDrawable(context.getResources(), bitmap);
	    else
		return null;
	} catch (IOException e) {
	    Log.d(TAG, "Exception writing rounded Image", e);
	    return null;
	} finally {
	    if (output != null)
		try {
		    output.close();
		} catch (IOException e) {
		    // Ignored
		}
	    rawImage.delete();
	}
    }

    /**
     * Sets the logo on the {@link ActionBar} to the user's Image.
     * 
     * @param actionBar
     * @param user
     * @return this helper
     */
    public ImageLoader bind(final ActionBar actionBar, final User user) {
	return bind(actionBar, new AtomicReference<User>(user));
    }

    /**
     * Sets the logo on the {@link ActionBar} to the user's Image.
     * 
     * @param actionBar
     * @param userReference
     * @return this helper
     */
    public ImageLoader bind(final ActionBar actionBar,
	    final AtomicReference<User> userReference) {
	if (userReference == null)
	    return this;

	final User user = userReference.get();
	final String userId = getId(user);
	if (userId == null)
	    return this;

	final String ImageUrl = user.getImageUrl();
	if (TextUtils.isEmpty(ImageUrl))
	    return this;

	BitmapDrawable loadedImage = loaded.get(userId);
	if (loadedImage != null) {
	    actionBar.setLogo(loadedImage);
	    return this;
	}

	new FetchImageTask(context) {

	    @Override
	    public BitmapDrawable call() throws Exception {
		final String ImageFilename = getImageFilenameForUrl(getImageUrl(user));
		final BitmapDrawable image = getImageBy(userId, ImageFilename);
		if (image != null)
		    return image;
		else
		    return fetchImage(ImageUrl, userId, ImageFilename);
	    }

	    @Override
	    protected void onSuccess(BitmapDrawable image) throws Exception {
		if (userId.equals(getId(userReference.get())))
		    actionBar.setLogo(image);
	    }
	}.execute();

	return this;
    }

    private ImageLoader setImage(final Drawable image, final ImageView view) {
	return setImage(image, view, null);
    }

    private ImageLoader setImage(final Drawable image, final ImageView view,
	    Object tag) {
	view.setImageDrawable(image);
	view.setTag(id.iv_avatar, tag);
	view.setVisibility(VISIBLE);
	return this;
    }

    private String getImageUrl(String id) {
	if (!TextUtils.isEmpty(id))
	    return "https://secure.grImage.com/Image/" + id + "?d=404";
	else
	    return null;
    }

    private String getImageUrl(User user) {
	String ImageUrl = user.getImageUrl();
	if (TextUtils.isEmpty(ImageUrl)) {
	    String imageUrl = user.getImageUrl();
	    if (TextUtils.isEmpty(imageUrl))
		imageUrl = GravatarUtils.getHash(user.getEmail());
	    ImageUrl = getImageUrl(imageUrl);
	}
	return ImageUrl;
    }

    /**
     * Bind view to image at URL
     * 
     * @param view
     * @param user
     * @return this helper
     */
    public ImageLoader bind(final ImageView view, final User user) {
	final String userId = getId(user);
	if (userId == null)
	    return setImage(loadingImage, view);

	final String ImageUrl = getImageUrl(user);
	if (TextUtils.isEmpty(ImageUrl))
	    return setImage(loadingImage, view);

	BitmapDrawable loadedImage = loaded.get(userId);
	if (loadedImage != null)
	    return setImage(loadedImage, view);

	setImage(loadingImage, view, userId);
	fetchImageTask(ImageUrl, userId, view).execute();

	return this;
    }

    private String getId(final User user) {
	if (user == null)
	    return null;

	long id = user.getId();
	if (id > 0)
	    return Long.toString(id);

	return GravatarUtils.getHash(user.getEmail());
    }

    private FetchImageTask fetchImageTask(final String ImageUrl,
	    final String userId, final ImageView view) {
	return new FetchImageTask(context) {

	    @Override
	    public BitmapDrawable call() throws Exception {
		if (!userId.equals(view.getTag(id.iv_avatar)))
		    return null;

		final String ImageFilename = getImageFilenameForUrl(ImageUrl);
		final BitmapDrawable image = getImageBy(userId, ImageFilename);
		if (image != null)
		    return image;
		else
		    return fetchImage(ImageUrl, userId, ImageFilename);
	    }

	    @Override
	    protected void onSuccess(final BitmapDrawable image)
		    throws Exception {
		if (image == null)
		    return;
		loaded.put(userId, image);
		if (userId.equals(view.getTag(id.iv_avatar)))
		    setImage(image, view);
	    }
	};
    }
}
