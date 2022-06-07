package com.team.service;

import com.team.model.po.BlogContent;
import com.team.model.po.BlogTask;
import com.team.repository.BlogTaskRepository;
import com.team.service.spider.queue.QueueService;
import jakarta.annotation.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class BlogTaskService {

    @Resource
    private BlogTaskRepository blogTaskRepository;
    @Resource
    private MongoTemplate mongoTemplate;
    public void getTask(){
        List<BlogTask> blogTasks = mongoTemplate.findAll(BlogTask.class);
        Optional<BlogTask> blogTask = blogTasks.stream().min(Comparator.comparingInt(BlogTask::getTime));
        if(blogTask.isPresent()){
            BlogTask blogTask1 = blogTask.get();
            Integer time = blogTask1.getTime();
        }
    }
    public void save(BlogTask blogTask){

    }

    public Mono<?> start(BlogTask bt){
        BlogContent blogContent = new BlogContent();
        blogContent.setBlogLink(bt.getAddress());
        blogContent.setOriginalBlogWebsiteAddress(bt.getAddress());
        blogContent.setCategory(bt.getCategory());
        QueueService.Queue.offer(blogContent);
        bt.setTime(bt.getTime()+1);
        return blogTaskRepository.save(bt);
    }
}
