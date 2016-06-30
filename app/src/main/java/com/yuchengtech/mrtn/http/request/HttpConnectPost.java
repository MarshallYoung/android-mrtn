package com.yuchengtech.mrtn.http.request;

import com.yuchengtech.mrtn.base.MrtnApplication;
import com.yuchengtech.mrtn.base.util.LogUtil;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

/**
 * 数据请求类
 *
 * @author yanchunhan
 * @created 2014-04-02
 */
public class HttpConnectPost {

    private static final int CONNECTION_TIMEOUT = 30 * 1000; // 设置超时时间
    private HttpThread httpthread;
    private volatile boolean bRun = true;
    private String url;
    private IHttpListener processor;
    private HttpClient client;
    private int responsecode;
    private String errStr = null;
    private List<NameValuePair> params = null;

    public HttpConnectPost(String url, IHttpListener processor) {
        this.url = url;
        this.processor = processor;
    }

    /**
     * 发送数据
     *
     * @param thread
     * @throws Throwable
     */
    private void send() throws Throwable {
        boolean bErr = false;
        try {
            HttpPost httpRequest = new HttpPost(url);
            client = new DefaultHttpClient();
            // 请求超时
            client.getParams()
                    .setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                            CONNECTION_TIMEOUT);
            // 发出HTTP request
            httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            httpRequest.setHeader("android_request", "1");
            httpRequest.setHeader("Content-Type",
                    "application/x-www-form-urlencoded"); // 转码
            httpRequest.setHeader("Cookie", "sid=" + MrtnApplication.sid);
            HttpResponse httpResponse = client.execute(httpRequest);
            responsecode = httpResponse.getStatusLine().getStatusCode();
            switch (responsecode) {
                case HttpURLConnection.HTTP_OK:
                    String strResult = EntityUtils.toString(
                            httpResponse.getEntity(), HTTP.UTF_8);
                    LogUtil.e("服务器返回的原始数据是", strResult);
                    try {
                        processor.decode(strResult);
                    } catch (Exception e) {
                        e.printStackTrace();
                        processor.decode(strResult);
                    }
                    break;
                /** 网关超时 */
                case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
                case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
                    bErr = true;
                    errStr = "请求服务器超时 ： " + responsecode;
                    break;
                case 500:
                    bErr = true;
                    errStr = "服务器内部异常 ： " + responsecode;
                    break;
                default: {
                    bErr = true;
                    errStr = "错误代码: " + responsecode;
                    break;
                }
            }

        } catch (ConnectTimeoutException e) {
            bErr = true;
            errStr = "ConnectTimeoutException" + e.getMessage();
        } catch (IllegalArgumentException e) {
            bErr = true;
            errStr = "IllegalArgumentException: " + e.getMessage();
        } catch (ClassCastException e) {
            bErr = true;
            errStr = "ClassCastException" + e.getMessage();
        } catch (IOException e) {
            bErr = true;
            errStr = "IOException" + e.getMessage();
        } catch (Exception e) {
            bErr = true;
            errStr = "Exception" + e.getMessage();
        } catch (Throwable e) {
            bErr = true;
            errStr = "Throwable: " + e.getMessage();
        } finally {

            if (bErr) {
                System.out.println("errStr -- " + errStr);
                processor.handleError("errStr -- " + errStr);
            }
        }

    }

    public List<NameValuePair> getParams() {
        return params;
    }

    public void setParams(List<NameValuePair> params) {
        this.params = params;
    }

    public void start() throws IllegalStateException {
        httpthread = new HttpThread();
        httpthread.start();

    }

    class HttpThread extends Thread {

        public void run() {
            while (bRun) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                bRun = false;
                try {
                    send();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
