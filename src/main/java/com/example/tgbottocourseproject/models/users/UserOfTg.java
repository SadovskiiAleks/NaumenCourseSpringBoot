package com.example.tgbottocourseproject.models.users;

import com.example.tgbottocourseproject.models.questions.Answer;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class UserOfTg {

    @Id
    @GeneratedValue
    Long id;
    Long tgId;
    String firstName;
    String lastName;
    String userName;
    String languageCode;

    @OneToMany(cascade = CascadeType.ALL)
    List<SavingGroupAnswer> savingGroupAnswers;

    Long savingGropeNow;
    Long savingAnswerNow;
}
