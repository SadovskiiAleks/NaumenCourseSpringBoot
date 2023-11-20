package com.example.tgbottocourseproject.service.s0;

import com.example.tgbottocourseproject.repository.qustion.QuestionRepository;
import com.example.tgbottocourseproject.repository.users.UserRepository;
import com.example.tgbottocourseproject.service.UserService;
import com.example.tgbottocourseproject.service.s1.start.StartBotService;
import com.example.tgbottocourseproject.service.s2.menu.FindPeopleService;
import com.example.tgbottocourseproject.service.s2.menu.SurveyService;
import com.example.tgbottocourseproject.service.s3.questionService.DispatcherTextQuestion;
import com.example.tgbottocourseproject.service.s3.questionService.DispatcherQuery;
import com.example.tgbottocourseproject.utils.InlineQuestionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DispatcherService {

    private StartBotService startBotService;
    private InlineQuestionUtils inlineQuestionUtils;
    private QuestionRepository questionRepository;
    private FindPeopleService findPeopleService;
    private SurveyService surveyService;
    private UserService userService;
    private DispatcherTextQuestion dispatcherTextQuestion;
    private DispatcherQuery dispatcherQuery;

    @Autowired
    public DispatcherService(StartBotService startBotService, InlineQuestionUtils inlineQuestionUtils, QuestionRepository questionRepository, FindPeopleService findPeopleService, SurveyService surveyService, UserRepository userRepository, UserService userService, DispatcherTextQuestion dispatcherTextQuestion, DispatcherQuery dispatcherQuery) {
        this.startBotService = startBotService;
        this.inlineQuestionUtils = inlineQuestionUtils;
        this.questionRepository = questionRepository;
        this.findPeopleService = findPeopleService;
        this.surveyService = surveyService;
        this.userService = userService;
        this.dispatcherTextQuestion = dispatcherTextQuestion;
        this.dispatcherQuery = dispatcherQuery;
    }

    public SendMessage addQueryAnswer(Update update) {
        SendMessage sendMessage = dispatcherQuery.dispatchQuery(update);
        return sendMessage;
    }

    public SendMessage addTextAnswer(Update update) {
        switch (update.getMessage().getText()) {
            case ("/start"):
                //Отправить меню
                //записать юзера
                userService.createUser(update);
                return startBotService.createStartKeyboard(update);
            case ("Оставить метаданные"):
                //Начать опрос
                return surveyService.startService(update);
            //*Удалить меню
            case ("Найти людей в команду"):
                //Отправить меню
                return findPeopleService.startService(update);
            //*Удалить меню
            default:
                return dispatcherTextQuestion.createButtonToSaveAnswer(update);
        }
    }
}
