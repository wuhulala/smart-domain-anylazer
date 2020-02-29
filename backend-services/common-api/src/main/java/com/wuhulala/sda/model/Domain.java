package com.wuhulala.sda.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Domain {

    private String domainId;

    private String name;

    private Date gmtCreate;

    private String creator;

}
