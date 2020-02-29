package com.wuhulala.sda.model;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 领域内容
 * <p>
 * reader -> read
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JSONType(naming= PropertyNamingStrategy.SnakeCase)
public class DomainContent {

    private String id;

    private String domainId;

    private String domainName;

    private String title;

    private String readerType;

    /**
     * 类型: pdf/网页/ppt/图片/word...
     */
    private String sourceType;

    private String origin;

    private String author;

    private String pubDate;

    private String content;

    private Date gmtCreate;

    private String creator;

}
