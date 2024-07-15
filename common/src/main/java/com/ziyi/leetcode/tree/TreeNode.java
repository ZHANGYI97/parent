package com.ziyi.leetcode.tree;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author zhy
 * @data 2023/6/29 20:24
 */
@Data
@AllArgsConstructor
public class TreeNode {

    private String code;

    private String pCode;

    private Integer state;

    private List<TreeNode> child;

}
