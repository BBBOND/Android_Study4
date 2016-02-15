package com.kim.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 伟阳 on 2016/2/15.
 */
public class FragmentTabActivity extends FragmentActivity implements View.OnClickListener {

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

    //Fragment
    private Fragment weiXinFragment;
    private Fragment friendFragment;
    private Fragment addressFragment;
    private Fragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fragment);

        findViewById(R.id.toFragmentTab).setVisibility(View.GONE);

        initView();
        initEvent();

        setSelect(0);
    }

    private void initView() {
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
    }

    private void initEvent() {
        tabWeiXin.setOnClickListener(this);
        tabFriend.setOnClickListener(this);
        tabAddress.setOnClickListener(this);
        tabSetting.setOnClickListener(this);
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

    private void restImg() {
        weiXinImg.setImageResource(imgsNormal[0]);
        friendImg.setImageResource(imgsNormal[1]);
        addressImg.setImageResource(imgsNormal[2]);
        settingImg.setImageResource(imgsNormal[3]);
    }

    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        switch (i) {
            case 0:
                if (weiXinFragment == null) {
                    weiXinFragment = new WeiXinFragment();
                    transaction.add(R.id.frameLayout, weiXinFragment);
                }
                transaction.show(weiXinFragment);
                weiXinImg.setImageResource(imgsPressed[0]);
                break;
            case 1:
                if (friendFragment == null) {
                    friendFragment = new FriendFragment();
                    transaction.add(R.id.frameLayout, friendFragment);
                }
                transaction.show(friendFragment);
                friendImg.setImageResource(imgsPressed[1]);
                break;
            case 2:
                if (addressFragment == null) {
                    addressFragment = new AddressFragment();
                    transaction.add(R.id.frameLayout, addressFragment);
                }
                transaction.show(addressFragment);
                addressImg.setImageResource(imgsPressed[2]);
                break;
            case 3:
                if (settingFragment == null) {
                    settingFragment = new SettingFragment();
                    transaction.add(R.id.frameLayout, settingFragment);
                }
                transaction.show(settingFragment);
                settingImg.setImageResource(imgsPressed[3]);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (weiXinFragment != null) {
            transaction.hide(weiXinFragment);
        }
        if (friendFragment != null) {
            transaction.hide(friendFragment);
        }
        if (addressFragment != null) {
            transaction.hide(addressFragment);
        }
        if (settingFragment != null) {
            transaction.hide(settingFragment);
        }
    }
}
