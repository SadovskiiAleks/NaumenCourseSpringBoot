package com.example.tgbottocourseproject.service;

import com.example.tgbottocourseproject.repository.QuestionRepository;
import com.example.tgbottocourseproject.service.questionService.FindPeopleService;
import com.example.tgbottocourseproject.service.questionService.SurveyService;
import com.example.tgbottocourseproject.utils.InlineQuestionUtils;
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

    public DispatcherService(StartBotService startBotService, InlineQuestionUtils inlineQuestionUtils, QuestionRepository questionRepository, FindPeopleService findPeopleService, SurveyService surveyService) {
        this.startBotService = startBotService;
        this.inlineQuestionUtils = inlineQuestionUtils;
        this.questionRepository = questionRepository;
        this.findPeopleService = findPeopleService;
        this.surveyService = surveyService;
    }

    public SendMessage addQueryAnswer(Update update) {
        var message = update.getMessage();
        var sendMessage = new SendMessage();

        update.getCallbackQuery().getData().toString();

        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
        sendMessage.setText("Error");
        return sendMessage;
    }

    public SendMessage addTextAnswer(Update update) {
        var message = update.getMessage();
        var sendMessage = new SendMessage();
        //Старт

        switch (update.getMessage().getText()) {
            case ("/start"):
                //Отправить меню
                return startBotService.createStartKeyboard(update);
            case ("Оставить метаданные"):
                //Отправить меню
                return surveyService.startService(update);
            case ("Найти людей в команду"):
                //Отправить меню
                return findPeopleService.startService(update);
        }

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Я еще не могу обрабатывать такие команды :(, если вы потерялись введите /start");
        return sendMessage;
    }

//System.out.println(message.getText());
//            List<String> listOfQ = new ArrayList<>();
//            listOfQ.add("Yes");
//            listOfQ.add("No");
//            listOfQ.add("No");
//            listOfQ.add("No");
//            listOfQ.add("No");
//            setView(questionUtils.generateQuestionWithAnswers(update, "Are your a programmer?", listOfQ, 2));


}
