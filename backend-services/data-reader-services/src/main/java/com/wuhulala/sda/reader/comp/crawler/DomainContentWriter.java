package com.wuhulala.sda.reader.comp.crawler;

import com.wuhulala.sda.model.DomainContent;

public interface DomainContentWriter {

    void init();

    void write(DomainContent data);

    void destroy();
}
