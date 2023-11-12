package com.example.tgbottocourseproject.service.questionService.queryQuestion;

import com.example.tgbottocourseproject.models.users.SavingAnswer;
import com.example.tgbottocourseproject.models.users.SavingGroupAnswer;
import com.example.tgbottocourseproject.models.users.UserOfTg;
import com.example.tgbottocourseproject.repository.qustion.GroupQuestionRepository;
import com.example.tgbottocourseproject.repository.qustion.QuestionRepository;
import com.example.tgbottocourseproject.repository.users.UserRepository;
import com.example.tgbottocourseproject.service.messageService.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Service
public class DispatcherQuery {
    private UserRepository userRepository;
    private MessageService messageService;
    private GroupQuestionRepository groupQuestionRepository;
    Long buttonOnLine = 3l;

    @Autowired
    public DispatcherQuery(UserRepository userRepository, MessageService messageService, GroupQuestionRepository groupQuestionRepository) {
        this.userRepository = userRepository;
        this.messageService = messageService;
        this.groupQuestionRepository = groupQuestionRepository;
    }

    @Transactional
    public SendMessage dispatchQuery(Update update) {
        //var message = update.getMessage();
        var sendMessage = new SendMessage();

        update.getCallbackQuery().getData().toString();
        // Записать ответ по запросу указонному у пользывателя в открытых
        saveAnswer(update);

        // *Удалить выбор вопроса

        UserOfTg userOfTg = userRepository.findByUserName(update.getCallbackQuery().getFrom().getUserName());
        Long numberOfGroupAnswer = userOfTg.getSavingGropeNow();
        Long numberOfAnswer = userOfTg.getSavingAnswerNow();

        int numberOfSafeQuestion = groupQuestionRepository.findById(numberOfGroupAnswer).get().getQuestionList().size();
        // Проверить номер вопроса
        if (numberOfSafeQuestion >= numberOfAnswer.intValue()) {
            // Отправить новый запрос
            sendMessage = messageService.findAndCreateMassageFromDBbyInline(update, buttonOnLine);
            return sendMessage;
        } else {
            System.out.println("Возврат ответа");
        }

        return sendMessage;
    }

    @Transactional
    public boolean saveAnswer(Update update) {
        // *получить юзера
        UserOfTg userOfTg = userRepository.findByUserName(update.getCallbackQuery().getFrom().getUserName());

        Long savingGropeNow = userOfTg.getSavingGropeNow();
        Long savingAnswerNow = userOfTg.getSavingAnswerNow();
        // *записать данные по юзеру

        if (userOfTg.getSavingGroupAnswers().size()==0) {
            userOfTg.setSavingGroupAnswers(generateAnswerListInUser(savingGropeNow));
        }
//        else if () {
//            //*Если список ответов есть, нужно проверить не записаны ли ответы если его нет то записать
//        } else {
//            //*Если ответовы есть то перезаписываем
//        }

        List<SavingGroupAnswer> savingGroupAnswerList = userOfTg.getSavingGroupAnswers();
        List<SavingAnswer> savingAnswerList = savingGroupAnswerList.get(savingGropeNow.intValue()-1).getSavingAnswers();

//        if (savingAnswerList.get(savingAnswerNow.intValue()) != null) {
//            SavingAnswer savingAnswer = savingAnswerList.get(savingAnswerNow);
//            savingAnswer.setAnswer(update.getCallbackQuery().getData());
//        } else {
            SavingAnswer savingAnswer = new SavingAnswer();
            //Long l = new Long(savingAnswerNow);
            savingAnswer.setId(Long.valueOf(savingAnswerNow));
            savingAnswer.setAnswer(update.getCallbackQuery().getData());
            savingAnswerList.add(savingAnswerNow.intValue()-1, savingAnswer);
//        }
        // Добавить 1 к номеру проверяемого запроса
        userOfTg.setSavingAnswerNow(Long.valueOf(savingAnswerNow + 1));
        userRepository.save(userOfTg);
        return false;
    }

//    private boolean generateListAnswer(UserOfTg userOfTg) {
//
//        return false;
//    }

    @Transactional
    protected List<SavingGroupAnswer> generateAnswerListInUser(Long savingGropeNow) {
        int sizeListQuestions = groupQuestionRepository.findById(savingGropeNow).get().getQuestionList().size();
        String nameOfQuestionGroup = groupQuestionRepository.findById(savingGropeNow).get().getGroupName();
        //создать ветку для данных вопросов
        List<SavingGroupAnswer> savingGroupAnswerList = new ArrayList<>();
        SavingGroupAnswer savingGroupAnswer = new SavingGroupAnswer();
        savingGroupAnswer.setNameOfAnswerGrope(nameOfQuestionGroup);
        savingGroupAnswer.setId(savingGropeNow);

        List<SavingAnswer> savingAnswerList = new ArrayList<>();
        for (int i = 0; i < sizeListQuestions; i++) {
            SavingAnswer savingAnswer = new SavingAnswer();
            savingAnswerList.add(savingAnswer);
        }
        savingGroupAnswer.setSavingAnswers(savingAnswerList);
        savingGroupAnswerList.add(savingGroupAnswer);
        return savingGroupAnswerList;
    }
}
