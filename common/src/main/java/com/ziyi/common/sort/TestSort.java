package com.ziyi.common.sort;

/**
 * auther:jurzis
 * date: 2021/4/14 16:26
 */
public class TestSort {

    public static void main(String[] args) {
        int [] arg = {3,2,68,2,5,6,9,0,12,54};
        ISort sort = new BubbleSort();
        SortFactory sortFactory = SortFactory.getInstance(sort);
        int [] result = sortFactory.sort(arg);
        print(result);
    }

    private static void print(int[] arg){
        for (int a : arg) {
            System.out.println(a);
        }
    }

}
