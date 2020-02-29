package com.wuhulala.sda.aggreate;

import com.alibaba.fastjson.JSON;
import com.wuhulala.sda.model.DomainWordResult;
import org.junit.Test;

import java.util.*;


public class DefaultDomainWordAggregatorTest {

    public static class MaxHeap<E extends Comparable<E>> {
        private PriorityQueue<E> queue;
        private int size;

        public MaxHeap(int size) {
            this.size = size;
            this.queue = new PriorityQueue<>(size, Comparator.naturalOrder());
        }

        public void add(E value) {
            if (queue.size() < size) {
                queue.add(value);
            } else {
                if (queue.peek().compareTo(value) < 0) {
                    queue.poll();
                    queue.add(value);
                }
            }
        }

        /**
         * 转为有序列表，自毁性操作
         * @return
         */
        public List<E> toList()
        {
            ArrayList<E> list = new ArrayList<E>(queue.size());
            while (!queue.isEmpty())
            {
                list.add(0, queue.poll());
            }

            return list;
        }
    }

    public static class KeyValue implements Comparable<KeyValue> {
        private String name;

        private Integer value;

        public static KeyValue of(String name, Integer value) {
            KeyValue kv = new KeyValue();
            kv.name = name;
            kv.value = value;
            return kv;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        @Override
        public int compareTo(KeyValue keyValue) {
            return value.compareTo(keyValue.value);
        }
    }

    @Test
    public void aggregate() {
        DefaultDomainWordAggregator aggregator = new DefaultDomainWordAggregator();
        try {
            List<DomainWordResult> result = aggregator.aggregate();

            System.out.println(JSON.toJSONString(result, true));

            System.out.println("-----------------------------------------------------------------");
            result.forEach(r -> {
                MaxHeap<KeyValue> wcdata = new MaxHeap<>(100);
                for (Map.Entry<String, Integer> each : r.getTop100().entrySet()) {
                    wcdata.add(KeyValue.of(each.getKey(), each.getValue()));
                }
                System.out.println(JSON.toJSONString(wcdata.toList()));

                System.out.println("-----------------------------------------------------------------");
            });
            // https://gallery.echartsjs.com/editor.html?c=xS1jMxuOVm 词云展示

            // 1. 分析结果
            // 应该是大数据 => 不是数据
            // Flink 和 flink 应该是一样的。
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}