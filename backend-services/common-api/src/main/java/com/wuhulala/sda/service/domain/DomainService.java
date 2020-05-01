package com.wuhulala.sda.service.domain;

import com.wuhulala.rest.base.annotation.RestService;
import com.wuhulala.rest.base.annotation.RestServiceModule;
import com.wuhulala.sda.dto.*;

@RestServiceModule("/domain")
public interface DomainService {

    @RestService(name="新增group",path = "group/add")
    DomainGroupResp addDomainGroup(DomainGroupReq req);

    @RestService(name="更新group",path = "group/update")
    DomainGroupResp updateDomainGroup(DomainGroupReq req);

    @RestService(name="更新group",path = "group/delete")
    DomainGroupResp deleteDomainGroup(DomainGroupReq req);

    @RestService(name="根据parentId获取group",path = "group/find_domain_group_by_parent")
    DomainGroupResp findDomainGroupByParent(DomainGroupReq req);

    @RestService(name="根据group获取对应的领域",path = "/find_domain_by_group")
    DomainResp findDomainByGroup(DomainReq req);

    @RestService(name="新增领域",path = "add")
    DomainResp addDomain(DomainReq req);

    @RestService(name="更新领域基本信息",path = "update")
    DomainResp updateDomain(DomainReq req);

    @RestService(name="删除领域",path = "delete")
    DomainResp deleteDomain(DomainReq req);

    ///////////////////////////////////////

    @RestService(name="获取最新的5条Domain",path = "/least5")
    DomainResp findLeast5Domain(DomainReq req);

    @RestService(name="获取应该复习的Domain",path = "/review5")
    DomainResp findReview5Domain(DomainReq req);

    ///////////////////////////////////////

    @RestService(name="获取所有领域的视图",path = "/domain_tree")
    DomainTreeResp findAllDomainWithTree(DomainReq req);

}
