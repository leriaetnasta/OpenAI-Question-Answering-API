package com.example.openaiapi.services.Interface;

import com.example.openaiapi.dtos.OutputDto;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ChatgptService {

    String sendChatgptRequest(String body) throws IOException, InterruptedException;

    void writeToCSV(String filePath, OutputDto outputDto);

    OutputDto sendPrompt(String filePath, String prompt) throws Exception;
}
