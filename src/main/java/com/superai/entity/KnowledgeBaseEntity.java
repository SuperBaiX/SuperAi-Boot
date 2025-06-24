package com.superai.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : XiaoBaiBai
 * @description : T
 * @created : 2025/6/24
 **/
@Data
@TableName("knowledge_base")
public class KnowledgeBaseEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("name")
    private String name;

    @TableField("user_id")
    private String userId;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
