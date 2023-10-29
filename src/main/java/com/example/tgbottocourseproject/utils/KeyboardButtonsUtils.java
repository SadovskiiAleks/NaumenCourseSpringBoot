package com.example.tgbottocourseproject.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeyboardButtonsUtils {


    public SendMessage generateKeyboardButton(Update update, String text, List<String> button) {
        var message = update.getMessage();
        var sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());

        KeyboardRow row1 = new KeyboardRow();
        for (String s : button) {
            row1.add(new KeyboardButton(s));
        }
//
//        KeyboardRow row2 = new KeyboardRow();
//        row1.add(new KeyboardButton("test 1"));
//        row1.add(new KeyboardButton("test 2"));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);
//        keyboard.add(row2);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }
}
