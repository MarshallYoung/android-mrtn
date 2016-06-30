package com.yuchengtech.mrtn.base;

public interface YXAPI {
    // 服务器URL
    // String URL = "http://172.16.1.231:7001/mms/";
//    String URL = "http://182.50.120.70:6061/mms/";
    String URL = "http://182.50.120.70:7110/mms/";
    // String URL = "http://172.16.1.198:8080/mms/";
    String SUCCES_CODE = "00";// 成功代码
    String URL_LOGIN = URL + "login/appLogin";
    String DATA_MODO_ERR = "数据解析异常";
}