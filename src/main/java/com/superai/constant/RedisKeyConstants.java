package com.superai.constant;

/**
 * Redis Key常量池
 */
public class RedisKeyConstants {
    /**
     * 聊天记录Key前缀，格式：chat:user:{userId}:session:{sessionId}
     */
    public static final String CHAT_KEY_PREFIX = "chat:user:";
    /**
     * 会话信息Key前缀，格式：session:user:{userId}:{sessionId}
     */
    public static final String SESSION_INFO_PREFIX = "session:user:";
} 