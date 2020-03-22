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
public class Domain {

    private String id;

    private String name;

    private Date gmtCreate;

    private Date gmtUpdate;

    private String creator;

    private String groupId;

//    /**图标*/
//    private String icon;
//
//    /**简介*/
//    private String desc;
//
//    /**子标题*/
//    private String subTitle;
//
//    /**主体架构图*/
//    private String arc;
//
//    /**思维导图*/






}
