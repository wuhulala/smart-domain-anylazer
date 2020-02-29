package com.wuhulala.sda.extractor.comp;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.wuhulala.sda.model.DomainContent;
import com.wuhulala.sda.model.DomainContentWord;
import com.wuhulala.sda.utils.MongoDBUtils;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

import static org.junit.Assert.*;

public class SmartNlpExtractorTest {

    private static final Logger logger = LoggerFactory.getLogger(SmartNlpExtractorTest.class);

    private SmartNlpExtractor extractor;

    @Before
    public void init() {
        extractor = new SmartNlpExtractor();
    }

    //todo we can use flink to impl
    @org.junit.Test
    public void extractWord() {
        MongoCollection<Document> domainContentCol = MongoDBUtils.mongoDatabase().getCollection("sda_domain_content");
        MongoCollection<Document> domainContentWordCol = MongoDBUtils.mongoDatabase().getCollection("sda_domain_content_word");
        domainContentWordCol.deleteMany(Filters.exists("_id"));
        domainContentCol.find().forEach(new Consumer<Document>() {
            @Override
            public void accept(Document document) {
                DomainContent content = JSON.parseObject(JSON.toJSONString(document), DomainContent.class);
                DomainContentWord domainContentWord = extractor.extractWord(content);
                try {

                    domainContentWordCol.insertOne(Document.parse(JSON.toJSONString(domainContentWord)));
                } catch (Exception e){
                    logger.error("插入失败, {}", Document.parse(JSON.toJSONString(domainContentWord)), e);
                }

            }
        });

    }
}