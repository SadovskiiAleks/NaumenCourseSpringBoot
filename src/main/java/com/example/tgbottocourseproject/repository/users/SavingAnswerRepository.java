package com.example.tgbottocourseproject.repository.users;

import com.example.tgbottocourseproject.models.users.SavingAnswer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingAnswerRepository extends CrudRepository<SavingAnswer, Long> {
}
