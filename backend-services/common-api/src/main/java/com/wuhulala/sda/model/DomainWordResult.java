package com.wuhulala.sda.model;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class DomainWordResult {

    private String domainId;

    private String domainName;

    /**
     * 1 title, 2 content
     */
    private String type;

    private Map<String, Integer> top100;

    private Date gmtCreate;

    private String creator;
}
