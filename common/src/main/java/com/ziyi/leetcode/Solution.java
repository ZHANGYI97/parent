package com.ziyi.leetcode;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhy
 * @data 2022/7/16 11:19
 */
public class Solution {

    public static void main(String[] args) {
        int[] a = {1,8,6,2,5,4,8,3,7};
        System.out.println(maxArea(a));
    }

    public static int maxArea(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int max = 0;
        int left = 0;
        int right = height.length - 1;
        while (left < right) {
            int minLength = Math.min(height[left], height[right]);
            int maxVolume = (right - left) * minLength;
            if (maxVolume > max) {
                max = maxVolume;
            }
            if (height[left] > height[right]) {
                right--;
            } else {
                left++;
            }
        }
        return max;

    }

}
