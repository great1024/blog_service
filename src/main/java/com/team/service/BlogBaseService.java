package com.team.service;

import com.team.repository.BlogContentRepository;
import com.team.repository.BlogTaskRepository;
import jakarta.annotation.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class BlogBaseService {
    @Resource
    protected BlogTaskService blogTaskService;
    @Resource
    protected MongoTemplate mongoTemplate;
    @Resource
    protected BlogTaskRepository blogTaskRepository;
    @Resource
    protected BlogContentRepository blogContentRepository;
    @Resource
    protected ReactiveMongoTemplate reactiveMongoTemplate;
}
