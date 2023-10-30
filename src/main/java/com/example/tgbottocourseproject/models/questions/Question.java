package com.example.tgbottocourseproject.models.questions;

import com.example.tgbottocourseproject.models.questions.Answer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue
    private Long id;
    private int numberQuestion;
    private String nameQuestion;
    private String typeQuestion;
    @OneToMany
    private List<Answer> answer;
}
