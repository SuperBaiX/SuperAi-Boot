package com.superai.controller;

import com.superai.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : XiaoBaiBai
 * @description : T
 * @created : 2025/6/24
 **/
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/dh")
    public String getTestChat(String question) {
        return chatService.getChat(question);
    }

}
