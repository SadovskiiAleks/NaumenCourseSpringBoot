package com.example.tgbottocourseproject.models.users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @OneToMany
    List<SavingAnswer> savingAnswers;
}
