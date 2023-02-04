package com.example.openaiapi;

import com.example.openaiapi.dtos.OutputDto;
import com.example.openaiapi.entities.Answer;
import com.example.openaiapi.services.Interface.ChatgptService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class OpenaiApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenaiApiApplication.class, args);
	}
	//@Bean
	CommandLineRunner start(ChatgptService chatgptService)
	{
		return args -> {
			OutputDto out =chatgptService.sendPrompt("chatgpt","What is gluten sensitivity?");
			System.out.println(out.getQuestion());
			System.out.println(out.getAnswer());

			OutputDto out2=chatgptService.sendPrompt("chatgpt","What is jenkins?");
			System.out.println(out2.getQuestion());
			System.out.println(out2.getAnswer());

		};
	}
}
