package com.yuchengtech.mrtn.http.request;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpConnectImg {

    private String POST = "POST";
    private int TIMEOUT = 30000;

    private String errStr = null;
    private HttpThread httpthread;
    private volatile boolean bRun = true;

    private String url;
    private IHttpListener processor;
    private HttpURLConnection httpConn;

    private int responsecode;
    private byte[] postData = null;
    private Map<String, Object> addHeadInfo;

    public HttpConnectImg(String url, IHttpListener processor) {
        this.setUrl(url);
        this.processor = processor;
    }

    public void send() throws Throwable {
        InputStream in = null;
        OutputStream out = null;
        boolean bErr = false;
        try {

            httpConn = (HttpURLConnection) new URL(this.getUrl())
                    .openConnection();
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setUseCaches(false);
            httpConn.setRequestMethod(POST);
            httpConn.setConnectTimeout(TIMEOUT);
            httpConn.setReadTimeout(TIMEOUT);
            httpConn.setRequestProperty("Content-type",
                    "application/x-java-serialized-object");
            // 设置是AndroidAPP应用请求
            httpConn.setRequestProperty("android_request", "1");
            if (this.addHeadInfo != null) {
                for (String key : this.getAddHeadInfo().keySet()) {
                    this.httpConn.addRequestProperty(key,
                            String.valueOf(this.getAddHeadInfo().get(key)));
                    System.out.println("key - " + key + "        value - "
                            + this.getAddHeadInfo().get(key));
                }
                httpConn.setRequestProperty("Charset", "UTF-8");
            }

            if (this.postData != null) {
                this.httpConn.setRequestProperty("Content-Length",
                        String.valueOf(this.postData.length));
                out = this.httpConn.getOutputStream();
                out.write(this.postData);
                out.flush();
            }
            httpConn.connect();

            this.responsecode = this.httpConn.getResponseCode();

            System.out.println("网络状态 -- " + this.httpConn.getResponseCode());

            switch (this.responsecode) {
                case 200:
                    in = this.httpConn.getInputStream();
                    // this.processor.decode(in);
                    this.processor.decode("");
                    break;
                case 408:
                case 504:
                    break;
                default:
                    bErr = true;
                    this.errStr = ("Response code: " + this.responsecode + " ");
            }
        } catch (ConnectTimeoutException e) {
            bErr = true;
            this.errStr = ("ConnectTimeoutException" + e.getMessage());
        } catch (IllegalArgumentException e) {
            bErr = true;
            this.errStr = ("IllegalArgumentException: " + e.getMessage());
        } catch (ClassCastException e) {
            bErr = true;
            this.errStr = ("ClassCastException" + e.getMessage());
        } catch (IOException e) {
            bErr = true;
            this.errStr = ("IOException" + e.getMessage()); // 超时在这里提示出文字
        } catch (Exception e) {
            bErr = true;
            this.errStr = ("Exception" + e.getMessage());
        } catch (Throwable e) {
            bErr = true;
            this.errStr = ("Throwable: " + e.getMessage());
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException localIOException13) {
                }
            if (out != null)
                try {
                    out.close();
                } catch (IOException localIOException14) {
                }
            if (this.httpConn != null) {
                this.httpConn.disconnect();
            }

            if (bErr) {
                System.out.println("errStr -- " + errStr);
                this.processor.handleError("没有网络连接，请检查网络设置");
            }

        }
    }

    public void start() throws IllegalStateException {
        this.httpthread = new HttpThread();
        this.httpthread.start();
    }

    public void close() {
        if (this.httpConn != null)
            this.httpConn.disconnect();
    }

    public void setPostData(byte[] postData) {
        this.postData = postData;
    }

    public Map<String, Object> getAddHeadInfo() {
        return this.addHeadInfo;
    }

    public void setAddHeadInfo(Map<String, Object> addHeadInfo) {
        this.addHeadInfo = addHeadInfo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    class HttpThread extends Thread {
        HttpThread() {
        }

        public void run() {
            while (HttpConnectImg.this.bRun) {
                try {
                    Thread.sleep(300L);
                } catch (InterruptedException localInterruptedException) {
                }
                HttpConnectImg.this.bRun = false;
                try {
                    HttpConnectImg.this.send();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
