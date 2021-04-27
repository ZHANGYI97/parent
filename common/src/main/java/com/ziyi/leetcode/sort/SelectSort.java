package com.ziyi.leetcode.sort;

/**
 * auther:jurzis 选择排序
 * date: 2021/4/14 20:21
 */
public class SelectSort implements ISort{

    /**
     * 排序方式：在未排序的数组中找到最大或最小的数
     *           放在第n位
     *           时间复杂度：O（n2）
     * @param arg
     * @return
     */
    @Override
    public int[] sort(int[] arg) {
        for (int i = 0; i < arg.length; i++) {
            int temp = i;
            for (int j = i+1; j < arg.length; j++) {
                if (arg[temp] > arg[j] ) {
                    temp = j;
                }
            }
            if (temp != i) {
                temp = arg[i];
                arg[i] = arg[temp];
                arg[temp] = temp;
            }
        }
        return arg;
    }
}
