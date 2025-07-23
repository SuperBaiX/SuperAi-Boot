package com.superai.service;

import com.superai.entity.UserChatRecord;
import com.superai.entity.SessionInfo;
import java.util.List;

/**
 * 用户聊天记录与会话管理服务接口
 * 支持多会话、会话标题、聊天记录的存取
 */
public interface UserChatRecordService {
    /**
     * 保存单条聊天记录到Redis
     * @param record 聊天记录对象
     */
    void save(UserChatRecord record);

    /**
     * 查询所有用户的所有聊天记录（未实现，预留）
     * @return 聊天记录列表
     */
    List<UserChatRecord> getAll();

    /**
     * 查询指定用户的所有聊天记录（未实现，预留）
     * @param userId 用户ID
     * @return 聊天记录列表
     */
    List<UserChatRecord> getByUserId(Long userId);

    /**
     * 查询指定用户指定会话的全部聊天记录
     * @param userId 用户ID
     * @param sessionId 会话ID
     * @return 聊天记录列表
     */
    List<UserChatRecord> getByUserIdAndSessionId(Long userId, String sessionId);

    /**
     * 新建会话并保存标题，返回sessionId
     * @param userId 用户ID
     * @param title 会话标题
     * @return 新会话ID
     */
    String createSession(Long userId, String title);

    /**
     * 获取用户所有会话ID列表
     * @param userId 用户ID
     * @return 会话ID列表
     */
    List<String> getSessionIdList(Long userId);

    /**
     * 保存或更新会话标题信息
     * @param sessionInfo 会话信息对象
     */
    void saveOrUpdateSessionInfo(SessionInfo sessionInfo);

    /**
     * 获取用户所有会话信息（含标题、创建时间等）
     * @param userId 用户ID
     * @return 会话信息列表
     */
    List<SessionInfo> getSessionInfoList(Long userId);
} 