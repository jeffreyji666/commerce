package mobile.intellect.commerceapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.*;
import mobile.intellect.commerceapp.R;
import mobile.intellect.commerceapp.app.AppContext;
import mobile.intellect.commerceapp.lbs.GPSLocationActivity;
import mobile.intellect.commerceapp.lbs.NetLocationActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends FragmentActivity {
    private static final String TAG = "HomeActivity";

    private AppContext ctx;

    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mDatas;

    private Button location;
    private ImageButton qrcode;

    private RadioGroup footer;
    private RadioButton mHome;
    private RadioButton mGroupon;
    private RadioButton mMarket;
    private RadioButton mMine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);

        ctx = (AppContext) getApplication();
        initView();
    }

    private void initView() {
        location = (Button) findViewById(R.id.location);
        qrcode = (ImageButton) findViewById(R.id.qrcode);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        footer = (RadioGroup) findViewById(R.id.footer);
        mHome = (RadioButton) findViewById(R.id.mHome);
        mMarket = (RadioButton) findViewById(R.id.mMarket);
        mGroupon = (RadioButton) findViewById(R.id.mGroupon);
        mMine = (RadioButton) findViewById(R.id.mMine);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type = ctx.getNetworkType();
                if (type == 0) {
                } else {
                    Intent intent = type == 1 ? new Intent(v.getContext(), NetLocationActivity.class) : new Intent(v.getContext(), GPSLocationActivity.class);
                    v.getContext().startActivity(intent);
                }
            }
        });
        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CommodityActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        mDatas = new ArrayList<Fragment>();
        HomeFragment home = new HomeFragment();
        MarketFragment market = new MarketFragment();
        GrouponFragment groupon = new GrouponFragment();
        MineFragment mine = new MineFragment();
        mDatas.add(home);
        mDatas.add(market);
        mDatas.add(groupon);
        mDatas.add(mine);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mDatas.get(i);
            }

            @Override
            public int getCount() {
                return mDatas.size();
            }
        };

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float offset, int offsetPixel) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        mHome.setChecked(true);
                        break;
                    case 1:
                        mMarket.setChecked(true);
                        break;
                    case 2:
                        mGroupon.setChecked(true);
                        break;
                    case 3:
                        mMine.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //绑定一个匿名监听器
        footer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                //获取变更后的选中项的ID
                int radioButtonId = arg0.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) findViewById(radioButtonId);
                //更新文本内容，以符合选中项
                int currentIndex = 0;
                if (rb.getText().equals(getResources().getString(R.string.market))) {
                    currentIndex = 1;
                }
                if (rb.getText().equals(getResources().getString(R.string.groupon))) {
                    currentIndex = 2;
                }
                if (rb.getText().equals(getResources().getString(R.string.mine))) {
                    currentIndex = 3;
                }
                mViewPager.setCurrentItem(currentIndex);
            }
        });
    }
}
