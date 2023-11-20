package com.example.tgbottocourseproject.models.users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class SavingAnswer {
    @Id
    @GeneratedValue
    Long id;

    String question;

    String answer;

}
