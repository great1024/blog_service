package com.team.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("blog_content")
public class BlogContent implements Serializable {
    @Id
    private ObjectId id;
    /**
     * 内容
     */
    private String content;
    private String title;
    /**
     * 原始内容
     */
    private String contentOriginal;

    private String originalBlogWebsiteAddress;

    /**
     * 此博客内容所属地址
     */
    private String blogLink;
    /**\
     * 创建时间 此数据不会更新所以不会有更新时间
     */
    private LocalDateTime createdTime;
    /**
     * 执行状态
     */
    private HandleStatus handleStatus;
    /**
     * 内容类型 0 是未知
     */
    private Integer contentType = 0;

    private BlogTask.Category category;


    @Getter
    public enum HandleStatus{
        WaitingProcessing("等待处理"),
        Completed("完成"),
        Fail("失败");
        private final String value;
        HandleStatus(String value) {
            this.value = value;
        }
    }

}
