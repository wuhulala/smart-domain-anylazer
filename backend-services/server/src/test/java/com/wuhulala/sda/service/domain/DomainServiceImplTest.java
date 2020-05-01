package com.wuhulala.sda.service.domain;

import com.alibaba.fastjson.JSON;
import com.wuhulala.sda.dto.DomainGroupReq;
import com.wuhulala.sda.dto.DomainGroupResp;
import com.wuhulala.sda.dto.DomainReq;
import com.wuhulala.sda.dto.DomainResp;
import com.wuhulala.sda.model.Domain;
import com.wuhulala.sda.model.DomainGroup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Rollback
@SpringBootTest
@RunWith(SpringRunner.class)
public class DomainServiceImplTest {

    @Autowired
    private DomainServiceImpl domainService;

    @Test
    public void addDomainGroup() {
        DomainGroup domainGroup = DomainGroup.builder().groupName("大数据").parentId("-1").gmtCreate(new Date()).build();
        DomainGroupReq req = new DomainGroupReq();
        req.setQuery(domainGroup);
        DomainGroupResp resp = domainService.addDomainGroup(req);
        domainGroup = resp.getItem();
        Assert.assertTrue(resp.isSuccess());

        Domain domain = Domain.builder().name("Flink").groupId(domainGroup.getId()).gmtCreate(new Date()).creator("wuhulala").build();
        DomainReq domainReq = new DomainReq();
        domainReq.setQuery(domain);
        DomainResp domainResp = domainService.addDomain(domainReq);
        Assert.assertTrue(domainResp.isSuccess());

        domainReq.getQuery().setName("Flink_new");
        domainService.updateDomain(domainReq);
        domainService.updateDomain(domainReq);

        domainReq.setGroupId(domainGroup.getId());
        DomainResp domainListResp = domainService.findDomainByGroup(domainReq);
        System.out.println(JSON.toJSON(domainListResp));

        req.setParentId("-1");
        DomainGroupResp groupListResp = domainService.findDomainGroupByParent(req);
        System.out.println(JSON.toJSON(groupListResp));

        domainService.deleteDomain(domainReq);
        domainService.deleteDomainGroup(req);

    }

    @Test
    public void updateDomainGroup() {
    }

    @Test
    public void deleteDomainGroup() {
    }

    @Test
    public void findDomainGroupResp() {
    }

    @Test
    public void addDomain() {
    }

    @Test
    public void updateDomain() {
    }

    @Test
    public void deleteDomain() {
    }

    @Test
    public void findAllDomainWithTree() {
        System.out.println(JSON.toJSON(domainService.findAllDomainWithTree(new DomainReq())));

    }
}