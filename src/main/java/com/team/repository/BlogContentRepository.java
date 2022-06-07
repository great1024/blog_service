package com.team.repository;

import com.team.model.po.BlogContent;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BlogContentRepository extends ReactiveMongoRepository<BlogContent, ObjectId> {
}
