package com.example.tgbottocourseproject.models;

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
public class GroupQuestion {
    @Id
    @GeneratedValue
    private Long id;

    String groupName;

    @OneToMany
    private List<Question> questionList;
}
