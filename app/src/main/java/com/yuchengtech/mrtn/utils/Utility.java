package com.yuchengtech.mrtn.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() + 1)) + 100;
        listView.setLayoutParams(params);
    }

    public static String ConverNullString(String v) {
        if (v.equals("null")) {
            return "";
        }
        return v;
    }

    /**
     * 字符串转换为日期
     *
     * @param dateString yyyy-MM-dd HH:mm:ss
     * @return 日期
     */
    public static Date stringToDate(String dateString) {
        String sf = "yyyyMMddHHmmss";
        Date dt = stringToDate(dateString, sf);
        return dt;
    }

    /**
     * 字符串转换为日期
     *
     * @param dateString 日期格式字符串
     * @param sf         日期格式化定义
     * @return 转换后的日期
     */
    public static Date stringToDate(String dateString, String sf) {
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat sdf = new SimpleDateFormat(sf);
        Date dt = sdf.parse(dateString, pos);
        return dt;
    }

    /**
     * 对日期进行格式化
     *
     * @param date 日期
     * @param sf   日期格式
     * @return 字符串
     */
    public static String FormatDate(Date date, String sf) {
        SimpleDateFormat dateformat = new SimpleDateFormat(sf);
        return dateformat.format(date);
    }

    public static String getChangeString(String dateStr, String colname) {
        Date date_time = null;
        if (dateStr.length() == 8)
            date_time = stringToDate(dateStr + "000000");
        else
            date_time = stringToDate(dateStr);
        if (colname.indexOf("yyyy") >= 0) {

            colname = colname.replaceAll("yyyy", FormatDate(date_time, "yyyy"));
        }
        if (colname.indexOf("yy") >= 0) {

            colname = colname.replaceAll("yy", FormatDate(date_time, "yy"));
        }
        if (colname.indexOf("MM") >= 0) {
            colname = colname.replaceAll("MM", FormatDate(date_time, "MM"));
        }
        if (colname.indexOf("dd") >= 0) {

            colname = colname.replaceAll("dd", FormatDate(date_time, "dd"));
        }
        if (colname.indexOf("HH") >= 0) {
            colname = colname.replaceAll("HH", FormatDate(date_time, "HH"));
        }
        if (colname.indexOf("mm") >= 0) {

            colname = colname.replaceAll("mm", FormatDate(date_time, "mm"));
        }
        if (colname.indexOf("ss") >= 0) {
            colname = colname.replaceAll("ss", FormatDate(date_time, "ss"));
        }
        return colname;

    }

}
