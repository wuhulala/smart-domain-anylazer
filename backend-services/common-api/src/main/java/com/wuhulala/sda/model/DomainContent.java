package com.wuhulala.sda.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class DomainContent {

    private String domainId;

    private String title;

    /**
     * 类型: pdf/网页/ppt/图片/word...
     */
    private String contentType;

    private String origin;

    private String author;

    private String pubDate;

    private String content;

    private Date gmtCreate;

    private String creator;

}
