package com.ziyi.leetcode.sort;

import java.util.Random;

/**
 * auther:jurzis 快速排序
 * date: 2021/4/27 19:36
 */
public class QuickSort implements ISort {
    @Override
    public int[] sort(int[] arg) {
        if (arg == null || arg.length < 2) {
            return arg;
        }
        quickSort(arg, 0, arg.length - 1);
        return arg;
    }

    /**
     * 快速排序，使得整数数组 arr 的 [L, R] 部分有序
     */
    public static void quickSort(int[] arr, int L, int R) {
        if (L < R) {
            // 把数组中随机的一个元素与最后一个元素交换，这样以最后一个元素作为基准值实际上就是以数组中随机的一个元素作为基准值
            swap(arr, new Random().nextInt(R - L + 1) + L, R);
            int[] p = partition(arr, L, R);
            quickSort(arr, L, p[0]);
            quickSort(arr, p[1], R);
        }
    }

    /**
     * 分区的过程，整数数组 arr 的[L, R]部分上，使得：
     * 大于 arr[R] 的元素位于[L, R]部分的右边，但这部分数据不一定有序
     * 小于 arr[R] 的元素位于[L, R]部分的左边，但这部分数据不一定有序
     * 等于 arr[R] 的元素位于[L, R]部分的中间
     * 返回第一个分区的最后一个元素，和第二个分区的第一个元素位置的整数数组
     */
    public static int[] partition(int[] arr, int L, int R) {

        int basic = arr[R];
        int less = L - 1;
        int more = R;
        //从最左边开始，循环与选定元素比较
        while (L < more) {
            if (arr[L] < basic) {
                //小于，元素不动 或者 挨着的两个交换顺序
                swap(arr, ++less, L++);
            } else if (arr[L] > basic) {
                //大于，选定元素与当前元素交换位置
                swap(arr, more--, L);
            } else {
                L++;
            }
        }
        return new int[]{less, more};
    }

    /*
     * 交换数组 arr 中下标为 i 和下标为 j 位置的元素
     */
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
