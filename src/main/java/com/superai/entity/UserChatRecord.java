package com.superai.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 用户聊天记录实体
 * 用于存储单条用户的问答内容及时间戳
 */
@Data
public class UserChatRecord {
    /**
     * 主键ID（可选）
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 用户ID（本项目写死为1）
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    /**
     * 会话ID，用于区分多会话
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long sessionId;
    /**
     * 聊天内容（如：Q: 问题\nA: 回答）
     */
    private String content;
    /**
     * 聊天时间戳
     */
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private LocalDateTime timestamp;


    public UserChatRecord() {
    }

    /**
     * 全参构造方法
     */
    public UserChatRecord(Long id, Long userId, Long sessionId, String content, LocalDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.sessionId = sessionId;
        this.content = content;
        this.timestamp = timestamp;
    }
} 