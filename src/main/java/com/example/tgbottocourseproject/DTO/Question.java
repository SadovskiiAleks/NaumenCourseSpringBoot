package com.example.tgbottocourseproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private int numberOfGroupQuestion;
    private int numberOfQuestion;
    private String question;
    private List<String> answers;

}
