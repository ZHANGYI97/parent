package com.ziyi.leetcode;

import com.ziyi.common.string.StringUtils;

import java.util.regex.Pattern;

/**
 * 读入字符串并丢弃无用的前导空格
 * 检查下一个字符（假设还未到字符末尾）为正还是负号，读取该字符（如果有）。 确定最终结果是负数还是正数。 如果两者都不存在，则假定结果为正。
 * 读入下一个字符，直到到达下一个非数字字符或到达输入的结尾。字符串的其余部分将被忽略。
 * 将前面步骤读入的这些数字转换为整数（即，"123" -> 123， "0032" -> 32）。如果没有读入数字，则整数为 0 。必要时更改符号（从步骤 2 开始）。
 * 如果整数数超过 32 位有符号整数范围 [−231,  231 − 1] ，需要截断这个整数，使其保持在这个范围内。具体来说，小于 −231 的整数应该被固定为 −231 ，大于 231 − 1 的整数应该被固定为 231 − 1 。
 * 返回整数作为最终结果。
 */
public class String2Int {

    public static void main(String[] args) {
        String s = "42";
        System.out.println(myAtoi(s));
    }

    public static int myAtoi(String s) {
        //1.去掉头尾空格
        s = s.trim();
        long resultLong = 0;
        if (StringUtils.isNotEmpty(s)) {
            //2.判断正负
            boolean flag = isSize(s);
            //3.读取有效数字
            long real = getRealNumber(s);
            //4.添加符号位
            resultLong = addSymbol(flag, real);
        } else {
            return 0;
        }
        return (int) resultLong;
    }

    /**
     * 判断是正数还是负数，true:正数
     *
     * @param s
     * @return
     */
    public static boolean isSize(String s) {
        if ("-".equals(s.charAt(0))) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 读取有效数字
     *
     * @param s
     * @return
     */
    public static long getRealNumber(String s) {
        boolean flag = false;
        StringBuilder builder = new StringBuilder();
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        for (int i = 0; i < s.length(); i++) {
            if (pattern.matcher(String.valueOf(s.charAt(i))).matches()) {
                //是数字
                builder.append(s.charAt(i));
                flag = true;
            } else {
                if (flag) {
                    break;
                }
            }
        }
        if (StringUtils.isEmpty(builder.toString())){
            return 0;
        } else {
            return Long.valueOf(builder.toString());
        }
    }

    public static long addSymbol(boolean flag, long real) {
        long max = 2147483647;
        long min = -2147483648;
        if (real > max) {
            return max;
        } else if (real < min) {
            return min;
        }
        if (flag) {
            //正数
            return real;
        } else {
            //负数
            return -real;
        }
    }


}
