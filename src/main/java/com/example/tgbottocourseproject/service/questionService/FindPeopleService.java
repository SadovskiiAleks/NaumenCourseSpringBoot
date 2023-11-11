package com.example.tgbottocourseproject.service.questionService;

import com.example.tgbottocourseproject.repository.users.UserRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class FindPeopleService {


    private final UserRepository userRepository;
    private final long groupOfQuestion = 2l;

    public FindPeopleService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SendMessage startService(Update update){
        return SendMessage.builder().chatId(update.getMessage().getChatId()).text("Ищу людей").build();
    }
}
