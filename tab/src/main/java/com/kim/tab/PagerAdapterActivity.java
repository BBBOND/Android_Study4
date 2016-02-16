package com.kim.tab;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapterActivity extends Activity implements View.OnClickListener {

    private ViewPager viewPager;
    private PagerAdapter adapter;
    private List<View> views = new ArrayList<>();
    int[] imgsNormal = {
            R.drawable.tab_weixin_normal,
            R.drawable.tab_find_frd_normal,
            R.drawable.tab_address_normal,
            R.drawable.tab_settings_normal
    };
    int[] imgsPressed = {
            R.drawable.tab_weixin_pressed,
            R.drawable.tab_find_frd_pressed,
            R.drawable.tab_address_pressed,
            R.drawable.tab_settings_pressed
    };

    //Tab
    private LinearLayout tabWeiXin;
    private LinearLayout tabFriend;
    private LinearLayout tabAddress;
    private LinearLayout tabSetting;

    //ImageButton
    private ImageButton weiXinImg;
    private ImageButton friendImg;
    private ImageButton addressImg;
    private ImageButton settingImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_viewpager);

        initView();
        initEvent();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //Tab
        tabWeiXin = (LinearLayout) findViewById(R.id.tab_weixin);
        tabFriend = (LinearLayout) findViewById(R.id.tab_friend);
        tabAddress = (LinearLayout) findViewById(R.id.tab_address);
        tabSetting = (LinearLayout) findViewById(R.id.tab_setting);
        //ImageButton
        weiXinImg = (ImageButton) findViewById(R.id.tab_weixin_img);
        friendImg = (ImageButton) findViewById(R.id.tab_friend_img);
        addressImg = (ImageButton) findViewById(R.id.tab_address_img);
        settingImg = (ImageButton) findViewById(R.id.tab_setting_img);

        LayoutInflater inflater = LayoutInflater.from(this);
        View tab1 = inflater.inflate(R.layout.tab1, null);
        View tab2 = inflater.inflate(R.layout.tab2, null);
        View tab3 = inflater.inflate(R.layout.tab3, null);
        View tab4 = inflater.inflate(R.layout.tab4, null);
        views.add(tab1);
        views.add(tab2);
        views.add(tab3);
        views.add(tab4);

        adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = views.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(views.get(position));
            }
        };
        viewPager.setAdapter(adapter);
    }

    private void initEvent() {
        tabWeiXin.setOnClickListener(this);
        tabFriend.setOnClickListener(this);
        tabAddress.setOnClickListener(this);
        tabSetting.setOnClickListener(this);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                restImg();
                switch (viewPager.getCurrentItem()) {
                    case 0:
                        weiXinImg.setImageResource(imgsPressed[0]);
                        break;
                    case 1:
                        friendImg.setImageResource(imgsPressed[1]);
                        break;
                    case 2:
                        addressImg.setImageResource(imgsPressed[2]);
                        break;
                    case 3:
                        settingImg.setImageResource(imgsPressed[3]);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        restImg();
        switch (v.getId()) {
            case R.id.tab_weixin:
                viewPager.setCurrentItem(0);
                weiXinImg.setImageResource(imgsPressed[0]);
                break;
            case R.id.tab_friend:
                viewPager.setCurrentItem(1);
                friendImg.setImageResource(imgsPressed[1]);
                break;
            case R.id.tab_address:
                viewPager.setCurrentItem(2);
                addressImg.setImageResource(imgsPressed[2]);
                break;
            case R.id.tab_setting:
                viewPager.setCurrentItem(3);
                settingImg.setImageResource(imgsPressed[3]);
                break;
            default:
                break;
        }
    }

    private void restImg() {
        weiXinImg.setImageResource(imgsNormal[0]);
        friendImg.setImageResource(imgsNormal[1]);
        addressImg.setImageResource(imgsNormal[2]);
        settingImg.setImageResource(imgsNormal[3]);
    }
}
