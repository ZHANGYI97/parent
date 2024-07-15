package com.ziyi.leetcode;

/**
 * @author zhy
 * @data 2022/12/7 20:57
 */

    public class DpProblemSalary {
    public int maxSalary(int[] startTime, int[] overTime, int[] salaries) {
        //状态转移方程：
        //OPT[i]=max{true:salaries[i]+OPT[pre(i)], false:OPT[i-1]}
        int n = salaries.length;

        int[] pre = new int[n];
        for (int i = 0; i < n; i++) {
            int max = 0;
            for (int j = i - 1; j >= 0; j--) {
                if (startTime[i] >= overTime[j]) {
                    max = j + 1;
                    break;
                }
            }
            pre[i] = max;
        }

        int[] opts = new int[n + 1];
        opts[0] = 0;

        for (int i = 1; i <= n; i++) {
            opts[i] = Math.max(opts[i - 1], salaries[i - 1] + opts[pre[i - 1]]);
        }

        return opts[n];
    }


    public static void main(String[] args) {
        int[] startTime = {1, 3, 0, 4, 3, 5, 6, 8};
        int[] overTime = {4, 5, 6, 7, 8, 9, 10, 11};
        int[] salaries = {5, 1, 8, 4, 6, 3, 2, 4};

        System.out.println(new DpProblemSalary().maxSalary(startTime, overTime, salaries));
    }
}
