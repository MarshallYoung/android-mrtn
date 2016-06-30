package com.yuchengtech.mrtn.http.request;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author L1
 * @created 2013-5-22
 */
public class HttpConnect {
    // , AppConfig {

    private static final int CONNECTION_TIMEOUT = 20000; // 设置超时时间
    private HttpThread httpthread;
    private volatile boolean bRun = true;
    private String url;
    private String requestmethod = "POST";
    private IHttpListener processor;
    private HttpURLConnection httpConn;
    private int responsecode;
    private String errStr = null;
    private byte[] postData = null;

    public HttpConnect(String url, IHttpListener processor) {
        this.url = url;
        this.processor = processor;
    }

    /**
     * @param thread
     * @throws Throwable
     */
    private void send(HttpThread thread) throws Throwable {
        InputStream in = null;
        OutputStream out = null;
        boolean bErr = false;

        try {
            String type = null;
            do {
                httpConn = getHttpConnect();

                byte[] data = this.getPostData();

                if (data != null) {

                    httpConn.setRequestProperty("Content-Length",
                            String.valueOf(data.length));

                    out = httpConn.getOutputStream();

                    out.write(data);

                    out.flush();
                }

                type = httpConn.getHeaderField("Content-Type");

            } while (type != null && type.startsWith("text/vnd.wap.wml"));

            responsecode = httpConn.getResponseCode();
            System.out.println("网络请求 -- " + this.responsecode);

            switch (responsecode) {

                case HttpURLConnection.HTTP_OK:
                    in = httpConn.getInputStream();
                    processor.decode(in);
                    // processor.decode("");
                    break;
                /** 网关超时 */
                case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
                case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
                    processor.handleError("请求服务器超时");
                    break;
                default: {
                    bErr = true;
                    errStr = "Response code: " + responsecode + " "
                            + httpConn.getResponseMessage();
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
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
            if (httpConn != null) {
                httpConn.disconnect();
            }

            if (bErr) {
                // processor.handleError(errStr);
                System.out.println("errStr -- " + errStr);
                processor.handleError("没有网络连接，请检查网络设置");
            }
        }
    }

    public byte[] getPostData() {
        return postData;
    }

    public void setPostData(byte[] postData) {
        this.postData = postData;
    }

    private HttpURLConnection getHttpConnect() throws Throwable {

        URL connUrl = new URL(url);

        httpConn = (HttpURLConnection) connUrl.openConnection();

        httpConn.setDoInput(true);

        httpConn.setDoOutput(true);

        httpConn.setConnectTimeout(CONNECTION_TIMEOUT);

        httpConn.setReadTimeout(CONNECTION_TIMEOUT);

        // if (http_mode == 0) {
        // requestmethod = "GET";
        // httpConn.setUseCaches(true);
        // } else {
        // requestmethod = "POST";

        // }
        httpConn.setUseCaches(false);
        httpConn.setRequestMethod(requestmethod);

        httpConn.setInstanceFollowRedirects(true);

        httpConn.setRequestProperty("Connection", "Keep-Alive");

        httpConn.setRequestProperty("Charset", "UTF-8");

        httpConn.setRequestProperty("Content-Type", "application/json");
        // 设置是AndroidAPP应用请求
        httpConn.setRequestProperty("android_request", "1");
        // setConnetAppConfig();

        return httpConn;
    }

    // private void setConnetAppConfig() {
    //
    // httpConn.setRequestProperty("PLATFORM", PLATFORM);
    //
    // httpConn.setRequestProperty("PROGRAMVERSION", VERSION);
    //
    // httpConn.setRequestProperty("IMEI", "414586362255525");
    // }

    public void start() throws IllegalStateException {
        httpthread = new HttpThread();
        httpthread.start();

    }

    public void close() {
        if (httpConn != null) {
            httpConn.disconnect();
        }
    }

    public int getResponsecode() {
        return responsecode;
    }

    public HttpURLConnection getConn() {
        return httpConn;
    }

    class HttpThread extends Thread {

        public void run() {
            while (bRun) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                }
                bRun = false;
                try {
                    send(this);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
