package com.yuchengtech.mrtn.base.manager;

import com.yuchengtech.mrtn.login.bean.ShiroUser;

/**
 * 用户信息管理
 *
 * @author yanchunhan
 */
public class UserManager {

    private static UserManager mInstance;
    private ShiroUser userInfo;

    // 单例
    private UserManager() {
    }

    /**
     * 获取账户管理器单件实例对象
     */
    public static UserManager getInstance() {
        if (null == mInstance) {
            mInstance = new UserManager();
        }
        return mInstance;
    }

    public ShiroUser getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ShiroUser userInfo) {
        this.userInfo = userInfo;
    }
}