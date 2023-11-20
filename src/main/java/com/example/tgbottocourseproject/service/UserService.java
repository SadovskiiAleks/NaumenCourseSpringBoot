package com.example.tgbottocourseproject.service;

import com.example.tgbottocourseproject.models.questions.GroupQuestion;
import com.example.tgbottocourseproject.models.questions.Question;
import com.example.tgbottocourseproject.models.users.SavingAnswer;
import com.example.tgbottocourseproject.models.users.SavingGroupAnswer;
import com.example.tgbottocourseproject.models.users.UserOfTg;
import com.example.tgbottocourseproject.repository.qustion.GroupQuestionRepository;
import com.example.tgbottocourseproject.repository.qustion.QuestionRepository;
import com.example.tgbottocourseproject.repository.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private GroupQuestionRepository groupQuestionRepository;
    private QuestionRepository questionRepository;

    @Autowired
    public UserService(UserRepository userRepository, GroupQuestionRepository groupQuestionRepository, QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.groupQuestionRepository = groupQuestionRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public boolean createUser(Update update) {
        // дать меню на перезапись вопросов
        UserOfTg userOfTg = userRepository.findByUserName(update.getMessage().getFrom().getUserName());
        if (userOfTg != null) {
            userRepository.delete(userOfTg);
        }
        userOfTg = generateUser(update);
        userRepository.save(userOfTg);
        return true;

    }

    private UserOfTg generateUser(Update update) {
        UserOfTg userOfTg = new UserOfTg();

        Long tgId = update.getMessage().getFrom().getId();
        String firstName = update.getMessage().getFrom().getFirstName();
        String lastName = update.getMessage().getFrom().getLastName();
        String userName = update.getMessage().getFrom().getUserName();
        String languageCode = update.getMessage().getFrom().getLanguageCode();

        Long savingGropeNow = 0l;
        Long savingAnswerNow = 0l;

        userOfTg.setTgId(tgId);
        userOfTg.setFirstName(firstName);
        userOfTg.setLastName(lastName);
        userOfTg.setUserName(userName);
        userOfTg.setLanguageCode(languageCode);

        userOfTg.setSavingGropeNow(savingGropeNow);
        userOfTg.setSavingAnswerNow(savingAnswerNow);

        createMemoryToAnswer(userOfTg);

        return userOfTg;
    }

    @Transactional
    public void createMemoryToAnswer(UserOfTg userOfTg) {

        List<GroupQuestion> groupQuestionsList = groupQuestionRepository.getAllQ();

        List<SavingGroupAnswer> savingGroupAnswers = new ArrayList<>();
        for (int i = 0; i < groupQuestionsList.size(); i++) {
            SavingGroupAnswer savingGroupAnswer = new SavingGroupAnswer();
            savingGroupAnswer.setNameOfAnswerGrope(groupQuestionsList.get(i).getGroupName());
            List<SavingAnswer> savingAnswerList = new ArrayList<>();
            for (int j = 0; j < groupQuestionsList.get(i).getQuestionList().size(); j++) {
                SavingAnswer sa = new SavingAnswer();
                sa.setQuestion(
                        groupQuestionsList.get(i)
                                .getQuestionList().get(j).getNameQuestion());
                savingAnswerList.add(sa);
            }
            savingGroupAnswer.setSavingAnswers(savingAnswerList);
            savingGroupAnswers.add(savingGroupAnswer);
        }
        userOfTg.setSavingGroupAnswers(savingGroupAnswers);
    }
}
