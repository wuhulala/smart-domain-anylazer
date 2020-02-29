package com.wuhulala.sda.reader.comp.crawler;

public interface CrawlerDataWriter {

    void init();

    void write(CrawlerData data);

    void destroy();
}
