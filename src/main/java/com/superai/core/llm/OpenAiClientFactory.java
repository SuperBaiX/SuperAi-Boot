package com.superai.core.llm;

import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.ai.openai.api.OpenAiApi;

/**
 * @author : XiaoBaiBai
 * @description : T
 * @created : 2025/6/24
 **/
@Component
public class OpenAiClientFactory {


//    public OpenAiChatClient create(String model, String baseUrl, String apiKey) {
//        RestClient restClient = RestClient.builder()
//                .baseUrl(baseUrl)
//                .defaultHeader("Authorization", "Bearer " + apiKey)
//                .build();
//
//        OpenAiApi openAiApi = new OpenAiApi(restClient);
//
//        OpenAiChatOptions options = OpenAiChatOptions.builder()
//                .withModel(model)
//                .withTemperature(0.8f)
//                .withMaxTokens(1000)
//                .build();
//
//        return new OpenAiChatClient(openAiApi, options);
//    }
}
