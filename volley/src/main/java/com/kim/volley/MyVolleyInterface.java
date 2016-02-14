package com.kim.volley;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by 伟阳 on 2016/2/14.
 */
public abstract class MyVolleyInterface {
    public Context context;
    public Response.Listener<String> listener;
    public Response.ErrorListener errorListener;

    public MyVolleyInterface() {
    }

    public MyVolleyInterface(Context context, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this.context = context;
        this.listener = listener;
        this.errorListener = errorListener;
    }

    public abstract void onSuccess(String result);

    public abstract void onError(VolleyError error);

    public Response.Listener<String> loadingListener() {
        listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onSuccess(response);
            }
        };
        return listener;
    }

    public Response.ErrorListener errorListener() {
        errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onError(error);
            }
        };
        return errorListener;
    }
}
