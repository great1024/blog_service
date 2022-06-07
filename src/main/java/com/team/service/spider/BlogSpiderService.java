package com.team.service.spider;


import com.team.model.po.BlogContent;
import com.team.model.po.BlogImage;
import com.team.model.po.BlogTask;
import com.team.repository.BlogContentSimpleRepository;
import com.team.service.BlogBaseService;
import com.team.service.BlogContentService;
import com.team.service.BlogImageService;
import com.team.service.spider.queue.QueueService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

/**
 * 每个链接都是一个 blogcontent 任务
 * 每获取点到一个链接 就添加到 blogcontent 列表中
 * 定时任务监听 队列长度，当队列小于100时就从 blogcontent 列表中拉取数据 来填充队列
 * 生成任务的时候需要带着任务ID
 */
@Slf4j
@Service
public class BlogSpiderService extends BlogBaseService implements Runnable {
    private static final BlockingQueue<Runnable> blockingQueueNode = new ArrayBlockingQueue<>(10);
    private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS, blockingQueueNode);

    @Resource
    private BlogContentService blogContentService;
    @Resource
    private BlogContentSimpleRepository blogContentSimpleRepository;
    @Resource
    private BlogImageService blogImageService;

    /**
     * 博客爬虫启动
     * 定时任务监听 队列长度，当队列小于50时就从 blogcontent 列表中拉取数据 来填充队列
     *
     * @throws IOException
     */
    private void start() {
        try {
            while (true) {
                BlogContent take = QueueService.Queue.take();
                Boolean result = addTask(() -> parse(take));
                if (!result) {
                    TimeUnit.SECONDS.sleep(1);
                }
            }
        } catch (Exception e) {
            if (e.getMessage().contains("state should be open")) {
                log.info("博客爬虫停止运行！");
                return;
            }
            log.info("博客爬虫出错：" + e.getMessage());
        }
    }

    private Boolean addTask(Runnable runnable) {
        if (threadPoolExecutor.getActiveCount() < 10 && blockingQueueNode.size() < 10) {
            threadPoolExecutor.execute(runnable);
            return true;
        }
        return false;
    }

    private void parse(BlogContent blogContent) {
        try {
            Document document = Jsoup.connect(blogContent.getBlogLink()).get();
            Optional<String> stringOptional = parsePageTitle(document);
            blogContent.setTitle(stringOptional.orElse("not has title"));
            String smallDomain = blogContent.getOriginalBlogWebsiteAddress().substring(12);
            parseListLink(document, smallDomain, blogContent.getCategory());
            Elements article = document.getElementsByTag("article");
            if (article.size() == 0) {
                article = document.select("[class~=article*]");
            }
            if (article.size() > 0) {
                String content = article.text();
                blogContent.setContent(content);
                String html = article.html();
                blogContent.setContentOriginal(html);
                for (Element element : article) {
                    Elements imgElements = element.getElementsByTag("img");
                    for (Element imgElement : imgElements) {
                        Set<String> keywords = new HashSet<>();
                        Attributes attributes = imgElement.attributes();
                        for (Attribute attribute : attributes) {
                            String value = attribute.getValue();
                            keywords.add(value);
                        }
                        stringOptional.ifPresent(keywords::add);
                        String src = imgElement.attr("src");
                        BlogImage blogImage = new BlogImage();
                        blogImage.setImageLink(src);
                        Boolean exists = blogImageService.exists(blogImage);
                        if (!exists) {
                            blogImage.setBlogLink(blogContent.getBlogLink());
                            blogImage.setKeywords(keywords);
                            blogImage.setOriginalBlogWebsiteAddress(blogContent.getOriginalBlogWebsiteAddress());
                            blogImage.setCategory(blogContent.getCategory());
                            mongoTemplate.save(blogImage);
                        }
                    }
                }
            }
            blogContent.setHandleStatus(BlogContent.HandleStatus.Completed);
            blogContentSimpleRepository.save(blogContent);
        } catch (Exception e) {
            blogContent.setHandleStatus(BlogContent.HandleStatus.Fail);
            blogContentSimpleRepository.save(blogContent);
            log.info("博客内容解析错误：" + e.getMessage());
        }
    }

    private Optional<String> parsePageTitle(Document s) {
        Elements title = s.getElementsByTag("title");
        List<String> titleList = title.stream().map(Element::text).toList();

        return Optional.of(titleList.size() > 0 ? titleList.get(0) : "not has title");
    }

    private void parseListLink(Document s, String domain, BlogTask.Category category) {
        Elements a = s.getElementsByTag("a");
        for (Element element : a) {
            boolean hrefHas = element.hasAttr("href");
            if (hrefHas) {
                BlogContent blogContent = new BlogContent();
                String href = element.attr("href");
                if (href.contains(domain)) {
                    blogContent.setBlogLink(href);
                } else if (!href.contains("http") && !href.equals("/") && !href.equals("#")) {
                    blogContent.setBlogLink("https://" + domain + href);
                } else {
                    continue;
                }
                Boolean exists = blogContentService.exists(blogContent);
                if (!exists) {
                    blogContent.setOriginalBlogWebsiteAddress("https://www." + domain);
                    blogContent.setHandleStatus(BlogContent.HandleStatus.WaitingProcessing);
                    blogContent.setCategory(category);
                    mongoTemplate.save(blogContent);
                }
            }
        }
    }

    @Override
    public void run() {
        start();
    }
}
