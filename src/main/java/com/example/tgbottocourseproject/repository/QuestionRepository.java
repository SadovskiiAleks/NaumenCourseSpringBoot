package com.example.tgbottocourseproject.repository;

import com.example.tgbottocourseproject.models.GroupQuestion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CrudRepository<GroupQuestion, Long> {
}

