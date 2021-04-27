package com.ziyi.leetcode.sort;

/**
 * auther:jurzis 排序算法，从小到大排列
 * date: 2021/4/14 16:26
 */
public class TestSort {

    public static void main(String[] args) {
        int [] arg = {3,2,68,2,5,6,9,0,12,54};
        //冒泡排序
        //ISort sort = new BubbleSort();
        //选择排序
        //ISort sort = new SelectSort();
        ISort sort = new InsertSort();
        SortFactory sortFactory = SortFactory.getInstance(sort);
        int [] result = sortFactory.sort(arg);
        print(result);
    }

    private static void print(int[] arg){
        for (int a : arg) {
            System.out.print(a);
            System.out.print(",");
        }
    }

}
