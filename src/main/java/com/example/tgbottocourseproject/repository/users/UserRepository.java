package com.example.tgbottocourseproject.repository.users;

import com.example.tgbottocourseproject.models.users.SavingGroupAnswer;
import com.example.tgbottocourseproject.models.users.UserOfTg;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserOfTg, Long> {
    UserOfTg findByUserName(String userName);
    boolean existsByUserName(String userName);


}
