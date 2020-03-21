package com.wuhulala.sda.service.domain;

import com.wuhulala.sda.dto.DomainGroupReq;
import com.wuhulala.sda.dto.DomainGroupResp;
import com.wuhulala.sda.dto.DomainReq;
import com.wuhulala.sda.dto.DomainResp;
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
}
