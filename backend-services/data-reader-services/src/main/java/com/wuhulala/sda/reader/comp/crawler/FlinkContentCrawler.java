package com.wuhulala.sda.reader.comp.crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.wuhulala.sda.reader.util.PageUtils;
import com.wuhulala.sda.reader.util.ThreadExecutorUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Flink中文官方网站爬虫
 *
 * <p>1. 只爬取一层</p>
 * <p>2. 需自定义设置字段名称与规则</p>
 */
@Slf4j
public class FlinkContentCrawler extends DomainBreadthCrawler {

    private String seedUrl;
    private String pageRegex;
    // json/xml
    private String seedType;
    private CrawlerDataWriter writer;
    private ThreadPoolExecutor threadPoolExecutor;


    /**
     * @param crawlPath crawlPath is the path of the directory which maintains
     *                  information of this crawler
     * @param autoParse if autoParse is true,BreadthCrawler will auto extract
     *                  links which match regex rules from page
     */
    public FlinkContentCrawler(String seedUrl, String pageRegex, String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse, "flink");
        this.seedUrl = seedUrl;
        this.pageRegex = pageRegex;

        /**设置爬取的网站地址
         * addSeed 表示添加种子
         * 种子链接会在爬虫启动之前加入到抓取信息中并标记为未抓取状态.这个过程称为注入*/
        this.addSeed(seedUrl);

        /** addRegex 参数为一个 url 正则表达式, 可以用于过滤不必抓取的链接，如 .js .jpg .css ... 等
         * 也可以指定抓取某些规则的链接，如下 addRegex 中会抓取 此类地址：
         * https://blog.github.com/2018-07-13-graphql-for-octokit/
         * */
        this.addRegex(pageRegex);
        this.addRegex("-.*\\.(jpg|png|gif).*");
        this.addRegex("-.*\\.(css|js).*");
        /**
         * 过滤 jpg|png|gif 等图片地址 时：
         * this.addRegex("-.*\\.(jpg|png|gif).*");
         * 过滤 链接值为 "#" 的地址时：
         * this.addRegex("-.*#.*");
         */

        /**设置线程数*/
        setThreads(50);
        getConf().setTopN(100);

        /**
         * 是否进行断电爬取，默认为 false
         *
         */
//        setResumable(true);

        writer = new MongoDbCrawlerDataWriter();
        writer.init();

        threadPoolExecutor = ThreadExecutorUtils.newThreadPoolExecutor("crawler-data-saver-", 1, 10);
    }

    /**
     * 必须重写 visit 方法，作用是:
     * 在整个抓取过程中,只要抓到符合要求的页面,webCollector 就会回调该方法,并传入一个包含了页面所有信息的 page 对象
     *
     * @param page
     * @param next
     */
    @Override
    public void visit(Page page, CrawlDatums next) {
        String url = page.url();
        /**如果此页面地址 确实是要求爬取网址，则进行取值
         */
        log.info("当前爬取>>>>>>>> " + url);
        if ("application/json".equals(page.contentType())) {
            String html = page.html();
            if (JSON.isValidArray(html)) {
                JSONArray array = JSON.parseArray(html);
                array.forEach(obj -> {
                    JSONObject jsonObject = (JSONObject) obj;
                    next.add(jsonObject.getString("permalink"));
                });
            }
        } else if (page.matchUrl(pageRegex)) {

            /**
             * 通过 选择器 获取页面 标题以及 正文内容
             * */
            processData(page);
        }
    }

    private void processData(final Page page) {
        threadPoolExecutor.execute(() -> {
            try {
                doProcessData(page);
            } catch (Exception e) {
                log.error("处理#{} 失败.", page.url(), e);
            }
        });
    }

    private void doProcessData(final Page page) {
        String title = "";
        if (page.select("h1[class=entry-title]").first() != null) {
            title = page.selectText("h1[class=entry-title]");
        }
        String content = "";
        if (page.select("div.entry-content").first() != null) {
            content = page.selectText("div.entry-content");
        }
        String author = "";
        if (page.select("span.author").first() != null) {
            author = page.selectText("span.author");
        }
        String pubDate = "";
        if (page.select("time.published").first() != null) {
            pubDate = page.select("time.published").first().attr("datetime");
        };
        writer.write(CrawlerData.builder()
                .domain(getDomain())
                .title(title)
                .pubDate(pubDate)
                .content(content)
                .author(author)
                .url(page.url())
                .gmtCreate(new Date())
                .build());
    }

    /**
     * webcollector自带获取html driver测试
     *
     * @param page
     */
    protected void handleByHtmlUnitDriver(Page page) {
        /*HtmlUnitDriver可以抽取JS生成的数据*/
        HtmlUnitDriver driver = PageUtils.getDriver(page, BrowserVersion.CHROME);
      /*HtmlUnitDriver也可以像Jsoup一样用CSS SELECTOR抽取数据
        关于HtmlUnitDriver的文档请查阅selenium相关文档*/
        print(driver);
    }

    /**
     * phantomjs driver测试
     *
     * @param page
     */
    protected void handleByPhantomJsDriver(Page page) {
        WebDriver driver = PageUtils.getDriver(page);
        print(driver);
        driver.quit();
    }

    protected void print(WebDriver driver) {
        List<WebElement> divInfos = driver.findElements(By.cssSelector("div.developers-info"));
        for (WebElement divInfo : divInfos) {
            WebElement price = divInfo.findElement(By.tagName("a"));
            System.out.println(price + ":" + price.getText());
        }
    }

    public String getCssSelectorConf(String key) {
        return conf.get(key);
    }

    @Data
    public static class WebSiteSelectorConfig {
        private String title;
        private String content;
        private String pubDate;
        private String author;
    }

    public static void main(String[] args) throws Exception {
        FlinkContentCrawler commonContentCrawler = new FlinkContentCrawler("https://ververica.cn/wp-admin/admin-ajax.php?action=my_ajax_sort_search", "https://ververica.cn/developers/.*", "crawler", true);
        commonContentCrawler.start(3);
    }

}
