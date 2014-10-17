package mobile.intellect.commerceapp.ui;

import android.app.Activity;
import android.os.Bundle;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by you on 10/17/14.
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobclickAgent.onEvent(this, this.toString());  //捕捉未捕捉异常，通过调用这个方法，可以收集引起程序异常退出的错误信息，如不用可注释
    }

    /**
     * 在重载函数中为统计代码 *
     */
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
}
