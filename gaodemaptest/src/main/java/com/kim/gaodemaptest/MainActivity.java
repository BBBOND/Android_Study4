package com.kim.gaodemaptest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AMapLocationListener {

    private static final String TAG = "MainActivity";
    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption = null;
    private TextView mainTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainTV = (TextView) findViewById(R.id.main_tv);

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "定位启动");
        mLocationClient.startLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "定位停止");
        mLocationClient.stopLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            String str = "";
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                Log.i(TAG, "当前定位结果来源: " + String.valueOf(aMapLocation.getLocationType()));//获取当前定位结果来源，如网络定位结果，详见定位类型表
                Log.i(TAG, "纬度: " + aMapLocation.getLatitude());//获取纬度
                Log.i(TAG, "经度: " + aMapLocation.getLongitude());//获取经度
                Log.i(TAG, "精度信息: " + aMapLocation.getAccuracy());//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                Date date = new Date(aMapLocation.getTime());
                Log.i(TAG, "定位时间: " + df.format(date));//定位时间
                Log.i(TAG, "地址: " + aMapLocation.getAddress());//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                Log.i(TAG, "国家信息: " + aMapLocation.getCountry());//国家信息
                Log.i(TAG, "省信息: " + aMapLocation.getProvince());//省信息
                Log.i(TAG, "城市信息: " + aMapLocation.getCity());//城市信息
                Log.i(TAG, "城区信息: " + aMapLocation.getDistrict());//城区信息
                Log.i(TAG, "街道信息: " + aMapLocation.getStreet());//街道信息
                Log.i(TAG, "街道门牌号信息: " + aMapLocation.getStreetNum());//街道门牌号信息
                Log.i(TAG, "城市编码: " + aMapLocation.getCityCode());//城市编码
                Log.i(TAG, "地区编码: " + aMapLocation.getAdCode());//地区编码
//                aMapLocation.getAOIName();//获取当前定位点的AOI信息

                str += "当前定位结果来源: " + String.valueOf(aMapLocation.getLocationType());
                str += "\n纬度: " + aMapLocation.getLatitude();
                str += "\n经度: " + aMapLocation.getLongitude();
                str += "\n精度信息: " + aMapLocation.getAccuracy();
                str += "\n定位时间: " + df.format(date);
                str += "\n地址: " + aMapLocation.getAddress();
                str += "\n国家信息: " + aMapLocation.getCountry();
                str += "\n省信息: " + aMapLocation.getProvince();
                str += "\n城市信息: " + aMapLocation.getCity();
                str += "\n城区信息: " + aMapLocation.getDistrict();
                str += "\n街道信息: " + aMapLocation.getStreet();
                str += "\n街道门牌号信息: " + aMapLocation.getStreetNum();
                str += "\n城市编码: " + aMapLocation.getCityCode();
                str += "\n地区编码: " + aMapLocation.getAdCode();
                mainTV.setText(str);
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
                str = "AmapError" + "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo();
                mainTV.setText(str);
            }
        }
    }
}
