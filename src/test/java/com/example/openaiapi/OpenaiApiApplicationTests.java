package com.example.openaiapi;

import com.example.openaiapi.dtos.OutputDto;
import com.example.openaiapi.services.ChatgptServiceImpl;
import com.example.openaiapi.services.Interface.ChatgptService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

class OpenaiApiApplicationTests {

	//@Test
	void itshouldAddtheQA() throws Exception {
		ObjectMapper objectMapper=new ObjectMapper();
		ChatgptService chatgptService= new ChatgptServiceImpl(objectMapper);
		OutputDto out =chatgptService.sendPrompt("chatgpt","What is jenkins?");
		System.out.println(out.getQuestion());
		System.out.println(out.getAnswer());
	}

}
