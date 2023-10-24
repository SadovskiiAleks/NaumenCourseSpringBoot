package com.example.tgbottocourseproject.utils;

import com.example.tgbottocourseproject.DTO.Question;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionUtils {

    //1. Необходимо сделать проверку по количетсву клавишь в строке
    public SendMessage generateQuestionWithAnswers(Update update, Question question, int buttonOnLine) {
        var message = update.getMessage();
        var sendMessage = new SendMessage();
        int keyboardMarkupQuantity = (int) Math.ceil(question.getAnswers().size() / buttonOnLine);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtonList = new ArrayList<>();

        //Уйти от tryCatch
        for (int i = 0; keyboardMarkupQuantity > i; i++) {
            List<InlineKeyboardButton> listOfButtonOnLine = new ArrayList<>();
            for (int j = 0; buttonOnLine > j; j++) {
                try {
                    InlineKeyboardButton button = new InlineKeyboardButton();
                    button.setText(question.getAnswers().get(j + i));
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(question.getNumberOfGroupQuestion())
                            .append(".")
                            .append(question.getNumberOfQuestion())
                            .append(" ")
                            .append(question.getAnswers().get(j + i));
                    button.setCallbackData(stringBuilder.toString());
                    listOfButtonOnLine.add(button);
                } catch (IndexOutOfBoundsException e) {

                }
            }
            inlineKeyboardButtonList.add(listOfButtonOnLine);
        }
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtonList);

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(question.getQuestion());
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    public SendMessage generateQuestionWithoutAnswers(Update update, Question question) {
        var message = update.getMessage();
        var sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(question.getQuestion());
        return sendMessage;
    }
}
