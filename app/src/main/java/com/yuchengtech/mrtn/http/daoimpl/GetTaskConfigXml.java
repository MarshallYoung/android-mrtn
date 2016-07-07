package com.yuchengtech.mrtn.http.daoimpl;

import android.content.Context;

import com.yuchengtech.mrtn.base.YXAPI;
import com.yuchengtech.mrtn.base.util.LogUtil;
import com.yuchengtech.mrtn.http.IHttpURLs;
import com.yuchengtech.mrtn.http.request.HttpConnectGet;
import com.yuchengtech.mrtn.http.request.IHttpListener;

import org.apache.http.NameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GetTaskConfigXml implements IHttpListener {

    private static final String LOG_TAG = "GetTaskConfigXml";
    private String TAG = "resources/upload/xml/";
    private HttpConnectGet conn;
    private IHttpURLs listener;
    private String fileName;

    public GetTaskConfigXml(IHttpURLs httpURLs, Context context, String fileName) {
        super();
        listener = httpURLs;
        this.fileName = fileName;
        if (conn == null) {
            conn = new HttpConnectGet(YXAPI.URL + TAG + fileName, this);
            LogUtil.e(LOG_TAG, YXAPI.URL + TAG + fileName);
        }
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
            // Log.d(TAG, temp);
            listener.despatch(temp, fileName);
        } catch (Exception e) {
            listener.handleErrorInfo("err");
            e.printStackTrace();
        }
    }

    public void request(String t) {

        List<NameValuePair> listPairs = new ArrayList<NameValuePair>();

        conn.setParams(listPairs);
        conn.start();
    }

    @Override
    public void decode(InputStream in) throws IOException {
        // TODO Auto-generated method stub

    }

}
