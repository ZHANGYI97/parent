package com.ziyi.leetcode;

/**
 * @author zhy
 * @data 2022/7/16 12:03
 */
public class RomanNumerals {

    public static void main(String[] args) {
        int num = 3;
        System.out.println(intToRoman(num));
    }

    public static String intToRoman(int num) {
        StringBuilder builder = new StringBuilder();
        String temp = String.valueOf(num);
        int length = temp.length();
        for (int i = length; i > 0; i--) {
            exchangeToRoman(num, i, builder);
            num = num % (i * 10);
        }
        return builder.toString();
    }

    public static void exchangeToRoman(int num, int i, StringBuilder builder) {
        String str1 = null;
        String str2 = null;
        if (i == 1) {
            str1 = "V";
            str2 = "I";
        } else if (i == 2) {
            str1 = "L";
            str2 = "X";
        } else if (i == 3) {
            str1 = "D";
            str2 = "C";
        } else if (i == 4) {
            str1 = "";
            str2 = "M";
        }
        num = num / (10 * i);
        //大于5
        int a = num / (5 * i * 10);
        //小于5
        int b = 0;
        if (num > (5 * i * 10)) {
            b = (num - (5 * i * 10)) / (1 * i * 10);
        } else {
            b = num / (1 * i * 10);
        }
        for (int m = 0; m < a; m++) {
            builder.append(str1);
        }
        for (int m = 0; m < b; m++) {
            builder.append(str2);
        }
    }

}
