package com.wuhulala.sda.util;


import com.wuhulala.sda.dto.DomainTree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组织机构树
 *
 * @author xueah20964 2019/8/31 Create 1.0  <br>
 * @version 1.0
 */
public class DomainTreeUtils {

    public static DomainTree buildTree(List<DomainTree> nodes) {
        DomainTree root = new DomainTree();
        root.setCode("-1");
        root.setName("root");

        Map<String, Boolean> buildFlags = new HashMap<>();
        buildFlags.put("-1", true);
        nodes.forEach(node -> buildTreeNode(root, node, nodes, buildFlags));
        return root;
    }

    public static void buildTreeNode(DomainTree root, DomainTree node, List<DomainTree> nodes, Map<String, Boolean> buildFlags) {
        if (Boolean.TRUE.equals(buildFlags.get(node.getCode()))) {
            System.out.println(">>>> node existed :::::" + node);
            return;
        }
        if (!Boolean.TRUE.equals(buildFlags.get(node.getParentCode()))) {
            nodes.stream()
                    .filter(tmp -> node.getParentCode().equals(tmp.getCode()))
                    .findFirst()
                    .ifPresent(
                            parent -> buildTreeNode(root, parent, nodes, buildFlags)
                    );
        }
        buildFlags.put(node.getCode(), true);
        root.addTree(node.getParentCode(), node, root);
    }

}
