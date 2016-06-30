package com.yuchengtech.mrtn.base;

public class TermStatus {

    public static final String 待装机 = "0";

    public static final String 在运营 = "1";

    public static final String 待撤机 = "2";

    public static final String 已撤机 = "3";

    public static String ConvertStatusInteger(String item) {
        if ("待装机".equals(item))
            return TermStatus.待装机;
        else if ("在运营".equals(item))
            return TermStatus.在运营;
        else if ("待撤机".equals(item))
            return TermStatus.待撤机;
        else if ("已撤机".equals(item))
            return TermStatus.已撤机;
        return item;
    }

    public static String ConvertStatus(String item) {
        if (TermStatus.待装机.equals(item))
            return "待装机";
        else if (TermStatus.在运营.equals(item))
            return "在运营";
        else if (TermStatus.待撤机.equals(item))
            return "待撤机";
        else if (TermStatus.已撤机.equals(item))
            return "已撤机";
        else
            return "";
    }
}
