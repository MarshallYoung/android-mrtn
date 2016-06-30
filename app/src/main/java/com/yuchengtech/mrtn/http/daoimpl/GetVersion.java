package com.yuchengtech.mrtn.http.daoimpl;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.yuchengtech.mrtn.base.YXAPI;
import com.yuchengtech.mrtn.http.IHttpURLs;
import com.yuchengtech.mrtn.http.request.HttpConnect;
import com.yuchengtech.mrtn.http.request.IHttpListener;
import com.yuchengtech.mrtn.http.tools.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * 获取版本信息
 *
 * @author Jin Xi
 */
public class GetVersion implements YXAPI, IHttpListener {

    private String TAG = "GetVersion";

    private HttpConnect conn;
    private IHttpURLs listener;

    private Context mContext;
    private Handler mHandler;

    public GetVersion(IHttpURLs listener, Context context, Handler handler) {
        super();
        this.listener = listener;
        this.mContext = context;
        this.mHandler = handler;
        if (conn == null) {
            // conn = new HttpConnect(GETVERSION, this);
            conn.start();
        }
    }

    public void getVersion(String hash, String cacheTime) {
        byte[] data = null;
        try {
            JSONObject params = new JSONObject();
            params.put("hash", hash);
            params.put("cacheTime", cacheTime);
            // Log.d(TAG, GETVERSION + " - " + params.toString());
            data = Tools.decodeToUTF8(params.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        conn.setPostData(data);
    }

    public void handleError(String err) throws IOException {
        listener.handleErrorInfo(err);
    }

    public void decode(String in) throws IOException {
        unmashal(in);
    }

    private void unmashal(String in) {
        try {
            String temp = in;// Tools.seekSep(in);
            if (temp == null || temp.equals("")) {
                listener.handleErrorInfo("数据解析异常");
                return;
            }
            Log.d(TAG, temp.toString());

            // JSONObject obj = new JSONObject(temp);
            // int code = obj.optInt("code");
            // if (code == 0) {
            // Version version = new Version();
            // version.parseVersion(obj);
            // listener.despatch(version);
            //
            // // 10018 没有数据
            // } else if (code == 10018) {
            // String result = obj.optString("msg");
            // listener.despatch(code, result);
            // } else {
            // AppConfig.CodeOperate(code, mContext, mHandler);
            // String result = obj.optString("msg");
            // listener.handleErrorInfo(result);
            // }
        } catch (Exception e) {
            listener.handleErrorInfo(DATA_MODO_ERR);
            e.printStackTrace();
        }
    }

    @Override
    public void decode(InputStream in) throws IOException {
        // TODO Auto-generated method stub

    }
}