package com.superai.core.llm;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * @author : XiaoBaiBai
 * @description : T
 * @created : 2025/6/24
 **/
@Slf4j
@Service
public class OpenAIService implements LLMService{

    @Value("${openai.api-key}")
    private String apiKey;


    @Override
    public String generateResponse(String prompt) {
        //构建OpenAi服务传入参数token超时时间
        OpenAiService service = new OpenAiService(apiKey, Duration.ofSeconds(60));
        CompletionRequest request = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt(prompt)
                .temperature(0.7)
                .maxTokens(500)
                .build();
        //TODO 网络通信失败 后面研究
        return service.createCompletion(request)
                .getChoices()
                .getFirst()
                .getText()
                .trim();
    }
}
