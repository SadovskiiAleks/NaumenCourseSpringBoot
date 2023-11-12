package com.example.tgbottocourseproject.models.users;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class SavingGroupAnswer {
    @Id
    @GeneratedValue
    Long id;

    @OneToMany(cascade = CascadeType.ALL)
    List<SavingAnswer> savingAnswers;

    String nameOfAnswerGrope;
}
