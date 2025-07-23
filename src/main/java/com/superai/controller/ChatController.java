package com.superai.controller;

import com.superai.service.ChatService;
import com.superai.service.UserChatRecordService;
import com.superai.dto.ChatRequest;
import com.superai.common.Result;
import com.superai.entity.UserChatRecord;
import com.superai.entity.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;
    @Autowired
    private UserChatRecordService userChatRecordService;

    /**
     * 新建会话，返回sessionId
     */
    @PostMapping("/session/new")
    public ResponseEntity<Result<String>> createSession(@RequestBody Map<String, String> req) {
        String title = (req != null && req.get("title") != null) ? req.get("title") : "新会话";
        String sessionId = userChatRecordService.createSession(1L, title);
        return ResponseEntity.ok(Result.success(sessionId));
    }

    /**
     * 获取当前用户所有会话信息（含标题）
     */
    @GetMapping("/session/list")
    public ResponseEntity<Result<List<SessionInfo>>> getSessionList() {
        List<SessionInfo> sessionInfos = userChatRecordService.getSessionInfoList(1L);
        return ResponseEntity.ok(Result.success(sessionInfos));
    }

    /**
     * 修改会话标题
     */
    @PostMapping("/session/{sessionId}/title")
    public ResponseEntity<Result<Void>> updateSessionTitle(@PathVariable String sessionId, @RequestBody Map<String, String> req) {
        String title = req.get("title");
        if (title == null || title.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Result.error("标题不能为空"));
        }
        SessionInfo info = new SessionInfo();
        info.setSessionId(Long.valueOf(sessionId));
        info.setUserId(1L);
        info.setTitle(title);
        userChatRecordService.saveOrUpdateSessionInfo(info);
        return ResponseEntity.ok(Result.success(null));
    }

    /**
     * 聊天问答接口（多会话）
     * @param request 用户问题请求体，需包含sessionId
     * @return 答案
     */
    @PostMapping("/ask")
    public ResponseEntity<Result<String>> ask(@RequestBody ChatRequest request) {
        if (request == null || request.getQuestion() == null || request.getQuestion().trim().isEmpty() || request.getSessionId() == null) {
            return ResponseEntity.badRequest().body(Result.error("问题和会话ID不能为空"));
        }
        String answer = chatService.getChat(request.getQuestion(), request.getSessionId());
        return ResponseEntity.ok(Result.success(answer));
    }

    /**
     * 获取指定会话的全部聊天记录
     */
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<Result<List<UserChatRecord>>> getSessionHistory(@PathVariable String sessionId) {
        List<UserChatRecord> records = userChatRecordService.getByUserIdAndSessionId(1L, sessionId);
        return ResponseEntity.ok(Result.success(records));
    }
}
