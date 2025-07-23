package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import com.superai.service.UserChatRecordService;

@SpringBootApplication
public class SuperAiBootApplication {

    @Autowired
    private UserChatRecordService userChatRecordService;

    public static void main(String[] args) {
        SpringApplication.run(SuperAiBootApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void syncChatHistoryToRedis() {
        // TODO: 从数据库加载用户聊天记录并写入Redis
        // userChatRecordService.save(...)
    }
} 