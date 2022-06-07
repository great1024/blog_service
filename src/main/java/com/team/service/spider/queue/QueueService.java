package com.team.service.spider.queue;

import com.team.model.po.BlogContent;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;

@Service
public class QueueService {
    public static ArrayBlockingQueue<BlogContent> Queue = new ArrayBlockingQueue<>(1000);
}
