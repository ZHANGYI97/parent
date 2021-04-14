package com.ziyi.common.sort;

/**
 * auther:jurzis 冒泡排序
 * date: 2021/4/14 11:31
 */
public class BubbleSort implements ISort{

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
