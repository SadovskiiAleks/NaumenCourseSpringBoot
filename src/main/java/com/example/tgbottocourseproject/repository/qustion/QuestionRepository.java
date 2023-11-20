package com.example.tgbottocourseproject.repository.qustion;

import com.example.tgbottocourseproject.models.questions.GroupQuestion;
import com.example.tgbottocourseproject.models.questions.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {


}

