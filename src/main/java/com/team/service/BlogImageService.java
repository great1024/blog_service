package com.team.service;

import com.team.model.po.BlogImage;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class BlogImageService extends BlogBaseService{

    public Boolean exists(BlogImage blogImage) {
        Query query = new Query();
        Example<BlogImage> of = Example.of(blogImage);
        Criteria criteria = Criteria.byExample(of);
        query.addCriteria(criteria);
        return mongoTemplate.exists(query, BlogImage.class);
    }
}
