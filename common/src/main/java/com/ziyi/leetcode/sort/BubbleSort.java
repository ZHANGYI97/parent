package com.ziyi.leetcode.sort;

/**
 * auther:jurzis 冒泡排序
 * date: 2021/4/14 11:31
 */
public class BubbleSort implements ISort{

    /**
     * 排序方式是：多次循环，每次比较两个数，若前一个数大于后一个数，则交换顺序
     *             循环找到每个数的位置
     *             时间复杂度：最好（O（n））,最差（O（n2））
     * @param arg
     * @return
     */
    @Override
    public int[] sort(int[] arg) {
        int temp;
        for (int i = 0; i < arg.length; i++){
            for (int j = i+1; j < arg.length; j++){
                if (arg[i] > arg[j]) {
                    temp = arg[j];
                    arg[j] = arg[i];
                    arg[i] = temp;
                }
            }
        }
        return arg;
    }
}
