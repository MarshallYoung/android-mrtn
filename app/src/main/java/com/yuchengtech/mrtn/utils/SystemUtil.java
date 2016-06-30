package com.yuchengtech.mrtn.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 系统工具类
 *
 * @author yanchunhan
 * @created 2014-04-02
 */
public class SystemUtil {
    // AES密钥
    private static String AES_KEY = "7d25da286a4e35984c0eb9382ebfb4db";

    /**
     * 手机串号数据IMEI
     *
     * @param act
     * @return
     */
    public static String getIMEI(Context act) {
        TelephonyManager tm = (TelephonyManager) act
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 手机型号
     *
     * @param context
     * @return
     */
    public static String GetPhoneModel() {
        return Build.MODEL;
    }

    /**
     * 获取APP-VersionCode
     *
     * @param act 上下文
     * @return APP-VersionCode
     */
    public static int GetAppVersionCode(Activity activity) {
        String pName = activity.getPackageName();
        int versionCode = 0;

        try {
            PackageInfo pinfo = activity.getPackageManager().getPackageInfo(
                    pName, PackageManager.GET_CONFIGURATIONS);
            versionCode = pinfo.versionCode;
        } catch (NameNotFoundException e) {
        }
        return versionCode;
    }

    /**
     * 获取APP-VersionName
     *
     * @param act 上下文
     * @return APP-VersionName
     */
    public static String GetAppVersionName(Activity activity) {
        String pName = activity.getPackageName();
        String versionName = "";

        try {
            PackageInfo pinfo = activity.getPackageManager().getPackageInfo(
                    pName, PackageManager.GET_CONFIGURATIONS);
            versionName = pinfo.versionName;
        } catch (NameNotFoundException e) {
        }
        return versionName;
    }

    /*
     * Load file content to String
     */
    public static String loadFileAsString(String filePath)
            throws java.io.IOException {
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }

    /**
     * MD5加密
     *
     * @param 需要加密的字符
     * @return 加密后的字符串
     */
    public static String getMD5(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(content.getBytes());
            return getHashString(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;

    }

    private static String getHashString(MessageDigest digest) {
        StringBuilder builder = new StringBuilder();
        for (byte b : digest.digest()) {
            builder.append(Integer.toHexString((b >> 4) & 0xf));
            builder.append(Integer.toHexString(b & 0xf));
        }
        return builder.toString();
    }

    /**
     *
     * @param data
     * @return
     */
    /*
	 * public static String getSign(String data) { String sign = ""; try { sign
	 * = getMD5(URLs.appkey + data + URLs.secret); } catch (Exception e) {
	 * e.printStackTrace(); } return sign; }
	 */

    /**
     * @param data
     * @return
     */
	/*public static List<NameValuePair> getParams(String jsonString,
			String command) {

		List<NameValuePair> listPairs = new ArrayList<NameValuePair>();
		try {
			listPairs.add(new BasicNameValuePair("appkey", URLs.appkey));
			listPairs.add(new BasicNameValuePair("command", command));
			String urlBase64 = "";
			urlBase64 = Base64.encodeToString(jsonString.getBytes("UTF-8"),
					Base64.URL_SAFE).replace("\n", "");
			listPairs.add(new BasicNameValuePair("data", urlBase64));
			listPairs.add(new BasicNameValuePair("sign", getSign(urlBase64)));
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		return listPairs;
	}*/
    public static void TelPhone(Activity mActivity, String phoneNum) {
        try {
            Pattern p = Pattern.compile("\\d+?");
            Matcher match = p.matcher(phoneNum);
            // 正则验证输入的是否为数字
            if (match.matches()) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                        + phoneNum));
                mActivity.startActivity(intent);
            } else {
                Toast.makeText(mActivity, "号码格式不正确！", Toast.LENGTH_LONG).show();
            }
        } catch (PatternSyntaxException e) {
            e.printStackTrace();
        }
    }
}
