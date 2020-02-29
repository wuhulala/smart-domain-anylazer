package com.wuhulala.sda.reader.comp.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializeConfig;
import lombok.Builder;
import lombok.Data;
import org.bson.Document;

import java.util.Date;

@Data
@Builder
@JSONType(naming=PropertyNamingStrategy.SnakeCase)
public class CrawlerData {

    private String domain;

    private String url;

    private String title;

    private String author;

    private String pubDate;

    private String content;

    private Date gmtCreate;

    public Object toJson() {
        return JSON.toJSON(this);
    }

    public Document toDocument() {
        Document document = new Document();
        document.put("domain", domain);
        document.put("url", url);
        document.put("title", title);
        document.put("author", author);
        document.put("pub_date", pubDate);
        document.put("content", content);
        document.put("gmt_create", gmtCreate);
        return document;
    }
}
