package com.superai.service.impl;

import com.superai.core.llm.LLMService;
import com.superai.entity.UserChatRecord;
import com.superai.service.ChatService;
import com.superai.service.UserChatRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 聊天服务实现
 * 负责调用大模型服务生成回复，并异步保存聊天记录到Redis
 */
@Slf4j
@Service
public class ChatServiceImpl implements ChatService {

    /** 大模型服务（如SpringAI） */
    @Autowired
    @Qualifier("springAiService")
    private LLMService llmService;

    /** 聊天记录服务 */
    @Autowired
    private UserChatRecordService userChatRecordService;

    /**
     * 获取聊天回复，并异步保存问答内容到Redis
     * @param question 用户问题
     * @param sessionId 会话ID
     * @return 回复内容
     */
    @Override
    public String getChat(String question, String sessionId) {
        if (question == null || question.trim().isEmpty() || sessionId == null) {
            throw new IllegalArgumentException("问题和会话ID不能为空");
        }
        log.info("收到问题: {} (sessionId={})", question, sessionId);
        String answer = llmService.generateResponse(question);
        log.info("返回答案: {}", answer);
        // 异步保存聊天记录（Q: 问题\nA: 回答）
        saveChatRecordAsync(new UserChatRecord(null, 1L, Long.valueOf(sessionId), "Q: " + question + "\nA: " + answer, LocalDateTime.now()));
        return answer;
    }

    /**
     * 异步保存聊天记录到Redis，避免阻塞主流程
     * @param record 聊天记录对象
     */
    @Async
    @Override
    public void saveChatRecordAsync(UserChatRecord record) {
        userChatRecordService.save(record);
    }
}
