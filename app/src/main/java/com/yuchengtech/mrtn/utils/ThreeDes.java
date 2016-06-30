package com.yuchengtech.mrtn.utils;

import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class ThreeDes {
    // 定义 加密算法,可用 DES,DESede,Blowfish
    private static final String Algorithm = "DESede";

    //www.ycsys.com.cn-3des-DESede--32
    private static final String KEY = "99909279702660260333703373330032";

    // keybyte为加密密钥，长度为24字节
    // src为被加密的数据缓冲区（源）
    public static byte[] encryptMode(byte[] keybyte, byte[] src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            // 加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    // keybyte为加密密钥，长度为24字节
    // src为加密后的缓冲区
    public static byte[] decryptMode(byte[] keybyte, byte[] src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            // 解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    // 转换成十六进制字符串
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n < b.length - 1)
                hs = hs + ":";
        }
        return hs.toUpperCase();
    }

    private static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        ThreeDes threeDes = new ThreeDes();
        // String key = "www.ycsys.com.cn-3des-DESede--32";
        String key = "99909279702660260333703373330032";
        String szSrc = "POS00005733";
        System.out.println("加密前的字符串:" + szSrc);
        String enstr = threeDes.encryptModeStr("", szSrc);
        System.out.println("加密密文:" + enstr);
        System.out.println("解密密文:" + threeDes.decryptModeStr(key, enstr));
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     *
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    private byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }

        return arrOut;
    }

    // 得到二进制密钥
    private byte[] getMastkey(String keystr) throws Exception {
        String tmpkey = keystr + keystr.substring(0, 16);
        Log.w("====tmpkey====", tmpkey);
        return hexStr2ByteArr(tmpkey);
    }

    /**
     * 得到加密后的16进制字符串
     *
     * @param key 16进制字符串的主密钥
     * @param src 字符串
     */
    public String encryptModeStr(String pkey, String src) throws Exception {
        String key = pkey;
        if (key == null || "".equals(key)) {
            key = ThreeDes.KEY;
        }
        byte[] keyBytes = getMastkey(key);
        byte[] encoded = encryptMode(keyBytes, src.getBytes());
        String descstr = byteArr2HexStr(encoded);
        descstr = descstr.substring(0, 32).toUpperCase();
        return descstr;
    }

    public String encryptModeStrTmp(String key, String src) throws Exception {
        byte[] keyBytes = getMastkey(key);
        byte[] encoded = encryptMode(keyBytes, src.getBytes());
        String descstr = byteArr2HexStr(encoded).toUpperCase();
        return descstr;
    }

    /**
     * 得到解密后的16进制字符串
     *
     * @param key 16进制字符串的主密钥
     * @param src 16进制字字符串
     */
    public String decryptModeStr(String pkey, String src) throws Exception {
        String key = pkey;
        if (key == null || "".equals(key)) {
            key = ThreeDes.KEY;
        }
        Log.w("====src====", src);
        byte[] keyBytes = getMastkey(key);
        Log.w("====keyBytes====", new String(keyBytes));
        // 得到密钥串的后16位
        String tmp = encryptModeStrTmp(key, "1234567812345678");
        Log.w("====tmp====", tmp);
        tmp = tmp.substring(32, 48);
        Log.w("====tmp====", tmp);

        byte[] dncoded = hexStr2ByteArr(src + tmp);
        Log.w("====dncoded====", new String(dncoded));
        byte[] srcBytes = decryptMode(keyBytes, dncoded);
        Log.w("====srcBytes====", new String(srcBytes));
        String tempString = new String(srcBytes);
        Log.w("====tempString====", tempString);
        if (tempString != null && tempString.length() > 11)
            tempString = tempString.substring(0, 11);
        return tempString;
    }
}
