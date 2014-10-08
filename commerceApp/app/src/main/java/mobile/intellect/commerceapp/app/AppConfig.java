package mobile.intellect.commerceapp.app;

import java.io.File;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;

public class AppConfig {
    public final static String CONF_APP_UNIQUEID = "APP_UNIQUEID";

    public final static String SAVE_IMAGE_PATH = "save_image_path";
    public final static String DEFAULT_SAVE_IMAGE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "Commerce"
            + File.separator;

    private static final Properties properties = new Properties();

    public static String getUserAgent(AppContext appContext) {
        StringBuilder ua = new StringBuilder("Commerce");
        ua.append('/' + appContext.getPackageInfo().versionName + '_'
                + appContext.getPackageInfo().versionCode);// App版本
        ua.append("/Android");// 手机系统平台
        ua.append("/" + android.os.Build.VERSION.RELEASE);// 手机系统版本
        ua.append("/" + android.os.Build.MODEL); // 手机型号
        ua.append("/" + appContext.getAppId());// 客户端唯一标识
        return ua.toString();
    }

    public static String getString(String key) {
        return properties.getProperty(key);
    }

    public static void set(String key, Object value) {
        properties.put(key, value);
    }

    public static Integer getInt(String key) {
        return Integer.parseInt(getString(key));
    }

    public static Boolean getBool(String key) {
        return getString(key).toLowerCase(Locale.CHINA).trim().equals("true");
    }

    /**
     * 获取Preference设置
     */
    public static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void put(Context ctx, Map<String, String> params) {
        SharedPreferences prefs = getSharedPreferences(ctx);
        SharedPreferences.Editor editor = prefs.edit();
        Iterator<String> keys = params.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            editor.putString(key, params.get(key));
        }
        editor.commit();
    }

    public static String get(Context ctx, String key) {
        SharedPreferences prefs = getSharedPreferences(ctx);
        return prefs.getString(key, "");
    }
}
