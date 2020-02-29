package com.wuhulala.sda.reader.util;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;


public class MongoDBUtils {

    public static MongoDatabase mongoDatabase() {


        // 连接到 mongodb 服务
        MongoClient mongoClient = new MongoClient("192.168.1.101", 27017);

        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("data");
        System.out.println("Connect to database successfully");


        return mongoDatabase;
    }


}
