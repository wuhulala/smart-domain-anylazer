package com.wuhulala.sda.logic.domain;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wuhulala.sda.dto.DomainTree;
import com.wuhulala.sda.mapper.domain.DomainGroupMapper;
import com.wuhulala.sda.mapper.domain.DomainMapper;
import com.wuhulala.sda.model.Domain;
import com.wuhulala.sda.model.DomainGroup;
import com.wuhulala.sda.util.DomainTreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DomainLogic {

    @Autowired
    private DomainMapper domainMapper;

    @Autowired
    private DomainGroupMapper domainGroupMapper;


    public void addDomainGroup(DomainGroup query, String system) {
        query.setCreator(system);
        query.setGmtCreate(new Date());
        domainGroupMapper.insert(query);
    }

    public void updateDomainGroup(DomainGroup query, String system) {
        query.setGmtUpdate(new Date());
        domainGroupMapper.updateById(query);
    }

    public void deleteDomainGroup(DomainGroup query) {
        domainGroupMapper.deleteById(query.getId());
    }

    public List<DomainGroup> findDomainGroupByParent(String parentId) {
        Wrapper<DomainGroup> wrapper = Wrappers.<DomainGroup>lambdaQuery().eq(DomainGroup::getParentId, parentId);
        return domainGroupMapper.selectList(wrapper);
    }

    public void addDomain(Domain query, String system) {
        query.setCreator(system);
        query.setGmtCreate(new Date());
        domainMapper.insert(query);
    }

    public void updateDomain(Domain query, String system) {
        query.setGmtUpdate(new Date());
        domainMapper.updateById(query);
    }

    public void deleteDomain(Domain query) {
        domainMapper.deleteById(query.getId());
    }


    public List<Domain> findDomainByGroup(String groupId) {
        Wrapper<Domain> wrapper = Wrappers.<Domain>lambdaQuery().eq(Domain::getGroupId, groupId);
        return domainMapper.selectList(wrapper);
    }

    public List<Domain> findLeastDomain(int limit) {
        Wrapper<Domain> wrapper = Wrappers.<Domain>lambdaQuery().orderByDesc(Domain::getGmtCreate).last("limit " + limit);
        return domainMapper.selectList(wrapper);
    }

    public List<Domain> findReviewDomain(int limit) {
        Wrapper<Domain> wrapper = Wrappers.<Domain>lambdaQuery().last("limit " + limit);
        return domainMapper.selectList(wrapper);
    }

    public DomainTree buildDomainTree() {
        List<DomainTree> groupNodes = domainGroupMapper.selectList(Wrappers.lambdaQuery())
                .stream()
                .map(group -> DomainTree.valueOf(group.getId(), group.getGroupName(), group.getParentId()))
                .collect(Collectors.toList());
        List<DomainTree> domainNodes = domainMapper
                .selectList(Wrappers.lambdaQuery())
                .stream()
                .map(domain -> DomainTree.valueOf(domain.getId(), domain.getName(), domain.getGroupId()))
                .collect(Collectors.toList());
        groupNodes.addAll(domainNodes);
        return DomainTreeUtils.buildTree(groupNodes);
    }
}
