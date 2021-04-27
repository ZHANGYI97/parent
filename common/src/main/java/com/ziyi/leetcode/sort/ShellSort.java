package com.ziyi.leetcode.sort;

/**
 * auther:jurzis 希尔排序
 * date: 2021/4/27 19:25
 */
public class ShellSort implements ISort {

    /**
     * 排序原理：选择步长n/2，每次从步长拿出两个数进行比较，并交换位置
     *           知道步长变为1
     *           进行插入排序，此时插入排序复杂度最低
     * @param arg
     * @return
     */
    @Override
    public int[] sort(int[] arg) {
        //增量gap，并逐步缩小增量
        for (int gap = arg.length / 2; gap > 0; gap /= 2) {
            //从第gap个元素，逐个对其所在组进行直接插入排序操作
            for (int i = gap; i < arg.length; i++) {
                int j = i;
                while (j - gap >= 0 && arg[j] < arg[j - gap]) {
                    //插入排序采用交换法
                    swap(arg, j, j - gap);
                    j -= gap;
                }
            }
        }
        return arg;
    }

    public static void swap(int[] arr, int a, int b) {
        arr[a] = arr[a] + arr[b];
        arr[b] = arr[a] - arr[b];
        arr[a] = arr[a] - arr[b];
    }
}
