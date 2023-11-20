package com.example.tgbottocourseproject.service.messageService;

import com.example.tgbottocourseproject.models.questions.Question;
import com.example.tgbottocourseproject.repository.qustion.GroupQuestionRepository;
import com.example.tgbottocourseproject.repository.users.UserRepository;
import com.example.tgbottocourseproject.utils.InlineQuestionUtils;
import com.example.tgbottocourseproject.utils.KeyboardButtonsUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
public class MessageService {
    private UserRepository userRepository;
    private final GroupQuestionRepository groupQuestionRepository;
    private final InlineQuestionUtils inlineQuestionUtils;
    private final KeyboardButtonsUtils keyboardButtonsUtils;

    public MessageService(UserRepository userRepository, GroupQuestionRepository groupQuestionRepository, InlineQuestionUtils inlineQuestionUtils, KeyboardButtonsUtils keyboardButtonsUtils) {
        this.userRepository = userRepository;
        this.groupQuestionRepository = groupQuestionRepository;
        this.inlineQuestionUtils = inlineQuestionUtils;
        this.keyboardButtonsUtils = keyboardButtonsUtils;
    }

    public SendMessage findAndCreateMassageFromDBbyInline(Update update, Long buttonOnLine, Question question) {
        SendMessage sendMessage = inlineQuestionUtils.generateQuestionWithAnswersbyInline(update, question, buttonOnLine.intValue());
        return sendMessage;
    }

    public SendMessage findAndCreateMassageFromDBbyMessage(Update update, Long buttonOnLine, Question question) {
        SendMessage sendMessage = inlineQuestionUtils
                .generateQuestionWithAnswers(update, question, buttonOnLine.intValue());
        return sendMessage;
    }

    public SendMessage generateKeyboardButton(Update update, String text, List<String> button) {
       return keyboardButtonsUtils.generateKeyboardButton(update, text, button);
    }
}
