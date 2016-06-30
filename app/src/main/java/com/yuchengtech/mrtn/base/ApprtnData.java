package com.yuchengtech.mrtn.base;

import java.util.HashMap;
import java.util.Map;

public class ApprtnData {
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
            put("003", "疑似风险商户调查单");
            put("002", "收单商户走访回访单");
            put("001", "培训");
        }
    };
    public static Map<String, String> taskOrderType = new HashMap<String, String>() {

        private static final long serialVersionUID = 1L;

        {
            put("009", "装机申请任务单");
            put("008", "装机任务单");
            put("005", "其他工单");
            put("004", "发卡行调单");
            put("003", "疑似风险商户调查单");
            put("002", "收单商户走访回访单");
            put("001", "培训");
        }
    };
    public static Map<String, String> taskMarkTypeCode = new HashMap<String, String>() {

        private static final long serialVersionUID = 1L;

        {
            put("信用卡预约申请", "信用卡预约申请");
            put("银行贷款预约申请", "银行贷款预约申请");
            put("理财产品预约购买", "理财产品预约购买");
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
            put("8", "风险调查");

        }
    };
    public static Map<String, String> mTradeAmountCode = new HashMap<String, String>() {

        private static final long serialVersionUID = 1L;

        {
            put("0", "高");
            put("1", "正常");
            put("2", "低");
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
    // 培训内容
    public static Map<String, String> mTaskTrainContent = new HashMap<String, String>() {

        private static final long serialVersionUID = 1L;

        {
            put("1", "银行卡基础知识");
            put("2", "操作注意事项");
            put("3", "刷卡机操作说明");
            put("4", "一般故障排除");
        }
    };
    // 培训结果
    public static Map<String, String> mTaskTrainResult = new HashMap<String, String>() {

        private static final long serialVersionUID = 1L;

        {
            put("1", "熟练掌握");
            put("2", "基本掌握");
            put("3", "申请培训");
            put("0", "其它情况");
        }
    };
    // 随机配送物品
    public static Map<String, String> mTaskTrainTermAtt = new HashMap<String, String>() {

        private static final long serialVersionUID = 1L;

        {
            put("1", "机具电源");
            put("2", "通讯连线");
            put("3", "密码键盘");
            put("4", "受理协议书");
            put("4", "补充协议");
        }
    };
    public static Map<String, String> mtermStatusCode = new HashMap<String, String>() {

        private static final long serialVersionUID = 1L;

        {

            put("0", "已撤机");
            put("1", "在运营");
        }
    };
    public static Map<String, String> OperatorType = new HashMap<String, String>() {

        private static final long serialVersionUID = 1L;

        {
            put("2", "维护人员及商户发起");
            put("1", "银行发起");
        }
    };
    /**
     * 商户类型 未知 传统对公POS POS1+1 POS1+2 支付易 POS1+N
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
     * 币种
     */
    public static Map<String, String> currency = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put("CNY/人民币", "CNY/人民币");
            put("HKD/港币", "HKD/港币");
            put("USD/美元", "USD/美元");
            put("EUR/欧元", "EUR/欧元");
            put("KRW/韩元", "KRW/韩元");
            put("JPY/日元", "JPY/日元");
            put("KRW/韩元", "KRW/韩元");
            put("GBP/英镑", "GBP/英镑");
            put("RUB/卢布", "RUB/卢布");
            put("SGD/新币", "SGD/新币");
            put("AUD/澳大利亚元", "AUD/澳大利亚元");
            put("CAD/加拿大元", "CAD/加拿大元");
            put("NZD/新西兰元", "NZD/新西兰元");
        }
    };

    /*
     * 证件 add ljw
     */
    public static Map<String, String> mcLglidtpMap = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put("身份证", "0");
            put("居民户口薄", "1");
            put("临时身份证", "2");
            put("军官证", "3");
            put("护照", "9");
        }
    };

    /*
     * 机具类型
     */
    public static Map<String, String> machineTypeMap = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put("0", "传统POS");
            put("1", "无线POS");
            put("2", "移动POS");
        }
    };
    public static Map<String, String> TermCompany = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put("0", "银行");
            put("1", "宇信易诚");
            put("2", "银联商务");
        }
    };
    public static Map<String, String> TermStatus = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put("0", "已撤机");
            put("1", "在运营");
        }
    };

}
