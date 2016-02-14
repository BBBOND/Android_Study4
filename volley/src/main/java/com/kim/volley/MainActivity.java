package com.kim.volley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 1. Volley的get和post请求方式的使用
 * 2. Volley的网络请求队列的创建和取消队列请求
 * 3. Volley与Activity生命周期的联动
 * 4. Volley的简单二次回调封装
 */
public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        //设置支持js
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

        });
        //设置不掉用外部浏览器
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        volleyGet();
//        volleyPost();
//        myVolleyGet();
//        myVolleyPost();
    }

    private void volleyGet() {
        String url = "http://lolbox.duowan.com/playerDetail.php?serverName=%E7%BD%91%E9%80%9A%E4%B8%80&playerName=BBBOND";
        //JsonObjectRequest、JsonArrayRequest
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("MainActivity", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        request.setTag("volleyGet");
        MyApplication.getHttpQueues().add(request);
    }

    private void volleyPost() {
        String url = "http://lolbox.duowan.com/playerDetail.php?";

//        Map<String, String> map = new HashMap<>();
//        JSONObject object = new JSONObject(map);

//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//              // TODO: 2016/2/14
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("MainActivity", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("serverName", "%E7%BD%91%E9%80%9A%E4%B8%80");
                map.put("playerName", "BBBOND");
                return map;
            }
        };
        //serverName=%E7%BD%91%E9%80%9A%E4%B8%80&playerName=BBBOND
        request.setTag("volleyPost");
        MyApplication.getHttpQueues().add(request);
    }

    private void myVolleyGet() {
        String url = "http://lolbox.duowan.com/playerDetail.php?serverName=%E7%BD%91%E9%80%9A%E4%B8%80&playerName=BBBOND";
        MyVolleyRequest.RequestGet(MainActivity.this, url, "myVolleyGet", new MyVolleyInterface() {
            @Override
            public void onSuccess(String result) {
                Log.i("MainActivity", result);
            }

            @Override
            public void onError(VolleyError error) {
                error.printStackTrace();
            }
        });
    }

    private void myVolleyPost() {
        String url = "http://lolbox.duowan.com/playerDetail.php?";
        Map<String, String> map = new HashMap<>();
        map.put("serverName", "%E7%BD%91%E9%80%9A%E4%B8%80");
        map.put("playerName", "BBBOND");
        MyVolleyRequest.RequestPost(MainActivity.this, url, "myVolleyPost", map, new MyVolleyInterface() {
            @Override
            public void onSuccess(String result) {
                Log.i("MainActivity", result);
            }

            @Override
            public void onError(VolleyError error) {
                error.printStackTrace();
            }
        });
    }


    @Override
    protected void onStop() {
        MyApplication.getHttpQueues().cancelAll("volleyGet");
        MyApplication.getHttpQueues().cancelAll("volleyPost");
        MyApplication.getHttpQueues().cancelAll("myVolleyGet");
        MyApplication.getHttpQueues().cancelAll("myVolleyPost");
        super.onStop();
    }
}
