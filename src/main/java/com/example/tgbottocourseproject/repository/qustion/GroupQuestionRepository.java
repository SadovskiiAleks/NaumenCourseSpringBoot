package com.example.tgbottocourseproject.repository.qustion;

import com.example.tgbottocourseproject.models.questions.GroupQuestion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupQuestionRepository extends CrudRepository<GroupQuestion, Long> {
}
