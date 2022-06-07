package com.team.controller;

import com.team.model.base.R;
import com.team.model.po.BlogContent;
import com.team.model.po.BlogTask;
import com.team.service.BlogBaseService;
import com.team.service.BlogTaskService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
@CrossOrigin
@RestController
@RequestMapping("task")
public class BlogTaskApi extends BlogBaseService {

    @Resource
    private BlogTaskService blogTaskService;

    @PostMapping("save")
    public Mono<?> save(String address, BlogTask.Category category) {
        BlogTask blogTask = new BlogTask();
        blogTask.setAddress(address);
        Query query = new Query();
        Example<BlogTask> of = Example.of(blogTask);
        Criteria criteria = Criteria.byExample(of);
        query.addCriteria(criteria);
        BlogTask one = mongoTemplate.findOne(query, BlogTask.class);
        if (Objects.nonNull(one)) {
            return Mono.just(R.error("数据重复"));
        }
        blogTask.setTime(0);
        blogTask.setCategory(category);
        blogTask.setStatus(BlogTask.Status.Waiting);
        return blogTaskRepository.insert(blogTask).map(R::successWithData);
    }

    @GetMapping("list")
    public Flux<BlogTask> list() {
        return blogTaskRepository.findAll();
    }

    @GetMapping("list2")
    public Flux<BlogContent> list2() {
        return blogContentRepository.findAll();
    }

    @GetMapping("allStart")
    public Mono<R<?>> allStart() {
        return blogTaskRepository.findAll().flatMap( blogTaskService :: start).then(Mono.just(R.success()));
    }

    @GetMapping("allStartUnexecuted")
    public Mono<R<?>> allStartUnexecuted() {
        BlogTask blogTask1 = new BlogTask();
        blogTask1.setTime(0);
        Example<BlogTask> of = Example.of(blogTask1);
        return blogTaskRepository.findAll(of).flatMap( blogTaskService :: start).then(Mono.just(R.success()));
    }

    @GetMapping(value = "pull", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<LocalDateTime> pull() {
        return Flux.fromStream(IntStream.range(1, 10).mapToObj(i -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return LocalDateTime.now();
        }));
    }
}
