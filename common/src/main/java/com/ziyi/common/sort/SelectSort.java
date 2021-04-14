package com.ziyi.common.sort;

/**
 * auther:jurzis 选择排序
 * date: 2021/4/14 20:21
 */
public class SelectSort implements ISort{
    @Override
    public int[] sort(int[] arg) {
        for (int i = 0; i < arg.length; i++) {
            int temp;
            for (int j = i+1; j < arg.length; j++) {
                if (arg[i] > arg[j] ) {
                    temp = arg[i];
                    arg[i] = arg[j];
                    arg[j] = temp;
                }
            }
        }
        return arg;
    }
}
