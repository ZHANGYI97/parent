package com.ziyi.leetcode;

/**
 * 给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false 。
 *
 * 回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。例如，121 是回文，而 123 不是。
 *
 *
 *
 */
public class Palindrome {

    public static void main(String[] args) {
        int x = -12321;
        System.out.println(isPalindrome(x));
    }

    public static boolean isPalindrome(int x) {
        String inputStr = String.valueOf(x);
        StringBuilder builder = new StringBuilder();
        for (int i = inputStr.length()-1; i >= 0; i--) {
            builder.append(inputStr.charAt(i));
        }
        if (builder.toString().equalsIgnoreCase(inputStr)) {
            return true;
        } else {
            return false;
        }
    }

}
