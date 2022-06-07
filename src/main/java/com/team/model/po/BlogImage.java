package com.team.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Set;

/**
 * 每个图片链接存一条数据，根据链接去重
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("blog_image")
public class BlogImage implements Serializable {
    @Id
    private ObjectId id;

    private String originalBlogWebsiteAddress;

    private String blogLink;

    private String imageLink;

    private Set<String> keywords;

    private BlogTask.Category category;
}
