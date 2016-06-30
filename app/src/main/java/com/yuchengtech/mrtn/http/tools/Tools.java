package com.yuchengtech.mrtn.http.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

/**
 * @author L1
 * @created 2013-5-22
 */
public class Tools {

    public static String[] split(String original, String regex) {
        int startIndex = 0;
        Vector<String> v = new Vector<String>();
        String[] str = null;
        int index = 0;
        startIndex = original.indexOf(regex);
        while (startIndex < original.length() && startIndex != -1) {
            String temp = original.substring(index, startIndex);
            v.addElement(temp);
            index = startIndex + regex.length();
            startIndex = original.indexOf(regex, startIndex + regex.length());
        }
        v.addElement(original.substring(index + 1 - regex.length()));
        str = new String[v.size()];
        for (int i = 0; i < v.size(); i++) {
            str[i] = (String) v.elementAt(i);
        }
        return str;
    }

    /**
     * ���ַ�ת��utf8����
     *
     * @param ch char[]
     * @return byte[]
     */
    public static byte[] decodeToUTF8(char[] ch) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        byte[] utf8 = new byte[4];
        int bytes = 0;
        try {
            for (int i = 0; i < ch.length; i++) {
                bytes = 0;
                if (ch[i] < 0x80) {
                    utf8[bytes++] = (byte) ch[i];
                } else if (ch[i] < 0x0800) {
                    utf8[bytes++] = (byte) (ch[i] >> 6 | 0xC0);
                    utf8[bytes++] = (byte) (ch[i] & 0x3F | 0x80);
                } else if (ch[i] < 0x10000) {
                    utf8[bytes++] = (byte) (ch[i] >> 12 | 0xE0);
                    utf8[bytes++] = (byte) (ch[i] >> 6 & 0x3F | 0x80);
                    utf8[bytes++] = (byte) (ch[i] & 0x3F | 0x80);
                } else {
                    utf8[bytes++] = (byte) (ch[i] >> 18 | 0xF0);
                    utf8[bytes++] = (byte) (ch[i] >> 12 & 0x3F | 0x80);
                    utf8[bytes++] = (byte) (ch[i] >> 6 & 0x3F | 0x80);
                    utf8[bytes++] = (byte) (ch[i] & 0x3F | 0x80);
                }
                bos.write(utf8, 0, bytes);

            }
        } catch (Throwable e) {
            // Debug.out("decodeToUTF8 error");
            e.printStackTrace();
        } finally {
            System.gc();
        }
        return bos.toByteArray();
    }

    /**
     * ���ַ�ת��UTF8
     *
     * @param str String
     * @return byte[]
     */
    public static byte[] decodeToUTF8(String str) {

        return decodeToUTF8(str.toCharArray());
    }

    /**
     * ��UTF8����ַ�
     *
     * @param data byte[]
     * @param off  int
     * @param len  int
     * @return String
     */
    public static String UTF8ArrayToString(byte[] data, int off, int len) {
        byte[] utf16 = UTF8ToUTF16(data, off, len);

        return UTF16ArrayToString(utf16, 0, utf16.length);
    }

    /**
     * ��unicode�ڲ���ݱ�����ַ�
     *
     * @param data byte[]
     * @param off  int
     * @param len  int
     * @return String
     */
    public static String UTF16ArrayToString(byte[] data, int off, int len) {
        StringBuffer buf = new StringBuffer();
        int index = off;
        char ch;
        int end = off + len - 1;
        try {
            while (index < end) {
                ch = (char) (((data[index + 1] << 8) & 0xff00) | (data[index] & 0xff));
                index += 2;
                buf.append(ch);
            }
        } catch (Throwable e) {

        } finally {
            System.gc();
        }
        return buf.toString();
    }

    /**
     * ��UTF8ת��unicode
     *
     * @param data   byte[]
     * @param off    int
     * @param length int
     * @return byte[]
     */
    public static byte[] UTF8ToUTF16(byte[] data, int off, int length) {
        int index = off;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] unicode = new byte[2];
        try {
            byte temp;
            while (index < off + length) {
                temp = data[index];
                if (((temp >> 7) & 0x1) == 0) {
                    // 0xxx xxxx
                    unicode[0] = temp;
                    unicode[1] = 0;
                    index++;
                } else if (((temp >> 5) & 0x7) == 0x6) {
                    // 110x xxxx 10xx xxxxx
                    unicode[0] = (byte) ((temp << 6) | (data[index + 1] & 0x3f));
                    unicode[1] = (byte) ((temp >> 2) & 0x7);

                    index += 2;
                } else if (((temp >> 4) & 0xf) == 0xe) {
                    // 1110 xxxx 10xx xxxx 10xx xxxx
                    unicode[0] = (byte) ((data[index + 1] << 6) | (data[index + 2] & 0x7f));
                    unicode[1] = (byte) ((temp << 4) | ((data[index + 1] >> 2) & 0xf));

                    index += 3;
                } else {
                    // 1111 0xxx 10xx xxxx 10xx xxxx 10xx xxxx
                    unicode[0] = (byte) ((data[index + 2] << 6) | (data[index + 3] & 0x3F));
                    unicode[1] = (byte) ((data[index + 1] << 4) | ((data[index + 2] >> 4) & 0xF));
                }

                bos.write(unicode);
            }

        } catch (IOException e) {
            // Debug.out("UTF6ArrayToString error");
            e.printStackTrace();
        } finally {
            System.gc();
        }
        return bos.toByteArray();
    }

    /**
     * ��ȡָ�����ֽ���
     *
     * @param in
     * @param baos
     * @param sep
     * @return String
     * @throws IOException
     */
    public static String seekSep(InputStream in) throws IOException {

        int index = 0;
        int data = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while ((data = in.read()) != -1) {
            if (data == '\r') {
                continue;
            }
            index++;
            baos.write(data);
        }
        String line = null;
        if (index > 1) {
            line = Tools.UTF8ArrayToString(baos.toByteArray(), 0, index);
            // line = new String(baos.toByteArray(), "UTF-8");
        }
        return line;
    }

}