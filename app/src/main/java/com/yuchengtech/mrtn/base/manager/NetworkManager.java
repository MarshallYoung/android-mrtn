package com.yuchengtech.mrtn.base.manager;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yuchengtech.mrtn.base.YXAPI;
import com.yuchengtech.mrtn.base.util.LogUtil;
import com.yuchengtech.mrtn.base.util.StringUtil;
import com.yuchengtech.mrtn.login.bean.LoginRequest;
import com.yuchengtech.mrtn.utils.SystemUtil;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 网络管理
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @date 2016年4月22日 下午4:53:33
 */
public class NetworkManager {

    private static final String TAG = "NetworkManager";
    private static RequestQueue mQueue;

    public NetworkManager(Context context) {
        this.mQueue = Volley.newRequestQueue(context);
    }

    /**
     * 登录
     *
     * @param userInfo 登录信息
     * @param listener 登录成功执行方法
     */
    public void Login(LoginRequest userInfo, final NetworkListener listener, final CookiesListener cookieListener) {
        final String username = userInfo.username;
        final String pwd = userInfo.pwd;
        // 加密
        final String encryptPwd = SystemUtil.getMD5(pwd);
        StringRequest stringRequest = new StringRequest(Method.POST, YXAPI.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {// 连接错误的时候出现提示
                listener.onFail(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("username", username);
                map.put("password", encryptPwd);
                return map;
            }

            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                try {
                    Map<String, String> responseHeaders = response.headers;
                    // 输出日志
                    for (Map.Entry<String, String> entry : responseHeaders.entrySet()) {
                        LogUtil.e(TAG, "key:  " + entry.getKey() + "  value:  " + entry.getValue());
                    }
                    String rawCookies = responseHeaders.get("Set-Cookie");// cookie值
                    String sid = StringUtil.getSId(rawCookies);
                    String dataString = new String(response.data, "UTF-8");// 返回值
                    cookieListener.onSId(sid);
                    return Response.success(dataString,
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };
        mQueue.add(stringRequest);
    }

    /**
     * 下载xml文档,用于初始化界面
     *
     * @param fileName 文件名
     * @param listener 回调
     */
    public void downloadXml(String fileName, final NetworkListener listener) {
        String URL = YXAPI.URL_DOWNLOADXML + fileName;
        StringRequest stringRequest = new StringRequest(Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogUtil.e(TAG, "downloadXml:  " + response);
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {// 连接错误的时候出现提示
                LogUtil.e(TAG, "volley error:  " + error.toString());
                listener.onFail(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("android_request", "1");
                return map;
            }
        };
        mQueue.add(stringRequest);
    }

    /**
     * 下载图片
     *
     * @param url      图片地址
     * @param listener 回调
     */
    public void loadImage(String url, final NetworkListener listener) {
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                listener.onSuccess(response);
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFail(error);
            }
        });
        mQueue.add(imageRequest);
    }

    // 回调
    public interface NetworkListener {
        void onSuccess(Object response);

        void onFail(VolleyError error);
    }

    // 操作Cookies的监听
    public interface CookiesListener {
        void onSId(String sid);
    }
}