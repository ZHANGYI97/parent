package com.ziyi.leetcode;

/**
 * 输入一个32位有符号整数，将整数反转，输出；若反转后整数大于范围，则返回0
 */
public class IntegerInversion {

    public static void main(String[] args) {
        int x = -2147483648;
        System.out.println(reverse(x));
    }

    public static int reverse(int x) {
        long max = 2147483647;
        long min = -2147483648;
        String str = null;
        boolean flag = false;
        StringBuilder builder = new StringBuilder();
        long real = x;
        if (real > 0) {
            str = String.valueOf(real);
        } else {
            flag = true;
            str = String.valueOf(-real);
        }
        for (int i = str.length() - 1; i >= 0; i--) {
            builder.append(str.charAt(i));
        }
        long resultStr = Long.valueOf(builder.toString());
        if (resultStr > max || resultStr < min) {
            return 0;
        } else {
            if (flag) {
                resultStr = -resultStr;
            }
            return (int) resultStr;
        }
    }

}
