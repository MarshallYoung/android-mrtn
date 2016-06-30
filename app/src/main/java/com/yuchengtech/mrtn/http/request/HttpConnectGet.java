package com.yuchengtech.mrtn.http.request;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
public class HttpConnectGet {

    private static final int CONNECTION_TIMEOUT = 30 * 1000; // 设置超时时间
    private HttpThread httpthread;
    private volatile boolean bRun = true;
    private String url;
    private IHttpListener processor;
    private HttpClient client;
    private int responsecode;
    private String errStr = null;
    private List<NameValuePair> params = null;

    public HttpConnectGet(String url, IHttpListener processor) {
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
            HttpGet httpGet = new HttpGet(url);
            client = new DefaultHttpClient();
            // 请求超时
            client.getParams()
                    .setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                            CONNECTION_TIMEOUT);

            httpGet.setHeader("android_request", "1");
            HttpResponse httpResponse = client.execute(httpGet);
            responsecode = httpResponse.getStatusLine().getStatusCode();
            switch (responsecode) {
                case HttpURLConnection.HTTP_OK:
                    String strResult = EntityUtils.toString(
                            httpResponse.getEntity(), HTTP.UTF_8);
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
                    processor.handleError("请求服务器超时");
                    break;
                default: {
                    bErr = true;
                    errStr = "Response code: " + responsecode;
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
                processor.handleError("没有网络连接，请检查网络设置");
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
