package com.wuhulala.sda.reader.comp.crawler;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.MongoCollection;
import com.wuhulala.sda.model.DomainContent;
import com.wuhulala.sda.utils.MongoDBUtils;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;


@Slf4j
public class MongoDbDomainContentWriter implements DomainContentWriter {

    private MongoCollection mongoCollection;

    @Override
    public void init() {
        mongoCollection = MongoDBUtils.mongoDatabase().getCollection("sda_domain_content");
        log.info(">>>>>> MongoDbCrawlerDataWriter 初始化成功...");
    }


    @Override
    public void write(DomainContent data) {
        mongoCollection.insertOne(Document.parse(JSON.toJSONString(data)));
    }


    @Override
    public void destroy() {

    }
}
