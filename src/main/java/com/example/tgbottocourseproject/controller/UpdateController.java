package com.example.tgbottocourseproject.controller;

import com.example.tgbottocourseproject.utils.MessageUtils;
import com.example.tgbottocourseproject.utils.QuestionUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
@Log4j
public class UpdateController {
    private TelegramBot telegramBot;
    private final QuestionsListController questionsListController;
    private final MessageUtils messageUtils;
    private final QuestionUtils questionUtils;

    public UpdateController(QuestionsListController questionsListController, MessageUtils messageUtils, QuestionUtils questionUtils) {
        this.questionsListController = questionsListController;
        this.messageUtils = messageUtils;
        this.questionUtils = questionUtils;
    }

    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Received update is null");
        }
        if (update.getMessage() != null) {
            distributeMessageByType(update);
        } if (update.getCallbackQuery() != null) {
            distributeCallbackQueryType(update);
        } else {
            log.error("Unsupported message type is received:" + update);
        }
    }

    private void distributeCallbackQueryType(Update update) {
        questionsListController.addQueryAnswer(update);
    }

    private void distributeMessageByType(Update update) {
        var message = update.getMessage();
        if (message.getText() != null) {
            questionsListController.addTextAnswer(update);
        } else if (message.getDocument() != null) {

        } else if (message.getPhoto() != null) {

        }  else {
            setUnsupportedMessageTypeView(update);
        }
    }


    private void setUnsupportedMessageTypeView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,
                "Unsupported message type view");
        setView(sendMessage);
    }

    public void setView(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }

    private void setFileIsReceivedView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,
                "file uploading...");
        setView(sendMessage);
    }
}
