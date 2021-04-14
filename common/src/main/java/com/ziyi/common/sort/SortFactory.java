package com.ziyi.common.sort;

/**
 * auther:jurzis
 * date: 2021/4/14 11:32
 */
public class SortFactory {

    private ISort sort;

    private SortFactory(ISort sort){
        this.sort = sort;
    }

    public static SortFactory getInstance(ISort sort){
        return new SortFactory(sort);
    }

    /**
     * 排序方法
     * @return
     */
    public int[] sort(int[] arg){
        return sort.sort(arg);
    }

}
