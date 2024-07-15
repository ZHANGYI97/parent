package com.ziyi.leetcode.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhy
 * @data 2023/6/29 20:31
 */
public class Test {

    public static void main(String[] args) {
/*        List<TreeNode> treeNodeList = new ArrayList<>();
        treeNodeList.add(new TreeNode("A", null, 0, Arrays.asList(
                new TreeNode("B", "A", 0, null),
                new TreeNode("C", "A", 1, null)
        )));

        List<Tree> treeList = Arrays.asList(
                new Tree("B"),
                new Tree("C")
        );

        boolean flag = checkConditions(treeNodeList, treeList);*/


        List<TreeNode> treeNodeList = new ArrayList<>();

// 第一级节点
        TreeNode node1 = new TreeNode("A", null, 1, new ArrayList<>());
        TreeNode node3 = new TreeNode("C", "A", 1, new ArrayList<>());
        TreeNode node5 = new TreeNode("E", "C", 0, new ArrayList<>());
        TreeNode node6 = new TreeNode("F", "C", 1, new ArrayList<>());
        TreeNode node9 = new TreeNode("I", "E", 0, new ArrayList<>());
        TreeNode node10 = new TreeNode("J", "E", 0, new ArrayList<>());
        TreeNode node11 = new TreeNode("K", "F", 1, new ArrayList<>());
        TreeNode node12 = new TreeNode("L", "F", 0, new ArrayList<>());


        TreeNode node2 = new TreeNode("B", null, 1, new ArrayList<>());
// 第二级节点
        TreeNode node4 = new TreeNode("D", "B", 0, new ArrayList<>());
// 第三级节点
        TreeNode node7 = new TreeNode("G", "D", 0, new ArrayList<>());
        TreeNode node8 = new TreeNode("H", "D", 0, new ArrayList<>());
// 第四级节点
        TreeNode node13 = new TreeNode("M", "G", 0, new ArrayList<>());
        TreeNode node14 = new TreeNode("N", "G", 0, new ArrayList<>());
        TreeNode node15 = new TreeNode("O", "H", 0, new ArrayList<>());
        TreeNode node16 = new TreeNode("P", "H", 0, new ArrayList<>());

// 构建树形结构
        node1.getChild().add(node3); // A 的子节点为 C
        node1.getChild().add(node4); // A 的子节点为 D

        node3.getChild().add(node5); // C 的子节点为 E
        node3.getChild().add(node6); // C 的子节点为 F
        node4.getChild().add(node7); // D 的子节点为 G
        node4.getChild().add(node8); // D 的子节点为 H

        node5.getChild().add(node9);  // E 的子节点为 I
        node5.getChild().add(node10); // E 的子节点为 J
        node6.getChild().add(node11); // F 的子节点为 K
        node6.getChild().add(node12); // F 的子节点为 L
        node7.getChild().add(node13); // G 的子节点为 M
        node7.getChild().add(node14); // G 的子节点为 N
        node8.getChild().add(node15); // H 的子节点为 O
        node8.getChild().add(node16); // H 的子节点为 P

// 将根节点添加到 treeNodeList
        treeNodeList.add(node1);
        treeNodeList.add(node2);

        List<Tree> treeList = Arrays.asList(
                new Tree("E"),
                new Tree("D")
        );

        boolean flag = checkConditions(treeNodeList, treeList);

        System.out.println(flag);

    }

    public static boolean checkConditions(List<TreeNode> treeNodeList, List<Tree> treeList) {
        for (Tree tree : treeList) {
            if (!isAllParentsStateOne(treeNodeList, tree.getCode())) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAllParentsStateOne(List<TreeNode> treeNodeList, String code) {
        List<String> parentCodes = getParentCodes(treeNodeList, code);

        for (String parentCode : parentCodes) {
            TreeNode parentNode = findNodeByCode(treeNodeList, parentCode);
            if (parentNode == null || parentNode.getState() != 1) {
                return false;
            }
        }

        return true;
    }

    private static List<String> getParentCodes(List<TreeNode> treeNodeList, String code) {
        List<String> parentCodes = new ArrayList<>();
        TreeNode node = findNodeByCode(treeNodeList, code);

        if (node != null) {
            String parentCode = node.getPCode();
            while (parentCode != null) {
                parentCodes.add(parentCode);
                TreeNode parentNode = findNodeByCode(treeNodeList, parentCode);
                if (parentNode != null) {
                    parentCode = parentNode.getPCode();
                } else {
                    parentCode = null;
                }
            }
        }

        return parentCodes;
    }

    private static TreeNode findNodeByCode(List<TreeNode> treeNodeList, String code) {
        for (TreeNode node : treeNodeList) {
            if (node.getCode().equals(code)) {
                return node;
            }

            TreeNode childMatch = findNodeByCode(node.getChild(), code);
            if (childMatch != null) {
                return childMatch;
            }
        }

        return null;
    }

}
