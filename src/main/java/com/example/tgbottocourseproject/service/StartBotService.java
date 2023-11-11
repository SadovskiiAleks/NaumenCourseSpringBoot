package com.example.tgbottocourseproject.service;

import com.example.tgbottocourseproject.utils.KeyboardButtonsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;


@Component
public class StartBotService {

    private final KeyboardButtonsUtils keyboardButtonsUtils;


    @Autowired
    public StartBotService(KeyboardButtonsUtils keyboardButtonsUtils) {
        this.keyboardButtonsUtils = keyboardButtonsUtils;
    }

    public SendMessage createStartKeyboard(Update update) {
        String text = "Добро пожаловать к боту по поиску партнеров для участия в проектах.\n" +
                "Подскажите вы хотите оставить резюме или найти партнера ?";
        List<String> button = new ArrayList<>();
        button.add("Оставить метаданные");
        button.add("Найти людей в команду");
        return keyboardButtonsUtils.generateKeyboardButton(update, text, button);
    }
}
