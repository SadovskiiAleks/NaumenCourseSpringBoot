package com.example.tgbottocourseproject.repository.users;

import com.example.tgbottocourseproject.models.users.SavingGroupAnswer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingGroupRepository extends CrudRepository<SavingGroupAnswer, Long> {
}
