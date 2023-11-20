package com.example.tgbottocourseproject.service.s1.start;

import com.example.tgbottocourseproject.service.messageService.MessageService;
import com.example.tgbottocourseproject.utils.KeyboardButtonsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;


@Component
public class StartBotService {

    private final MessageService messageService;

    @Autowired
    public StartBotService(MessageService messageService) {
        this.messageService = messageService;
    }

    public SendMessage createStartKeyboard(Update update) {
        String text = "Добро пожаловать к боту по поиску партнеров для участия в проектах.\n" +
                "Подскажите вы хотите оставить резюме или найти партнера ?";
        List<String> button = new ArrayList<>();
        button.add("Оставить метаданные");
        button.add("Найти людей в команду");
        return messageService.generateKeyboardButton(update, text, button);
    }
}
