package com.example.tgbottocourseproject.service;

import com.example.tgbottocourseproject.models.questions.Answer;
import com.example.tgbottocourseproject.models.users.SavingAnswer;
import com.example.tgbottocourseproject.models.users.SavingGroupAnswer;
import com.example.tgbottocourseproject.models.users.UserOfTg;
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

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean createUser(Update update) {
        if (userRepository.existsByUserName(update.getMessage().getFrom().getUserName())){
            return false;
        } else {
            UserOfTg userOfTg = generateUser(update);
            userRepository.save(userOfTg);
            return true;
        }
    }

    private UserOfTg generateUser(Update update) {
        Long tgId = update.getMessage().getFrom().getId();
        String firstName = update.getMessage().getFrom().getFirstName();
        String lastName = update.getMessage().getFrom().getLastName();
        String userName = update.getMessage().getFrom().getUserName();
        String languageCode = update.getMessage().getFrom().getLanguageCode();

        Long savingGropeNow = 0l;
        Long savingAnswerNow = 0l;

        UserOfTg userOfTg = new UserOfTg();
        List<SavingGroupAnswer> savingGroupAnswers = new ArrayList<>();
        SavingGroupAnswer savingGroupAnswer = new SavingGroupAnswer();
        List<SavingAnswer> savingAnswerList = new ArrayList<>();


        userOfTg.setTgId(tgId);
        userOfTg.setFirstName(firstName);
        userOfTg.setLastName(lastName);
        userOfTg.setUserName(userName);
        userOfTg.setLanguageCode(languageCode);

        savingGroupAnswer.setSavingAnswers(savingAnswerList);
        savingGroupAnswers.add(savingGroupAnswer);

        userOfTg.setSavingGroupAnswers(savingGroupAnswers);

        userOfTg.setSavingGropeNow(savingGropeNow);
        userOfTg.setSavingAnswerNow(savingAnswerNow);

        return userOfTg;
    }


}
