package com.example.tgbottocourseproject.models.users;

import com.example.tgbottocourseproject.models.questions.Answer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    Long id;
    Long tgId;
    String firstName;
    String lastName;
    String userName;
    String languageCode;

    @OneToMany
    List<SavingGroupAnswer> savingGroupAnswers;

    Long savingGropeNow;
    Long savingAnswerNow;
}
