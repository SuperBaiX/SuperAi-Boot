package com.superai.dto;

/**
 * 聊天请求体
 */
public class ChatRequest {
    private String question;
    /** 会话ID */
    private String sessionId;
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
} 