package com.example.openaiapi.services;

import com.example.openaiapi.dtos.OutputDto;
import com.example.openaiapi.entities.Answer;
import com.example.openaiapi.entities.Call;
import com.example.openaiapi.services.Interface.ChatgptService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import static org.apache.commons.lang3.ArrayUtils.toStringArray;


@Slf4j
@Service

public class ChatgptServiceImpl implements ChatgptService {
    @Value("${openai.apikey}")
    private String openaiApiKey;
    private ObjectMapper jsonMapper;
    @Value("${url}")
    private String URL;
    @Value("${openai.model}")
    private String model;
    @Value("${openai.maxTokens}")
    private Integer max_tokens;
    @Value("${openai.temperature}")
    private Double temperature;
    private final HttpClient client = HttpClient.newHttpClient();
    public ChatgptServiceImpl(ObjectMapper jsonMapper){
        this.jsonMapper=jsonMapper;
    }

    @Override
    public String sendChatgptRequest(String body) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + openaiApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(body)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    @Override
    public void writeToCSV(String filePath, OutputDto outputDto)
    {
        File file = new File(filePath);
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(file, true));

            String[] data = {outputDto.getQuestion(),outputDto.getAnswer()};
            writer.writeNext(data);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public OutputDto sendPrompt(String filePath,String prompt) throws Exception {
        Call call = new Call(model,prompt,max_tokens,temperature);
        String responseBody = sendChatgptRequest(jsonMapper.writeValueAsString(call));
        Answer answer = jsonMapper.readValue(responseBody, Answer.class);
        String text= Optional.of(answer.getChoices().get(0).getText()).orElseThrow();
        OutputDto outputDto= new OutputDto(prompt,text);
        writeToCSV(filePath,outputDto);
        return outputDto;
    }



}
