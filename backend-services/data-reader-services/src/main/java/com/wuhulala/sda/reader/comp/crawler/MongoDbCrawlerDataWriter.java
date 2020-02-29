package com.wuhulala.sda.reader.comp.crawler;

import com.mongodb.client.MongoCollection;
import com.wuhulala.sda.reader.util.MongoDBUtils;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MongoDbCrawlerDataWriter implements CrawlerDataWriter {

    private MongoCollection mongoCollection;

    @Override
    public void init() {
        mongoCollection = MongoDBUtils.mongoDatabase().getCollection("crawler_origin_data");
        log.info(">>>>>> MongoDbCrawlerDataWriter 初始化成功...");
    }


    @Override
    public void write(CrawlerData data) {
        mongoCollection.insertOne(data.toDocument());
    }


    @Override
    public void destroy() {

    }
}
