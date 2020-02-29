package com.wuhulala.sda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DomainGroup {

    private String groupName;

    private String groupId;

    private String parentId;

    private Date gmtCreate;

    private String creator;
}
