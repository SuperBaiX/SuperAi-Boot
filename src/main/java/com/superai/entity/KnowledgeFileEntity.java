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
@TableName("knowledge_file")
public class KnowledgeFileEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("knowledge_id")
    private Long knowledgeId;

    @TableField("file_name")
    private String fileName;

    @TableField("file_path")
    private String filePath;

    @TableField("file_size")
    private Long fileSize;

    @TableField("chunk_count")
    private Integer chunkCount;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
