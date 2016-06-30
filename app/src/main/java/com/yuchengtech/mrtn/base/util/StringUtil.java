package com.yuchengtech.mrtn.base.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    private static int VALID_ACCOUNT_MIN = 4;
    private static int VALID_ACCOUNT_MAX = 40;

    private static int VALID_USERNAME_MIN = 2;
    private static int VALID_USERNAME_MAX = 8;

    private static int VALID_PASSWORD_MIN = 6;
    private static int VALID_PASSWORD_MAX = 16;
    private static String TRUNCATE_POSFIX = "...";

    /**
     * 得到sid
     *
     * @param cookieString
     * @return sid服务器返回的位移识别码, 类似于sessionId
     */
    public static String getSId(String cookieString) {
        String result = "";
        // TODO
        int index = cookieString.indexOf("=");
        if (index > 2) {
            String key = cookieString.substring(0, 3);// 得到"="前三位的字符串并判断
            if ("sid".equals(key)) {
                String subString = cookieString.substring(index + 1);
                int fIndex = subString.indexOf(";");
                if (fIndex > 0) {
                    result = subString.substring(0, fIndex);
                }
            }
        }
        return result;
    }

    /**
     * Email验证
     */
    public static boolean validateEmail(Context context, String sTermail) {
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(sTermail);
        boolean validate = m.matches();
        if (!validate) {
            return false;
        }
        return true;
    }

    /**
     * 手机验证
     *
     * @param context
     * @param sTermail
     * @return 返回true 标识手机号码是正确的
     */
    public static boolean validateMoblie(Context context, String sTermail) {
        String strPattern = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(sTermail);
        boolean validate = m.matches();
        // System.out.println("--"+validate);
        return validate;
    }

    /**
     * 检查账户名输入法串的合法性
     *
     * @param context
     * @param usrName
     * @return
     */
    public static boolean validateAccount(Context context, String account) {
        if (account.length() < VALID_ACCOUNT_MIN
                || account.length() > VALID_ACCOUNT_MAX) {
            return false;
        }

        return true;
    }

    /**
     * 检查用户名输入法串的合法性
     *
     * @param context
     * @param usrName
     * @return
     */
    public static boolean validateUsername(Context context, String usrName) {
        if (usrName.length() < VALID_USERNAME_MIN
                || usrName.length() > VALID_USERNAME_MAX) {
            return false;
        }

        return true;
    }

    /**
     * 检查密码输入法串的合法性
     *
     * @param context
     * @param password
     * @return
     */
    public static boolean validatePassword(Context context, String password) {
        if (password.length() < VALID_PASSWORD_MIN
                || password.length() > VALID_PASSWORD_MAX) {
            return false;
        }

        return true;
    }

    // 2011-6-27 16:11:24
    public static String getDateString(long timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss");
        Date curDate = new Date(timestamp);
        String dateString = formatter.format(curDate);
        return dateString;
    }

    // 2011-6-27 16:11
    public static String getDateStringYYMMDD(long timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(timestamp * 1000);
        String dateString = formatter.format(curDate);
        return dateString;
    }

    public static boolean isDateToday(Date date) {
        // TODO But Calendar is so slowwwwwww....
        Date today = new Date();
        if (date.getYear() == today.getYear()
                && date.getMonth() == today.getMonth()
                && date.getDate() == today.getDate()) {
            return true;
        }
        return false;
    }

    public static boolean isNetworkAvailable(Context context) {
        boolean flag = false;
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                flag = ni.isAvailable();
            }
        }
        return flag;
    }

    public static long copy(InputStream input, OutputStream output)
            throws IOException {
        byte[] buffer = new byte[512];
        long count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0
                || str.equalsIgnoreCase("null");
    }

    public static String getDateWithWeekString(Context context, long timestamp) {
        // String yearStr = (String) DateUtils.formatDateTime(context,
        // timestamp, DateUtils.FORMAT_SHOW_YEAR);// 2011
        // String dateStr = (String) DateUtils.formatDateTime(context,
        // timestamp, DateUtils.FORMAT_SHOW_DATE);// 5月11日
        // String timeStr = (String) DateUtils.formatDateTime(context,
        // timestamp, DateUtils.FORMAT_SHOW_TIME);// 16：40

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(timestamp);
        String dateString = formatter.format(curDate);

        String weekStr = (String) DateUtils.formatDateTime(context, timestamp,
                DateUtils.FORMAT_SHOW_WEEKDAY);// 星期二
        String retStr = dateString + " " + weekStr;
        return retStr;
    }

    // 2011-6-27 16:11:24
    public static String getDateString(long timestamp, boolean hasYear) {
        SimpleDateFormat formatter;

        if (hasYear) {
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            formatter = new SimpleDateFormat("MM-dd HH:mm");
        }

        Date curDate = new Date(timestamp);
        String dateString = formatter.format(curDate);
        // Log.i("yy", "timestamp:"+timestamp+" --"+dateString);
        return dateString;
    }

    public static String getYHDDateString(long timestamp) {
        SimpleDateFormat formatter;

        formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date curDate = new Date(timestamp);
        String dateString = formatter.format(curDate);
        return dateString;
    }

    /**
     * yyyy年MM月dd日
     *
     * @param timestamp
     * @return
     */
    public static String getYHDDateStringCH(long timestamp) {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(timestamp);
        String dateString = formatter.format(curDate);
        return dateString;
    }

    // 16:11
    public static String getTimeString(long timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date curDate = new Date(timestamp);
        String dateString = formatter.format(curDate);
        return dateString;
    }

    // 16:11
    public static String getFullTimeString(long timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(timestamp);
        String dateString = formatter.format(curDate);
        return dateString;
    }

    public static String truncate(String srcString, int limitLen) {
        if (isEmpty(srcString))
            return srcString;

        String destString = srcString;
        int len = srcString.length();

        if (len > limitLen) {
            destString = srcString.substring(0, limitLen) + TRUNCATE_POSFIX;
        }

        return destString;
    }

    public static String checkNull(String content) {
        if (StringUtil.isEmpty(content))
            return "null";
        else
            return content;
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatDate(String dateStr, String patten) {
        if (dateStr == null || "".equals(dateStr))
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat(patten);
        try {
            return sdf.format(sdf.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatDate(String dateStr) {
        if (dateStr == null || "".equals(dateStr))
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.format(sdf.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String toSafeString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public static boolean isNotBlank(String str) {
        return (str != null && !"".equals(str.trim()));
    }

    public static double toDouble(String str) {
        double tmp = 0;
        try {
            tmp = Double.parseDouble(str);
        } catch (Exception e) {
        }
        return tmp;
    }
}