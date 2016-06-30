package com.yuchengtech.mrtn.base;

import java.util.HashMap;
import java.util.Map;

public class DataType {
    /**
     * 任务单类型
     */
    public static Map<String, String> taskOrderTypeCode = new HashMap<String, String>() {

        private static final long serialVersionUID = 1L;

        {
            put("010", "补充进件材料");
            put("009", "装机申请任务单");
            put("008", "装机任务单");
            put("007", "故障报修");
            put("006", "耗材配送");
            put("005", "其他工单");
            put("004", "发卡行调单");
            put("003", "疑似风险单");
            put("002", "走访单");
            put("001", "培训");
        }
    };
    public static Map<String, String> taskTrainTypeCode = new HashMap<String, String>() {

        private static final long serialVersionUID = 1L;

        {
            put("001", "新增终端");
            put("002", "收银员变更");
            put("003", "定期培训");
        }
    };
    /**
     * 走访性质
     */
    public static Map<String, String> visitNatureCode = new HashMap<String, String>() {

        private static final long serialVersionUID = 1L;

        {
            put("0", "巡检");
            put("1", "换机");
            put("2", "培训");
            put("3", "耗材配送");
            put("4", "调账");
            put("5", "补签协议");
            put("6", "变更资料");
            put("7", "发卡行调单");
            put("7", "风险调查");

        }
    };

    public static Map<String, String> mTaskStatusCode = new HashMap<String, String>() {

        private static final long serialVersionUID = 1L;

        {
            put("3", "异常已处理");
            put("2", "正常已处理");
            put("1", "已派工");
            put("0", "待处理");
        }
    };

    /**
     * 未知 传统对公POS POS1+1 POS1+2 支付易 POS1+N
     */
    public static Map<String, String> sysCode = new HashMap<String, String>() {

        private static final long serialVersionUID = 1L;

        {
            put("0000", "未知");
            put("0010", "传统对公POS");
            put("0020", "传统对私POS");
            put("0030", "POS1+1");
            put("0040", "POS1+2");
            put("0050", "支付易");
            put("0060", "POS1+N");
        }
    };

    /**
     * 交易类型 “1000”----消费 “1010”----消费撤销 “2000”----预授权完成 “2010”----预授权完成撤销
     * “3000”----分期 “3010”----分期撤销 “4000”----退货
     */
    public static Map<String, String> tradeType = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put("1000", "消费");
            put("1010", "消费撤销");
            put("2000", "预授权完成");
            put("2010", "预授权完成撤销");
            put("3000", "分期");
            put("3010", "分期撤销");
            put("4000", "退货");
        }
    };
    public static Map<String, String> mcNatureMp = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put("0", "国营");
            put("1", "集体");
            put("2", "私营");
            put("3", "合资");
            put("4", "股份制");
            put("5", "个体工商户");
            put("6", "其它");
        }
    };
    /**
     * 法人证件类型 0-身份证 1-居民户口薄 2-临时身份证 3-军官证 9-护照
     */
    public static Map<String, String> mcLglidtpMap = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put("0", "身份证");
            put("1", "居民户口薄");
            put("2", "临时身份证");
            put("3", "军官证");
            put("9", "护照");
        }
    };
    public static Map<String, String> mcStatusMap = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put("0", "正常");
            put("1", "撤机");
            put("2", "可疑");
        }
    };

}
