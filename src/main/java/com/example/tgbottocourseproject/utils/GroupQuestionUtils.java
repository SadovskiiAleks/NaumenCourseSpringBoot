package com.example.tgbottocourseproject.utils;

import com.example.tgbottocourseproject.models.GroupQuestion;
import com.example.tgbottocourseproject.models.Question;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class GroupQuestionUtils {
    public SendMessage generateQuestionWithAnswers(Update update, GroupQuestion groupQuestion, int buttonOnLine, String startName) {
        var message = update.getMessage();
        var sendMessage = new SendMessage();
        int keyboardMarkupQuantity = (int) Math.ceil(groupQuestion.getQuestionList().size() / buttonOnLine);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtonList = new ArrayList<>();

        //Уйти от tryCatch
        for (int i = 0; keyboardMarkupQuantity > i; i++) {
            List<InlineKeyboardButton> listOfButtonOnLine = new ArrayList<>();
            for (int j = 0; buttonOnLine > j; j++) {
                try {
                    InlineKeyboardButton button = new InlineKeyboardButton();
                    button.setText(groupQuestion.getQuestionList().get(j + i).getNameQuestion());
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder
//                      .append(question.getNumberOfGroupQuestion())
//                            .append(".")
//                            .append(question.getNumberOfQuestion())
//                            .append(" ")
                            .append(groupQuestion.getQuestionList().get(j + i).getNameQuestion());
                    button.setCallbackData(stringBuilder.toString());
                    listOfButtonOnLine.add(button);
                } catch (IndexOutOfBoundsException e) {

                }
            }
            inlineKeyboardButtonList.add(listOfButtonOnLine);
        }
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtonList);

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(startName);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }
}
