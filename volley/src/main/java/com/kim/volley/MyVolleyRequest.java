package com.kim.volley;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by 伟阳 on 2016/2/14.
 */
public class MyVolleyRequest {
    public static StringRequest request;
    public static Context context;

    public static void RequestGet(Context context, String url, String tag, MyVolleyInterface myVolleyInterface) {
        MyApplication.getHttpQueues().cancelAll(tag);
        request = new StringRequest(Request.Method.GET, url, myVolleyInterface.loadingListener(), myVolleyInterface.errorListener());
        request.setTag(tag);
        MyApplication.getHttpQueues().add(request);
        MyApplication.getHttpQueues().start();
    }

    public static void RequestPost(Context context, String url, String tag, final Map<String, String> map, MyVolleyInterface myVolleyInterface) {
        MyApplication.getHttpQueues().cancelAll(tag);
        request = new StringRequest(Request.Method.POST, url, myVolleyInterface.loadingListener(), myVolleyInterface.errorListener()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        request.setTag(tag);
        MyApplication.getHttpQueues().add(request);
        MyApplication.getHttpQueues().start();
    }
}
