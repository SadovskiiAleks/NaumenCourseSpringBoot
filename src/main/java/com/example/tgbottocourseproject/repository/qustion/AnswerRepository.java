package com.example.tgbottocourseproject.repository.qustion;

import com.example.tgbottocourseproject.models.questions.Answer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Long> {

}
