package com.ziyi.common.sort;

/**
 * auther:jurzis
 * date: 2021/4/14 16:26
 */
public class TestSort {

    public static void main(String[] args) {
        ISort sort = new BubbleSort();
        SortFactory sortFactory = SortFactory.getInstance(sort);
        int [] result = sortFactory.sort();
    }

}
