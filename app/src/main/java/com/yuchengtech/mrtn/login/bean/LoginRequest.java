package com.yuchengtech.mrtn.login.bean;

import com.yuchengtech.mrtn.base.bean.YXRequest;

/**
 * 用户登录信息
 */
public class LoginRequest extends YXRequest {

    public String username;
    public String password;

    public LoginRequest(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }
}