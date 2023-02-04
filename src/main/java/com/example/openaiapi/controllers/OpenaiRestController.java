package com.example.openaiapi.controllers;

import com.example.openaiapi.dtos.OutputDto;
import com.example.openaiapi.dtos.InputDto;
import com.example.openaiapi.services.Interface.ChatgptService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class OpenaiRestController {
    ChatgptService chatgptService;
    @PostMapping("/sendPrompt")
    public OutputDto sendPrompt(@RequestBody InputDto prompt) throws Exception {
        return chatgptService.sendPrompt("chatgpt",prompt.getPrompt());
    }


}
