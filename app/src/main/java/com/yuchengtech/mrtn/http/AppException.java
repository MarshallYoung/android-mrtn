package com.yuchengtech.mrtn.http;

/**
 * 错误码
 */
public interface AppException {
    // 请求非法 -- 退回登陆页
    public static final int CODE_REQUEST_ILLEGAL = 10001;
    // 缺少参数 -- 返回
    public static final int CODE_LACK_PARAMETERS = 10002;
    // 站点关闭 退回登陆页
    public static final int CODE_APP_CLOSE = 10003;
    // 未登陆 退回登陆页
    public static final int CODE_NO_LOGIN = 10004;
    // hash验证失败 退回登陆页
    public static final int CODE_NO_HASH = 10005;
    // 无配置信息更新 不处理
    public static final int CODE_CONFIG_NO_UPDATE = 10006;
    // 配置信息有更新 -- 调用更新配置接口，更新配置，完成重复之前的操作
    public static final int CODE_CONFIG_UPDATE = 10007;

    // 访问标识无法识别 不处理
    // public static final int CODE_UNABLE_TO_IDENTIFY = 10011;
    // 用户名或密码不正确 返回
    // public static final int CODE_NAME_OR_PW_ERR = 10012;

    // 您的帐号已经在其它地方登陆 退回登陆页
    public static final int CODE_OTHER_LOGIN = 10013;
    // 暂停手机客户端访问 回主页
    public static final int CODE_STOP_ACCESS = 10014;
    // 您的版本已经不适用了，请尽快升级 调用测试升级接口
    public static final int CODE_UPGRADE = 10015;
    // 帐号已被禁用，请与管理员联系 退回登陆页
    public static final int CODE_DISABLE = 10016;
    // 帐号已经被删除 退回登陆页
    public static final int CODE_DELETE = 10017;

    public static final String DATA_MODO_ERR = "返回数据错误";
}