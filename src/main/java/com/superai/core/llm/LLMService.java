package com.superai.core.llm;

/**
 * @author : XiaoBaiBai
 * @description : T
 * @created : 2025/6/24
 **/
public interface LLMService {

    /**
     * 生成对话
     * @param prompt 提示词
     * @return
     */
    public String generateResponse(String prompt);
}
