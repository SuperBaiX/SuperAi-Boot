package com.superai.service.impl;

import com.superai.core.llm.LLMService;
import com.superai.core.llm.SpringAiService;
import com.superai.service.ChatService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author : XiaoBaiBai
 * @description : T
 * @created : 2025/6/24
 **/
@Slf4j
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    @Qualifier("springAiService")
    private LLMService llmService;

    @Override
    public String getChat(String question) {
        return llmService.generateResponse(question);
    }
}
