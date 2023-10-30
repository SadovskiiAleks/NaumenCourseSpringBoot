package com.example.tgbottocourseproject.models.questions;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Answer {
    @Id
    @GeneratedValue
    private Long id;
    private String nameAnswer;

}
