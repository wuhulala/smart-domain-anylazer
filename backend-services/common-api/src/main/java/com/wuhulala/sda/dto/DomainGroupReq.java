package com.wuhulala.sda.dto;

import com.wuhulala.base.dto.req.BaseReq;
import com.wuhulala.sda.model.DomainGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DomainGroupReq extends BaseReq<DomainGroup> {

    private String parentId;

}
