package com.wuhulala.sda.reader.comp.crawler;

import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;

public abstract class DomainBreadthCrawler extends BreadthCrawler {
    private String domain;

    public DomainBreadthCrawler(String crawlPath, boolean autoParse, String domain) {
        super(crawlPath, autoParse);
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }
}
