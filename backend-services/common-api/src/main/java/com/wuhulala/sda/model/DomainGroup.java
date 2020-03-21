package com.wuhulala.sda.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainGroup {

    private String id;

    private String groupName;

    private String parentId;

    private Date gmtCreate;

    private Date gmtUpdate;

    private String creator;
}
