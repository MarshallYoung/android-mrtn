package com.yuchengtech.mrtn.base.manager;

import android.app.Activity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yuchengtech.mrtn.base.YXAPI;
import com.yuchengtech.mrtn.base.YXParams;
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

    private RequestQueue mQueue;

    /**
     * 构造方法
     *
     * @param mContext
     */
    public NetworkManager(final Activity context) {
        super();
        this.mQueue = Volley.newRequestQueue(context);
    }

    /**
     * 登录
     *
     * @param userInfo 登录信息
     * @param listener 登录成功执行方法
     */
    public void Login(final LoginRequest userInfo,
                      final NetworkListener listener, final CookiesListener cookieListener) {
        // 加密密码
        final String encryptPwd = SystemUtil.getMD5(userInfo.password);
        StringRequest stringRequest = new StringRequest(Method.POST,
                YXAPI.URL_LOGIN, new Response.Listener<String>() {
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
                map.put(YXParams.Login.USERNAME, userInfo.username);
                map.put(YXParams.Login.PASSWORD, encryptPwd);
                return map;
            }

            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                try {
                    Map<String, String> responseHeaders = response.headers;
                    // 输出日志
                    for (Map.Entry<String, String> entry : responseHeaders
                            .entrySet()) {
                        Log.e("==" + entry.getKey(), entry.getValue());
                    }
                    String rawCookies = responseHeaders.get("Set-Cookie");// cookie值
                    String sid = StringUtil.getSId(rawCookies);
                    String dataString = new String(response.data, "UTF-8");// 返回值
                    Log.e("==sid==", sid);
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

    // 回调
    public interface NetworkListener {
        public void onSuccess(String response);

        public void onFail(VolleyError error);
    }

    // 操作Cookies的监听
    public interface CookiesListener {
        public void onSId(String sid);
    }
}