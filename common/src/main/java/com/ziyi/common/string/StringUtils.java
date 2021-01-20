package com.ziyi.common.string;

public class StringUtils {

    /**
     * 左侧填充字符
     * @param src 原字符
     * @param len 需要填充的长度
     * @param ch 需要填充的字符
     * @return
     */
    public static String padLeft(String src, int len, char ch) {
        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }

        char[] chars = new char[len];
        System.arraycopy(src.toCharArray(), 0, chars, diff, src.length());

        for (int i = 0; i < diff; i++){
            chars[i] = ch;
        }
        return new String(chars);
    }

    public static boolean isNullOrEmpty(String text) {
        return text == null || "".equalsIgnoreCase(text);
    }

}
