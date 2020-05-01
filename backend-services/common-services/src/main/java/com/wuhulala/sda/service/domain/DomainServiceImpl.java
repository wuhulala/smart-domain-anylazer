package com.wuhulala.sda.service.domain;

import com.wuhulala.sda.dto.*;
import com.wuhulala.sda.logic.domain.DomainLogic;
import com.wuhulala.sda.model.Domain;
import com.wuhulala.sda.model.DomainGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wuhulala
 */
@Service
public class DomainServiceImpl implements DomainService {

    @Autowired
    private DomainLogic domainLogic;

    @Override
    public DomainGroupResp addDomainGroup(DomainGroupReq req) {
        DomainGroupResp resp = new DomainGroupResp();
        domainLogic.addDomainGroup(req.getQuery(), "system");
        resp.setItem(req.getQuery());
        return resp;
    }

    @Override
    public DomainGroupResp updateDomainGroup(DomainGroupReq req) {
        domainLogic.updateDomainGroup(req.getQuery(), "system");
        return new DomainGroupResp();
    }

    @Override
    public DomainGroupResp deleteDomainGroup(DomainGroupReq req) {
        domainLogic.deleteDomainGroup(req.getQuery());
        return new DomainGroupResp();
    }


    @Override
    public DomainGroupResp findDomainGroupByParent(DomainGroupReq req) {
        List<DomainGroup> list = domainLogic.findDomainGroupByParent(req.getParentId());
        DomainGroupResp resp = new DomainGroupResp();
        resp.setList(list);
        return resp;
    }

    @Override
    public DomainResp findDomainByGroup(DomainReq req) {
        List<Domain> list = domainLogic.findDomainByGroup(req.getGroupId());
        DomainResp resp = new DomainResp();
        resp.setList(list);
        return resp;
    }

    @Override
    public DomainResp addDomain(DomainReq req) {
        DomainResp resp = new DomainResp();
        domainLogic.addDomain(req.getQuery(), "system");
        resp.setItem(req.getQuery());
        return resp;
    }

    @Override
    public DomainResp updateDomain(DomainReq req) {
        domainLogic.updateDomain(req.getQuery(), "system");
        return new DomainResp();
    }

    @Override
    public DomainResp deleteDomain(DomainReq req) {
        domainLogic.deleteDomain(req.getQuery());
        return new DomainResp();
    }

    @Override
    public DomainResp findLeast5Domain(DomainReq req) {
        List<Domain> list = domainLogic.findLeastDomain(5);
        DomainResp resp = new DomainResp();
        resp.setList(list);
        return resp;
    }

    /**
     *
     * @param req
     * @return
     */
    @Override
    public DomainResp findReview5Domain(DomainReq req) {
        // 根据访问日志计算出访问时间， 然后根据艾宾浩斯-遗忘曲线算法 + 知识权重排序 来计算。

//        1． 第一个记忆周期：5分钟
//
//        2． 第二个记忆周期：30分钟
//
//        3． 第三个记忆周期：12小时
//
//        4． 第四个记忆周期：1天
//
//        5． 第五个记忆周期：2天
//
//        6． 第六个记忆周期：4天
//
//        7． 第七个记忆周期：7天
//
//        8． 第八个记忆周期：15天
        List<Domain> list = domainLogic.findReviewDomain(5);
        DomainResp resp = new DomainResp();
        resp.setList(list);
        return resp;
    }

    @Override
    public DomainTreeResp findAllDomainWithTree(DomainReq req) {
        DomainTreeResp resp = new DomainTreeResp();
        resp.setTree(domainLogic.buildDomainTree());
        return resp;
    }
}
