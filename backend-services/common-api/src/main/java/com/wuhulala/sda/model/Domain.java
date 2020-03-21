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


}
