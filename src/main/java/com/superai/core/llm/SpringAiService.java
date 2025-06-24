package com.superai.core.llm;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ai.chat.prompt.Prompt;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : XiaoBaiBai
 * @description : T
 * @created : 2025/6/24
 **/
@Component
public class SpringAiService implements LLMService{

    @Autowired
    private OpenAiChatClient openAiChatClient;


    // 模拟对话上下文（可从数据库进行查询）每个实例维护一个上下文（实际中应该用用户 ID 管理）
    private static final List<Message> messageHistory  = new ArrayList<>();

    @Override
    public String generateResponse(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "请输入有效的问题。";
        }
        // 添加用户消息到上下文
        messageHistory.add(new UserMessage(input));

        // 构造 Prompt 请求
        Prompt prompt = new Prompt(messageHistory);

        // 调用模型获取响应
        ChatResponse chatResponse = openAiChatClient.call(prompt);

        // 提取响应内容
        String response = chatResponse.getResult().getOutput().getContent();

        // 添加 AI 回复到上下文
        messageHistory.add(new AssistantMessage(response));

        return response;
    }
}
