package com.team.model.po;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("blog_task")
public class BlogTask implements Serializable {
    @Id
    private ObjectId id;
    private String address;
    /**
     * 当前状态
     */
    private Status status;
    /**
     * 类型
     */
    private Category category;
    /**
     * 当前执行次数
     */
    private Integer time;

    @CreatedDate
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime updateTime;

    @Getter
    public enum Category{
        HomeFurnishing("家居"),
        Pet("宠物"),
        Fitness("健身"),
        Gardening("园艺");
        private final String value;

        Category(String value) {
            this.value = value;
        }
    }
    @Getter
    public enum Status{
        Running("运行中"),
        Waiting("等待运行"),
        Ended("已结束");
        private final String value;

        Status(String value) {
            this.value = value;
        }
    }

}
