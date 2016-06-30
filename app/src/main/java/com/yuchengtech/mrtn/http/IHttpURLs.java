package com.yuchengtech.mrtn.http;

/**
 * 回调接口
 */
public interface IHttpURLs {
    void despatch(Object o);

    void despatch(Object o, Object ob);

    void handleErrorInfo(String err);
}