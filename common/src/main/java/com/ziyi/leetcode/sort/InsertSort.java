package com.ziyi.leetcode.sort;

/**
 * auther:jurzis
 * 插值排序
 * date: 2021/4/27 16:38
 */
public class InsertSort implements ISort{

    /**
     * 排序方式：从一个元素开始，视为一个已排序数组
     *           从第二个元素开始，依次与该已排序数组从后往前进行对比
     *           找到大于该元素的位置，进行插入
     *           时间复杂度：最佳（O(n)）,最差（O(n2)）
     * @param arg
     * @return
     */
    @Override
    public int[] sort(int[] arg) {
        for (int i = 0; i < arg.length; i++) {
            int temp;
            int index = i+1;
            for (int j = 0; j < i+1; j++) {
                if (index < arg.length && arg[index] < arg[i-j]) {
                    temp = arg[i - j];
                    arg[i - j] = arg[index];
                    arg[index] = temp;
                    index = i - j;
                }
            }
        }
        return arg;
    }
}
