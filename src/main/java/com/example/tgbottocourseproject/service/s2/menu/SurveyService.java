package com.example.tgbottocourseproject.service.s2.menu;

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
    private final long numberOfQuestion = 1;
    private final long buttonOnLine = 3;

    @Autowired
    public SurveyService(GroupQuestionRepository groupQuestionRepository, InlineQuestionUtils inlineQuestionUtils, UserRepository userRepository, MessageService messageService) {
        this.groupQuestionRepository = groupQuestionRepository;
        this.inlineQuestionUtils = inlineQuestionUtils;
        this.userRepository = userRepository;
        this.messageService = messageService;
    }

    @Transactional
    public SendMessage startService(Update update) {
        UserOfTg userOfTg = userRepository.findByUserName(update.getMessage().getFrom().getUserName());
        userOfTg.setSavingGropeNow(groupOfQuestion);
        userOfTg.setSavingAnswerNow(numberOfQuestion);

        List<Question> questions = groupQuestionRepository.findById(groupOfQuestion).get().getQuestionList();
        Question question = questions.stream()
                .filter(x->x.getNumberQuestion() == numberOfQuestion)
                .findFirst().get();
        SendMessage sendMessage = messageService.findAndCreateMassageFromDBbyMessage(update,buttonOnLine, question);

        userOfTg.setSavingAnswerNow(groupOfQuestion + 1);
        userRepository.save(userOfTg);
        return sendMessage;
    }
}
