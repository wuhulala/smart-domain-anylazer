package com.wuhulala.sda.dto;

import com.wuhulala.base.dto.req.BaseReq;
import com.wuhulala.sda.model.DomainGroup;
import lombok.Data;

@Data
public class DomainGroupReq extends BaseReq<DomainGroup> {

    private String parentId;

}
