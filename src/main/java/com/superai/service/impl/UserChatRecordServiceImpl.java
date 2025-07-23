package com.superai.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.superai.constant.RedisKeyConstants;
import com.superai.entity.UserChatRecord;
import com.superai.entity.SessionInfo;
import com.superai.service.UserChatRecordService;
import com.superai.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户聊天记录与会话管理服务实现
 * 聊天记录和会话信息全部存储于Redis，支持多会话、会话标题、过期自动清理
 */
@Service
public class UserChatRecordServiceImpl implements UserChatRecordService {
    /**
     * 聊天记录Key前缀，格式：chat:user:{userId}:session:{sessionId}
     */
    private static final String CHAT_KEY_PREFIX = RedisKeyConstants.CHAT_KEY_PREFIX;
    /**
     * 会话信息Key前缀，格式：session:user:{userId}:{sessionId}
     */
    private static final String SESSION_INFO_PREFIX = RedisKeyConstants.SESSION_INFO_PREFIX;
    /**
     * 聊天记录和会话信息在Redis中的过期天数
     */
    private static final long EXPIRE_DAYS = 30;

    /**
     * Redis操作工具类
     */
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 保存单条聊天记录到Redis指定会话列表
     *
     * @param record 聊天记录对象
     */
    @Override
    public void save(UserChatRecord record) {
        String key = getSessionKey(record.getUserId(), String.valueOf(record.getSessionId()));
        redisUtil.rightPush(key, record, EXPIRE_DAYS);
    }

    /**
     * 查询所有用户的所有聊天记录（未实现，预留）
     *
     * @return 聊天记录列表
     */
    @Override
    public List<UserChatRecord> getAll() {
        // 仅演示，实际应遍历所有用户所有会话key
        return new ArrayList<>();
    }

    /**
     * 查询指定用户的所有聊天记录（未实现，预留）
     *
     * @param userId 用户ID
     * @return 聊天记录列表
     */
    @Override
    public List<UserChatRecord> getByUserId(Long userId) {
        // 仅演示，实际应遍历所有sessionId
        return new ArrayList<>();
    }

    /**
     * 查询指定用户指定会话的全部聊天记录
     *
     * @param userId    用户ID
     * @param sessionId 会话ID
     * @return 聊天记录列表
     */
    @Override
    public List<UserChatRecord> getByUserIdAndSessionId(Long userId, String sessionId) {
        String key = getSessionKey(userId, sessionId);
        List<UserChatRecord> list = redisUtil.range(key, 0, -1, UserChatRecord.class);
        if (list == null) return new ArrayList<>();
        return list;
    }

    /**
     * 新建会话并保存标题信息，返回新会话ID
     *
     * @param userId 用户ID
     * @param title  会话标题
     * @return 新会话ID
     */
    @Override
    public String createSession(Long userId, String title) {
        Long sessionId = IdWorker.getId();
        SessionInfo info = new SessionInfo();
        info.setSessionId(sessionId);
        info.setUserId(userId);
        info.setTitle(title);
        info.setCreateTime(LocalDateTime.now());
        saveOrUpdateSessionInfo(info);
        return sessionId.toString();
    }

    /**
     * 获取用户所有会话ID列表
     *
     * @param userId 用户ID
     * @return 会话ID列表
     */
    @Override
    public List<String> getSessionIdList(Long userId) {
        String pattern = CHAT_KEY_PREFIX + userId + ":session:*";
        Set<String> keys = redisUtil.keys(pattern);
        if (keys == null) return Collections.emptyList();
        // 提取sessionId
        return keys.stream()
                .map(k -> k.substring((CHAT_KEY_PREFIX + userId + ":session:").length()))
                .collect(Collectors.toList());
    }

    /**
     * 保存或更新会话标题信息到Redis
     *
     * @param sessionInfo 会话信息对象
     */
    @Override
    public void saveOrUpdateSessionInfo(SessionInfo sessionInfo) {
        String key = getSessionInfoKey(sessionInfo.getUserId(), String.valueOf(sessionInfo.getSessionId()));
        redisUtil.set(key, sessionInfo, EXPIRE_DAYS);
    }

    /**
     * 获取用户所有会话信息（含标题、创建时间等）
     *
     * @param userId 用户ID
     * @return 会话信息列表
     */
    @Override
    public List<SessionInfo> getSessionInfoList(Long userId) {
        String pattern = SESSION_INFO_PREFIX + userId + ":*";
        Set<String> keys = redisUtil.keys(pattern);
        if (keys == null) return Collections.emptyList();
        List<SessionInfo> result = new ArrayList<>();
        for (String key : keys) {
            SessionInfo info = redisUtil.get(key, SessionInfo.class);
            if (info != null) {
                result.add(info);
            }
        }
        return result;
    }

    /**
     * 生成Redis中聊天记录的Key
     *
     * @param userId    用户ID
     * @param sessionId 会话ID
     * @return Redis Key
     */
    private String getSessionKey(Long userId, String sessionId) {
        return CHAT_KEY_PREFIX + userId + ":session:" + sessionId;
    }

    /**
     * 生成Redis中会话信息的Key
     *
     * @param userId    用户ID
     * @param sessionId 会话ID
     * @return Redis Key
     */
    private String getSessionInfoKey(Long userId, String sessionId) {
        return SESSION_INFO_PREFIX + userId + ":" + sessionId;
    }
} 