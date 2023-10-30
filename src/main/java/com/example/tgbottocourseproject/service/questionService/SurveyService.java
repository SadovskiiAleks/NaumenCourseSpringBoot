package com.example.tgbottocourseproject.service.questionService;

import com.example.tgbottocourseproject.models.questions.Question;
import com.example.tgbottocourseproject.repository.qustion.GroupQuestionRepository;
import com.example.tgbottocourseproject.utils.InlineQuestionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Transactional
public class SurveyService {

    private final GroupQuestionRepository groupQuestionRepository;
    private final InlineQuestionUtils inlineQuestionUtils;

    @Autowired
    public SurveyService(GroupQuestionRepository groupQuestionRepository, InlineQuestionUtils inlineQuestionUtils) {
        this.groupQuestionRepository = groupQuestionRepository;
        this.inlineQuestionUtils = inlineQuestionUtils;
    }

    public SendMessage startService(Update update) {
        long id = 1;
        int idOfQuestion = 2;
        int gropeOfQuestion = idOfQuestion;

        //GroupQuestion groupQuestion = groupQuestionRepository.findById(id).get();
        Question question1 = groupQuestionRepository.findById(id).get().getQuestionList().get(idOfQuestion);

        SendMessage sendMessage = inlineQuestionUtils.generateQuestionWithAnswers(update,question1,gropeOfQuestion,3);
        return sendMessage;
    }
}
