package com.wuhulala.sda.reader.comp;

/**
 * 智能读取器
 */
public interface SmartReader {

    void init();

    void open();

    Object getResult();

    String getType();

    void destory();

}
