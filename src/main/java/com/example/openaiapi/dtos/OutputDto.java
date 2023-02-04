package com.example.openaiapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class OutputDto {
    private String question;
    private String answer;
}
