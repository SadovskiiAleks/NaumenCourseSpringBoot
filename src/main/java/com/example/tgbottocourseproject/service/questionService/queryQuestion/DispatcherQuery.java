package com.example.tgbottocourseproject.service.questionService.queryQuestion;

import com.example.tgbottocourseproject.models.questions.Answer;
import com.example.tgbottocourseproject.models.users.SavingAnswer;
import com.example.tgbottocourseproject.models.users.SavingGroupAnswer;
import com.example.tgbottocourseproject.models.users.UserOfTg;
import com.example.tgbottocourseproject.repository.qustion.GroupQuestionRepository;
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
    public DispatcherQuery(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public SendMessage dispatchQuery(Update update) {
        //var message = update.getMessage();
        var sendMessage = new SendMessage();

        update.getCallbackQuery().getData().toString();
        // Записать ответ по запросу указонному у пользывателя в открытых
        saveAnswer(update);

        // *Удалить выбор вопроса

        // Отправить новый запрос
        sendMessage = messageService.findAndCreateMassageFromDB(update, buttonOnLine);

        return sendMessage;
    }

    @Transactional
    public boolean saveAnswer(Update update) {
        // *получить юзера
        UserOfTg userOfTg = userRepository.findByUserName(update.getCallbackQuery().getFrom().getUserName());

        Long savingGropeNow = userOfTg.getSavingGropeNow();
        Long savingAnswerNow = userOfTg.getSavingAnswerNow();
        // *записать данные по юзеру

        if (userOfTg.getSavingGroupAnswers().isEmpty()) {
            userOfTg.setSavingGroupAnswers(generateAnswerListInUser(savingGropeNow));
        }
//        else if () {
//            //*Если список ответов есть, нужно проверить не записаны ли ответы если его нет то записать
//        } else {
//            //*Если ответовы есть то перезаписываем
//        }

        List<SavingGroupAnswer> savingGroupAnswerList = userOfTg.getSavingGroupAnswers();
        List<SavingAnswer> savingAnswerList = savingGroupAnswerList.get(savingGropeNow.intValue()).getSavingAnswers();

//        if (savingAnswerList.get(savingAnswerNow.intValue()) != null) {
//            SavingAnswer savingAnswer = savingAnswerList.get(savingAnswerNow);
//            savingAnswer.setAnswer(update.getCallbackQuery().getData());
//        } else {
            SavingAnswer savingAnswer = new SavingAnswer();
            //Long l = new Long(savingAnswerNow);
            savingAnswer.setId(Long.valueOf(savingAnswerNow));
            savingAnswer.setAnswer(update.getCallbackQuery().getData());
            savingAnswerList.add(savingAnswerNow.intValue(), savingAnswer);
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


    private List<SavingGroupAnswer> generateAnswerListInUser(Long savingGropeNow) {
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
        return savingGroupAnswerList;
    }
}
