package com.yuchengtech.mrtn.main.bean;

/**
 * 功能按钮实体.上面是图标,下面是功能文字.用于主页(HomepageFragment)
 *
 * @author lovesych1314
 */
public class FunctionButtonInfo {

    public static final int HOME_MCINFO = 0;// 商户信息
    public static final int HOME_SCAN_CODE = 1;
    public static final int HOME_TODO_LIST = 2;// 待办列表
    public static final int HOME_COMPLETED_LIST = 3;// 办结列表
    public static final int HOME_PREDICT_TIME = 4;// 预计上门时间
    public static final int HOME_ORDER_INSTALL = 5;// 上门装机或退件
    public int id = 0;
    /**
     * 功能名称
     */
    public String name = "";
    /**
     * logo
     */
    public int imgIndex = 0;
    /**
     * 背景
     */
    public int imgBgIndex = 0;
}