package com.example.tgbottocourseproject.service.questionService;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class FindPeopleService {

    public SendMessage startService(Update update){
        return SendMessage.builder().chatId(update.getMessage().getChatId()).text("Ищу людей").build();
    }
}
