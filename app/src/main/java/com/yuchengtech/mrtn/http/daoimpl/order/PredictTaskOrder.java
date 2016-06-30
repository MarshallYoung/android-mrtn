package com.yuchengtech.mrtn.http.daoimpl.order;

import android.util.Log;

import com.yuchengtech.mrtn.base.YXAPI;
import com.yuchengtech.mrtn.http.IHttpURLs;
import com.yuchengtech.mrtn.http.request.HttpConnectPost;
import com.yuchengtech.mrtn.http.request.IHttpListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PredictTaskOrder implements YXAPI, IHttpListener {
    private String TAG = "taskOrderService/PredictTaskOrder";
    private HttpConnectPost conn;
    private IHttpURLs listener;

    public PredictTaskOrder(IHttpURLs listener) {
        super();
        this.listener = listener;
        if (conn == null) {
            conn = new HttpConnectPost(URL + TAG, this);
        }
    }

    public void request(String taskId, String type, String date) {
        List<NameValuePair> listPairs = new ArrayList<NameValuePair>();
        if (taskId != null && !taskId.isEmpty()) {
            listPairs.add(new BasicNameValuePair("taskId", taskId));
        }
        if (type != null && !type.isEmpty()) {
            listPairs.add(new BasicNameValuePair("type", type));
        }
        if (date != null && !date.isEmpty()) {
            listPairs.add(new BasicNameValuePair("date", date));
        }
        conn.setParams(listPairs);
        conn.start();
    }

    @Override
    public void handleError(String err) throws IOException {
        listener.handleErrorInfo(err);
    }

    @Override
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
            Log.d(TAG, temp);
            JSONObject obj = new JSONObject(temp);
            boolean success = obj.optBoolean("success");
            if (success) {
                listener.despatch("任务单保存成功!");
            } else {
                listener.handleErrorInfo("任务单保存失败");
            }
        } catch (Exception e) {
            listener.handleErrorInfo(DATA_MODO_ERR);
            e.printStackTrace();
        }
    }

    @Override
    public void decode(InputStream in) throws IOException {

    }

}
