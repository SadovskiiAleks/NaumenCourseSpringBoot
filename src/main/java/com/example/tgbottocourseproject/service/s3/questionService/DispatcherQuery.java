package com.example.tgbottocourseproject.service.s3.questionService;

import com.example.tgbottocourseproject.models.questions.Question;
import com.example.tgbottocourseproject.models.users.SavingAnswer;
import com.example.tgbottocourseproject.models.users.SavingGroupAnswer;
import com.example.tgbottocourseproject.models.users.UserOfTg;
import com.example.tgbottocourseproject.repository.qustion.GroupQuestionRepository;
import com.example.tgbottocourseproject.repository.users.UserRepository;
import com.example.tgbottocourseproject.service.messageService.MessageService;
import com.example.tgbottocourseproject.utils.MessageUtils;
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
    private MessageUtils messageUtils;
    private GroupQuestionRepository groupQuestionRepository;
    Long buttonOnLine = 3l;

    @Autowired
    public DispatcherQuery(UserRepository userRepository, MessageService messageService, MessageUtils messageUtils, GroupQuestionRepository groupQuestionRepository) {
        this.userRepository = userRepository;
        this.messageService = messageService;
        this.messageUtils = messageUtils;
        this.groupQuestionRepository = groupQuestionRepository;
    }

    @Transactional
    public SendMessage dispatchQuery(Update update) {
        var sendMessage = new SendMessage();

        // Записать ответ по запросу указонному у пользывателя в открытых
        UserOfTg userOfTg = userRepository.findByUserName(update.getCallbackQuery().getFrom().getUserName());
        Long numberOfGroupAnswer = userOfTg.getSavingGropeNow();
        Long numberOfAnswer = userOfTg.getSavingAnswerNow();

        saveAnswer(update, userOfTg, numberOfGroupAnswer, numberOfAnswer);

        List<Question> questions = groupQuestionRepository.findById(numberOfGroupAnswer).get().getQuestionList();
        Question question = findQuestion(questions, numberOfAnswer);
        // *Удалить выбор вопроса
        int numberOfSafeQuestion = groupQuestionRepository.findById(numberOfGroupAnswer).get().getQuestionList().size();
        // Проверить номер вопроса
        if (numberOfSafeQuestion > numberOfAnswer.intValue()) {
            // Отправить новый запрос
            if (question.getTypeQuestion().equals("TEXT")) {
                if (update.getCallbackQuery().getMessage().getText().startsWith("Вы ввели:")) {
                    numberOfAnswer++;
                    Question question2 = findQuestion(questions, numberOfAnswer);
                    sendMessage = messageService.findAndCreateMassageFromDBbyInline(update, buttonOnLine, question2);
                    numberOfAnswer++;
                    userOfTg.setSavingAnswerNow(numberOfAnswer);
                    userRepository.save(userOfTg);
                } else {
                    sendMessage = messageService.findAndCreateMassageFromDBbyInline(update, buttonOnLine, question);
                }
            } else {
                sendMessage = messageService.findAndCreateMassageFromDBbyInline(update, buttonOnLine, question);
                numberOfAnswer++;
                userOfTg.setSavingAnswerNow(numberOfAnswer);
                userRepository.save(userOfTg);
            }
            return sendMessage;
        } else {
            userOfTg.setSavingGropeNow(0l);
            userOfTg.setSavingAnswerNow(0l);
            return sendMessage = messageUtils.generateSendMessageWithTextByInLine(update, "Вопросы окончены, спасибо за ответы:");
        }
    }

    private Question findQuestion(List<Question> questions, Long numberOfAnswer) {
        final int numberOfAnswerAdd = numberOfAnswer.intValue();
        return questions.stream()
                .filter(x -> x.getNumberQuestion() == numberOfAnswerAdd)
                .findFirst().get();
    }

    @Transactional
    public void saveAnswer(Update update, UserOfTg userOfTg, Long savingGropeNow, Long savingAnswerNow) {
//        // Заменить все в репозитории
//
//        List<SavingGroupAnswer> savingGroupAnswerList = userOfTg.getSavingGroupAnswers();
//        SavingGroupAnswer savingGroupAnswer = savingGroupAnswerList.get(savingGropeNow.intValue() - 1);
//
//        List<SavingAnswer> savingAnswerList = savingGroupAnswer.getSavingAnswers();
//        SavingAnswer savingAnswer = savingAnswerList.get(savingAnswerNow.intValue() - 1 - 1);
//
//        savingAnswer.setAnswer(update.getCallbackQuery().getData());
//
//        savingAnswerList.set(savingAnswerNow.intValue() - 1, savingAnswer);
//
//        savingGroupAnswer.setSavingAnswers(savingAnswerList);
//
//        savingGroupAnswerList.set(savingGropeNow.intValue() - 1, savingGroupAnswer);
//
//        userOfTg.setSavingGroupAnswers(savingGroupAnswerList);
//
//        userRepository.save(userOfTg);

    }

//    @Transactional
//    protected List<SavingGroupAnswer> generateAnswerListInUser(Long savingGropeNow) {
//        int sizeListQuestions = groupQuestionRepository.findById(savingGropeNow).get().getQuestionList().size();
//        String nameOfQuestionGroup = groupQuestionRepository.findById(savingGropeNow).get().getGroupName();
//        //создать ветку для данных вопросов
//        List<SavingGroupAnswer> savingGroupAnswerList = new ArrayList<>();
//        SavingGroupAnswer savingGroupAnswer = new SavingGroupAnswer();
//        savingGroupAnswer.setNameOfAnswerGrope(nameOfQuestionGroup);
//        savingGroupAnswer.setId(savingGropeNow);
//
//        List<SavingAnswer> savingAnswerList = new ArrayList<>();
//        for (int i = 0; i < sizeListQuestions; i++) {
//            SavingAnswer savingAnswer = new SavingAnswer();
//            savingAnswerList.add(savingAnswer);
//        }
//        savingGroupAnswer.setSavingAnswers(savingAnswerList);
//        savingGroupAnswerList.add(savingGroupAnswer);
//        return savingGroupAnswerList;
//    }
}
