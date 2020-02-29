package com.wuhulala.sda.aggreate;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.wuhulala.sda.model.DomainContentWord;
import com.wuhulala.sda.model.DomainWordResult;
import com.wuhulala.sda.utils.MongoDBUtils;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.bson.Document;

import java.util.*;
import java.util.function.Consumer;

public class DefaultDomainWordAggregator implements DomainWordAggregator {

    @Override
    public List<DomainWordResult> aggregate() throws Exception {
        MongoCollection<Document> domainContentWordCol = MongoDBUtils.mongoDatabase().getCollection("sda_domain_content_word");

        FindIterable<Document> originData = domainContentWordCol.find();
        List<DomainContentWord> documentList = new ArrayList<>();
        originData.forEach(new Consumer<Document>() {
            @Override
            public void accept(Document document) {
                documentList.add(JSON.parseObject(JSON.toJSONString(document), DomainContentWord.class));
            }
        });
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        return env.fromCollection(documentList).groupBy("domainName").reduce(new ReduceFunction<DomainContentWord>() {
            @Override
            public DomainContentWord reduce(DomainContentWord value1, DomainContentWord value2) throws Exception {
                Map<String, Integer> value1TitleFq = value1.getTitleWordFrequency();
                Map<String, Integer> value2TitleFq = value2.getTitleWordFrequency();
                Set<String> keySet = new HashSet<>();
                keySet.addAll(value1TitleFq.keySet());
                keySet.addAll(value2TitleFq.keySet());
                for (String key : keySet) {
                    value1TitleFq.merge(key, value2TitleFq.getOrDefault(key, 0), Integer::sum);
                }
                Map<String, Integer> value1ContentFq = value1.getContentWordFrequency();
                Map<String, Integer> value2ContentFq = value2.getContentWordFrequency();
                Set<String> keySet2 = new HashSet<>();
                keySet2.addAll(value1ContentFq.keySet());
                keySet2.addAll(value2ContentFq.keySet());
                for (String key : keySet2) {
                    value1ContentFq.merge(key, value2ContentFq.getOrDefault(key, 0), Integer::sum);
                }
                return value1;
            }
        }).flatMap(new FlatMapFunction<DomainContentWord, DomainWordResult>() {
            @Override
            public void flatMap(DomainContentWord value, Collector<DomainWordResult> out) throws Exception {
                DomainWordResult r1 = new DomainWordResult();
                r1.setDomainId(value.getDomainId());
                r1.setDomainName(value.getDomainName());
                r1.setTop100(value.getTitleWordFrequency());
                r1.setType("1");
                r1.setCreator("DefaultDomainWordAggregator");
                r1.setGmtCreate(new Date());
                out.collect(r1);

                DomainWordResult r2 = new DomainWordResult();
                r2.setDomainId(value.getDomainId());
                r2.setDomainName(value.getDomainName());
                r2.setTop100(value.getContentWordFrequency());
                r2.setType("2");
                r2.setCreator("DefaultDomainWordAggregator");
                r2.setGmtCreate(new Date());
                out.collect(r2);
            }
        }).collect();

    }

}
