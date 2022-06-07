package com.team.service;

import com.team.service.spider.BlogSpiderService;
import com.team.service.spider.queue.QueueMonitor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StartService implements ApplicationRunner {
    @Resource
    private BlogSpiderService blogSpiderService;
    @Resource
    private QueueMonitor queueMonitor;
    /**
     * 启动时运行
     * @param args incoming application arguments
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("启动队列监听服务");
        new Thread(queueMonitor).start();
        log.info("启动队列监听服务启动完成");
        log.info("启动博客爬虫服务");
        new Thread(blogSpiderService).start();
        log.info("启动博客爬虫服务启动完成");
    }
}
