package com.superai.service;

import com.superai.entity.UserChatRecord;

/**
 * 聊天服务接口，负责与大模型对接和聊天记录异步保存
 */
public interface ChatService {
    /**
     * 获取聊天回复（多会话）
     * @param question 用户问题
     * @param sessionId 会话ID
     * @return 回复内容
     */
    String getChat(String question, String sessionId);

    /**
     * 异步保存聊天记录到Redis，避免阻塞主流程
     * @param record 聊天记录对象
     */
    void saveChatRecordAsync(UserChatRecord record);
}
