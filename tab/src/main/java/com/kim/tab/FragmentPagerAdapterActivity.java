package com.kim.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 如微信
 * Created by 伟阳 on 2016/2/16.
 */
public class FragmentPagerAdapterActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private FragmentPagerAdapter adapter;
    private List<Fragment> fragments = new ArrayList<>();
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

        setSelect(0);
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

        fragments = new ArrayList<>();
        Fragment weiXinFragment = new WeiXinFragment();
        Fragment friendFragment = new FriendFragment();
        Fragment addressFragment = new AddressFragment();
        Fragment settingFragment = new SettingFragment();
        fragments.add(weiXinFragment);
        fragments.add(friendFragment);
        fragments.add(addressFragment);
        fragments.add(settingFragment);

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
        };
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTabPressedImg(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
                setSelect(0);
                break;
            case R.id.tab_friend:
                setSelect(1);
                break;
            case R.id.tab_address:
                setSelect(2);
                break;
            case R.id.tab_setting:
                setSelect(3);
                break;
            default:
                break;
        }
    }

    private void setSelect(int i) {
        setTabPressedImg(i);
        viewPager.setCurrentItem(i);
    }

    private void setTabPressedImg(int i) {
        switch (i) {
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
