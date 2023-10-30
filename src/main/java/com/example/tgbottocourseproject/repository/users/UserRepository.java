package com.example.tgbottocourseproject.repository.users;

import com.example.tgbottocourseproject.models.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
