package com.wuhulala.sda.service.domain;

import com.wuhulala.rest.base.annotation.RestService;
import com.wuhulala.rest.base.annotation.RestServiceModule;

import static com.wuhulala.rest.base.HTTPMethod.GET;
import static com.wuhulala.rest.base.HTTPMethod.POST;

@RestServiceModule("/domain")
public interface DomainService {

    @RestService(name="测试Hello", types= {GET, POST}, path = "/hello", desc = "测试程序")
    String sayHello(String name);
}
