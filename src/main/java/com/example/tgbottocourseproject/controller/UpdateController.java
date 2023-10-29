package com.example.tgbottocourseproject.controller;

import com.example.tgbottocourseproject.service.DispatcherService;
import com.example.tgbottocourseproject.utils.MessageUtils;
import com.example.tgbottocourseproject.utils.InlineQuestionUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
@Log4j
public class UpdateController {
    private TelegramBot telegramBot;
    private final DispatcherService dispatcherService;
    private final MessageUtils messageUtils;
    private final InlineQuestionUtils inlineQuestionUtils;

    public UpdateController(DispatcherService dispatcherService, MessageUtils messageUtils, InlineQuestionUtils inlineQuestionUtils) {
        this.dispatcherService = dispatcherService;
        this.messageUtils = messageUtils;
        this.inlineQuestionUtils = inlineQuestionUtils;
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
        } else if (update.getCallbackQuery() != null) {
            distributeCallbackQueryType(update);
        } else {
            log.error("Unsupported message type is received:" + update);
        }
    }

    private void distributeMessageByType(Update update) {
        var message = update.getMessage();
        if (message.getText() != null) {
            setView(dispatcherService.addTextAnswer(update));
        } else {
            setUnsupportedMessageTypeView(update);
        }
    }

    private void distributeCallbackQueryType(Update update) {
        setView(dispatcherService.addQueryAnswer(update));
    }

    private void setUnsupportedMessageTypeView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,
                "Unsupported message type view");
        setView(sendMessage);
    }

    public void setView(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }
}
