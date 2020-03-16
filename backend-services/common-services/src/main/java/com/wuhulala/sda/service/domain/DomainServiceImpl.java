package com.wuhulala.sda.service.domain;

import org.springframework.stereotype.Service;

@Service
public class DomainServiceImpl implements DomainService {

    @Override
    public String sayHello(String name) {
        return "hello " + name + " !";
    }
}
