package com.wuhulala.sda.dto;

import com.wuhulala.base.dto.req.BaseReq;
import com.wuhulala.sda.model.Domain;
import lombok.Data;

@Data
public class DomainReq extends BaseReq<Domain> {

    private String groupId;
}
