package com.example.tgbottocourseproject.service.questionService;

import com.example.tgbottocourseproject.models.questions.Question;
import com.example.tgbottocourseproject.models.users.UserOfTg;
import com.example.tgbottocourseproject.repository.qustion.GroupQuestionRepository;
import com.example.tgbottocourseproject.repository.users.UserRepository;
import com.example.tgbottocourseproject.service.messageService.MessageService;
import com.example.tgbottocourseproject.utils.InlineQuestionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
@Transactional
public class SurveyService {

    private final GroupQuestionRepository groupQuestionRepository;
    private final InlineQuestionUtils inlineQuestionUtils;
    private final UserRepository userRepository;
    private final MessageService messageService;
    private final long groupOfQuestion = 1l;

    @Autowired
    public SurveyService(GroupQuestionRepository groupQuestionRepository, InlineQuestionUtils inlineQuestionUtils, UserRepository userRepository, MessageService messageService) {
        this.groupQuestionRepository = groupQuestionRepository;
        this.inlineQuestionUtils = inlineQuestionUtils;
        this.userRepository = userRepository;
        this.messageService = messageService;
    }

    @Transactional
    public SendMessage startService(Update update) {
        long numberOfQuestion = 1;
        long buttonOnLine = 3;

        UserOfTg userOfTg =  userRepository.findByUserName(update.getMessage().getFrom().getUserName());
        userOfTg.setSavingGropeNow(groupOfQuestion);
        userOfTg.setSavingAnswerNow(numberOfQuestion);


        List<Question> questions = groupQuestionRepository.findById(groupOfQuestion).get().getQuestionList();
        Question question2 = questions.get((int) numberOfQuestion);
        SendMessage sendMessage = inlineQuestionUtils.generateQuestionWithAnswers(update,question2,(int) buttonOnLine);
        return sendMessage;


        //long id = 1;
        //int idOfQuestion = 1;
        //int gropeOfQuestion = idOfQuestion;
        //GroupQuestion groupQuestion = groupQuestionRepository.findById(id).get();
    }
}
