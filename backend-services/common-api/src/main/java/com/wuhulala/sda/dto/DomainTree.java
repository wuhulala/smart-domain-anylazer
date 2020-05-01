package com.wuhulala.sda.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

@Data
@Slf4j
public class DomainTree implements Comparable<DomainTree> {

    /**
     * 根编码
     */
    public static final String ROOT_CODE = "-1";

    private String name;

    private String code;

//    private String style;

//    private String type;


    private String parentCode;

//    private String level;

//    private String parentName;

//    private String expand;

    private LinkedList<DomainTree> children;

//    private String shortName;

    /**
     * 异步加载使用
     */
    private String loading;

    /**
     * 是否叶子节点
     */
    private String leaf;

    /**
     * 节点排序字段
     */
    private BigDecimal sortDouble;

    public static DomainTree valueOf(String id, String name, String parentId) {
        DomainTree domainTree = new DomainTree();
        domainTree.setCode(id);
        domainTree.setName(name);
        domainTree.setParentCode(parentId);
        return domainTree;
    }

    public void checkChildrenInit() {
        if (children == null) {
            children = new LinkedList<>();
        }
    }

    public void addFirstChild(DomainTree node) {
        checkChildrenInit();
        children.addFirst(node);
    }

    public void addLastChild(DomainTree node) {
        checkChildrenInit();
        children.addLast(node);
    }

    public void addTree(int index, DomainTree node) {
        checkChildrenInit();
        children.add(index, node);
    }

    public void addTree(String parentCode, DomainTree node, DomainTree root) {

        DomainTree parent = findTreeByCode(parentCode);

        if (parent == null) {
            parent = root;
        }

        if (CollectionUtils.isEmpty(parent.getChildren())) {
            parent.addFirstChild(node);
            return;
        }

        int rank = 0;
        for (DomainTree each : parent.getChildren()) {
            if (node.compareTo(each) <= 0) {
                parent.addTree(rank, node);
                return;
            }
            rank++;
        }
        parent.addTree(rank, node);
    }

    public DomainTree findTreeByCode(String code) {

        if (Objects.equals(code, this.getCode())) {
            return this;
        }

        Queue<DomainTree> queue = new LinkedList<>();
        queue.add(this);

        while (!queue.isEmpty()) {
            DomainTree node = queue.poll();
            if (node.getChildren() != null) {
                for (DomainTree each : node.getChildren()) {
                    if (Objects.equals(code, each.getCode())) {
                        return each;
                    }
                }
            }
            queue.addAll(CollectionUtils.emptyIfNull(node.getChildren()));
        }

        return null;
    }

    public List<DomainTree> findRootChildren() {

        if (ROOT_CODE.equals(code)) {
            return this.getChildren();
        }
        return null;
    }

    public List<DomainTree> queryAllNodes() {
        List<DomainTree> result = new ArrayList<>();
        Queue<DomainTree> queue = new LinkedList<>();
        queue.add(this);
        if (!ROOT_CODE.equals(this.getCode())) {
            result.add(this);
        }
        while (!queue.isEmpty()) {
            DomainTree node = queue.poll();
            result.addAll(CollectionUtils.emptyIfNull(node.getChildren()));
            queue.addAll(CollectionUtils.emptyIfNull(node.getChildren()));
            node.setChildren(null);
        }
        return result;
    }


    @Override
    public int compareTo(DomainTree o) {
        if (this.getSortDouble() != null && o.getSortDouble() != null) {
            int tmp = this.getSortDouble().compareTo(o.getSortDouble());
            if (tmp != 0) {
                return tmp;
            }
        }
        int tmp = 0/*this.getType().compareTo(o.getType())*/;
        if (tmp == 0) {
            return this.getCode().compareTo(o.getCode());
        }
        return tmp;
    }

}
