package com.team.service;

import com.team.model.po.BlogContent;
import com.team.repository.BlogContentSimpleRepository;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class BlogContentService extends BlogBaseService {
    @Resource
    private BlogContentSimpleRepository blogContentSimpleRepository;


    public Boolean exists(BlogContent blogContent) {
        Query query = new Query();
        Example<BlogContent> of = Example.of(blogContent);
        Criteria criteria = Criteria.byExample(of);
        query.addCriteria(criteria);
        return mongoTemplate.exists(query, BlogContent.class);
    }

    public Flux<BlogContent> list(String host, Pageable pageable){
        Query query = new Query();
        query.with(pageable);
        return reactiveMongoTemplate.find(query,BlogContent.class);
    }
    public void update(BlogContent blogContents){
//        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, BlogContent.class);
        BlogContent save = blogContentSimpleRepository.save(blogContents);
    }
}
