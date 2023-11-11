package com.example.tgbottocourseproject.service.messageService;

import com.example.tgbottocourseproject.models.questions.Question;
import com.example.tgbottocourseproject.models.users.UserOfTg;
import com.example.tgbottocourseproject.repository.qustion.GroupQuestionRepository;
import com.example.tgbottocourseproject.repository.users.UserRepository;
import com.example.tgbottocourseproject.utils.InlineQuestionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
public class MessageService {
    private UserRepository userRepository;
    private final GroupQuestionRepository groupQuestionRepository;
    private final InlineQuestionUtils inlineQuestionUtils;

    public MessageService(UserRepository userRepository, GroupQuestionRepository groupQuestionRepository, InlineQuestionUtils inlineQuestionUtils) {
        this.userRepository = userRepository;
        this.groupQuestionRepository = groupQuestionRepository;
        this.inlineQuestionUtils = inlineQuestionUtils;
    }

    @Transactional
    public SendMessage findAndCreateMassageFromDB(Update update, Long buttonOnLine) {
//        var message = update.getMessage();
//        var sendMessage = new SendMessage();

        UserOfTg userOfTg = userRepository.findByUserName(update.getMessage().getFrom().getUserName());
        Long groupOfQuestion = userOfTg.getSavingGropeNow();
        Long numberOfQuestion = userOfTg.getSavingAnswerNow();

        List<Question> questions = groupQuestionRepository.findById(groupOfQuestion).get().getQuestionList();
        Question question = questions.get(numberOfQuestion.intValue());
        SendMessage sendMessage = inlineQuestionUtils.generateQuestionWithAnswers(update, question,buttonOnLine.intValue());
        return sendMessage;
    }
}
