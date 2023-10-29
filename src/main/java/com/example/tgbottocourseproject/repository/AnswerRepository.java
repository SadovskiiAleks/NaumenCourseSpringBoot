package com.example.tgbottocourseproject.repository;

import com.example.tgbottocourseproject.models.Answer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Long> {

}
