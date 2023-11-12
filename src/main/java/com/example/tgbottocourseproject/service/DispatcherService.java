package com.example.tgbottocourseproject.service;

import com.example.tgbottocourseproject.repository.qustion.QuestionRepository;
import com.example.tgbottocourseproject.repository.users.UserRepository;
import com.example.tgbottocourseproject.service.questionService.queryQuestion.DispatcherQuery;
import com.example.tgbottocourseproject.service.questionService.DispatcherTextQuestion;
import com.example.tgbottocourseproject.service.menu.FindPeopleService;
import com.example.tgbottocourseproject.service.menu.SurveyService;
import com.example.tgbottocourseproject.service.start.StartBotService;
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
        var message = update.getMessage();
        var sendMessage = new SendMessage();

        switch (update.getMessage().getText()) {
            case ("/start"):
                //Отправить меню
                return startBotService.createStartKeyboard(update);
            case ("Оставить метаданные"):
                //записать юзера
                userService.createUser(update);
                //Начать опрос
                return surveyService.startService(update);
                //*Удалить меню
            case ("Найти людей в команду"):
                //Отправить меню
                return findPeopleService.startService(update);
            //*Удалить меню
            default:
                dispatcherTextQuestion.createButtonToSaveAnswer(update);
        }

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Я еще не могу обрабатывать такие команды :(, если вы потерялись введите /start");
        return sendMessage;
    }


//    private SendMessage sedUserQuestion(Update update) {
//        // *Проверить номер вопроса который должен получить пользователь
//
//
//        // *Данный вопрос - это текстовый ответ, на который должен прийти запрос подтверждение.
//
//        // *Нужно понять, что за пользователь пишет и узнать в БД на какой запрос ему нужно потдвердение.
//
//        // *Если запросы не заданы то нужно написать "Я еще не могу обрабатывать такие команды :(, если вы потерялись введите /start"
//        update.getMessage().getText();
//    }

//System.out.println(message.getText());
//            List<String> listOfQ = new ArrayList<>();
//            listOfQ.add("Yes");
//            listOfQ.add("No");
//            listOfQ.add("No");
//            listOfQ.add("No");
//            listOfQ.add("No");
//            setView(questionUtils.generateQuestionWithAnswers(update, "Are your a programmer?", listOfQ, 2));


}
