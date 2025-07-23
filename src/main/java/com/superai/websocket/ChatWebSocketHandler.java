package com.superai.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.superai.service.ChatService;
import com.superai.service.UserChatRecordService;
import com.superai.entity.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * WebSocket聊天处理器，支持多会话和流式推送
 */
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserChatRecordService userChatRecordService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // 连接建立后可做初始化
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // 解析前端发来的JSON，包含question、sessionId、title（可选）
        Map<String, String> msg = objectMapper.readValue(message.getPayload(), Map.class);
        String question = msg.get("question");
        String sessionId = msg.get("sessionId");
        String title = msg.get("title");
        if (question == null || sessionId == null) {
            session.sendMessage(new TextMessage("{\"error\":\"问题和会话ID不能为空\"}"));
            return;
        }
        // 如果有title，自动保存/更新会话标题
        if (title != null && !title.trim().isEmpty()) {
            SessionInfo info = new SessionInfo();
            info.setSessionId(Long.valueOf(sessionId));
            info.setUserId(1L);
            info.setTitle(title);
            info.setCreateTime(LocalDateTime.now());
            userChatRecordService.saveOrUpdateSessionInfo(info);
        }
        // 生成回复（可改为流式推送）
        String answer = chatService.getChat(question, sessionId);
        // 推送完整回复
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(Map.of(
            "sessionId", sessionId,
            "question", question,
            "answer", answer,
            "timestamp", LocalDateTime.now().toString()
        ))));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        // 处理异常
        try {
            session.sendMessage(new TextMessage("{\"error\":\"WebSocket异常\"}"));
        } catch (IOException ignored) {}
    }
} 