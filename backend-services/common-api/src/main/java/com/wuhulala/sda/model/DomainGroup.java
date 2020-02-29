package com.wuhulala.sda.model;

import lombok.Data;

import java.util.Date;

@Data
public class DomainGroup {

    private String groupName;

    private String groupId;

    private String parentId;

    private Date gmtCreate;

    private String creator;
}
