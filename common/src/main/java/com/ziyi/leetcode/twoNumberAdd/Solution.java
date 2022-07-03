package com.ziyi.leetcode.twoNumberAdd;

import org.apache.commons.compress.utils.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author zhy
 * @data 2022/6/6 23:39
 */
public class Solution {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1, new ListNode(2, new ListNode(3)));
        ListNode l2 = new ListNode(2, new ListNode(8, new ListNode(4)));
        System.out.println(addTwoNumbers(l1, l2).val);
    }


    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        StringBuilder builder1 = new StringBuilder();
        StringBuilder builder2 = new StringBuilder();
        toList(l1, builder1);
        toList(l2, builder2);
        Integer integer1 = Integer.valueOf(builder1.reverse().toString());
        Integer integer2 = Integer.valueOf(builder2.reverse().toString());
        Integer temp = integer1 + integer2;
        StringBuilder builder = new StringBuilder(temp.toString());
        String[] array = stringToStringArray(builder.reverse().toString(), 1);
        List<ListNode> list = new ArrayList();
        for (String s : array) {
            list.add(new ListNode(Integer.valueOf(s)));
        }
        for (int i = 0; i < list.size(); i++) {
            if (i + 1 < list.size()) {
                list.get(i).next = new ListNode(list.get(i + 1).val);

            }
        }
        return list.get(0);

    }

    public static String[] stringToStringArray(String src, int length) {
        //检查参数是否合法
        if (null == src || src.equals("")) {
            return null;
        }

        if (length <= 0) {
            return null;
        }
        int n = (src.length() + length - 1) / length; //获取整个字符串可以被切割成字符子串的个数
        String[] split = new String[n];
        for (int i = 0; i < n; i++) {
            if (i < (n - 1)) {
                split[i] = src.substring(i * length, (i + 1) * length);
            } else {
                split[i] = src.substring(i * length);
            }
        }
        return split;
    }


    private static void toList(ListNode listNode, StringBuilder builder) {
        builder.append(listNode.val);
        if (!Objects.isNull(listNode.next)) {
            toList(listNode.next, builder);
        }
    }


    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
