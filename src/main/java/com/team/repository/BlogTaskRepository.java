package com.team.repository;

import com.team.model.po.BlogTask;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Service;

@Service
public interface BlogTaskRepository extends ReactiveMongoRepository<BlogTask, ObjectId> {
}
