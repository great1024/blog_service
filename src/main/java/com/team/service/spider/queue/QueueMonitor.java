package com.team.service.spider.queue;

import com.team.model.po.BlogContent;
import com.team.service.BlogBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class QueueMonitor extends BlogBaseService implements Runnable {


    public List<BlogContent> getTask() {
        BlogContent blogContent = new BlogContent();
        blogContent.setHandleStatus(BlogContent.HandleStatus.WaitingProcessing);
        Query query = new Query();
        Example<BlogContent> of = Example.of(blogContent);
        Criteria criteria = Criteria.byExample(of);
        query.addCriteria(criteria);
        query.limit(1000);
        return mongoTemplate.find(query, BlogContent.class);
    }

    public void run() {
        while (true) {
            try {
                if (QueueService.Queue.size() < 50) {
                    List<BlogContent> task = this.getTask();
                    for (BlogContent content : task) {
                        if (!QueueService.Queue.contains(content)) {
                            boolean offer = QueueService.Queue.offer(content);
                            if (!offer) {
                                log.debug("队列阻塞：当前长度为" + QueueService.Queue.size());
                                TimeUnit.SECONDS.sleep(100);
                            }
                        }
                    }
                }else {
                    TimeUnit.SECONDS.sleep(100);
                }
            } catch (Exception e) {
                if(e.getMessage().contains("state should be open")){
                    log.info("队列监听程序停止运行！");
                    break;
                }
                log.error("队列监听程序出错：" + e.getMessage());
            }
        }
    }
}
