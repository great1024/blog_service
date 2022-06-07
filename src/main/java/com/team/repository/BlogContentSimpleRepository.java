package com.team.repository;

import com.team.model.po.BlogContent;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlogContentSimpleRepository extends MongoRepository<BlogContent, ObjectId> {
}
