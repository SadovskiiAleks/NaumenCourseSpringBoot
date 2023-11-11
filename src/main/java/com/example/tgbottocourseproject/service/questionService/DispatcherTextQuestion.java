package com.example.tgbottocourseproject.service.questionService;

import com.example.tgbottocourseproject.models.questions.Answer;
import com.example.tgbottocourseproject.models.questions.Question;
import com.example.tgbottocourseproject.models.users.UserOfTg;
import com.example.tgbottocourseproject.repository.qustion.GroupQuestionRepository;
import com.example.tgbottocourseproject.repository.users.UserRepository;
import com.example.tgbottocourseproject.service.enums.TypeOfQuestion;
import com.example.tgbottocourseproject.utils.InlineQuestionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Service
public class DispatcherTextQuestion {
    private UserRepository userRepository;
    private GroupQuestionRepository groupQuestionRepository;
    private InlineQuestionUtils inlineQuestionUtils;

    @Autowired
    public DispatcherTextQuestion(UserRepository userRepository, GroupQuestionRepository groupQuestionRepository, InlineQuestionUtils inlineQuestionUtils) {
        this.userRepository = userRepository;
        this.groupQuestionRepository = groupQuestionRepository;
        this.inlineQuestionUtils = inlineQuestionUtils;
    }

//    @Transactional
//    public SendMessage createButtonToSaveAnswer(Update update) {
//
//        // Проверить номер вопроса который должен получить пользователь
//        UserOfTg userOfTg = userRepository.findByUserName(update.getMessage().getFrom().getUserName());
//        // *Получить номер вопроса из репозитория напрямую
//        Long savingGropeNow = userOfTg.getSavingGropeNow();
//        Long savingAnswerNow = userOfTg.getSavingAnswerNow();
//
//        // *Данный вопрос - это текстовый ответ, на который должен прийти запрос подтверждение.
//
//        // *Нужно понять, что за пользователь пишет и узнать в БД на какой запрос ему нужно потдвердение.
//
//        // *Если запросы не заданы то нужно написать "Я еще не могу обрабатывать такие команды :(, если вы потерялись введите /start"
//        update.getMessage().getText();
//        return new SendMessage();
//    }

    @Transactional
    public SendMessage createButtonToSaveAnswer(Update update) {

        //*Проверить какой тип запроса направлен был юзеру если текст то взять
        // его ответ записать в кнопку и отправить на подтверждение
        UserOfTg userOfTg = userRepository.findByUserName(update.getMessage().getFrom().getUserName());
        Long groupOfQuestion = userOfTg.getSavingGropeNow();
        Long numberOfQuestion = userOfTg.getSavingAnswerNow();

        List<Question> questions = groupQuestionRepository.findById(groupOfQuestion).get().getQuestionList();
        Question question = questions.get(numberOfQuestion.intValue());

        if (question.getTypeQuestion().equals(TypeOfQuestion.TEXT)) {
            //*создать кнопку для подтверждения запроса
            int buttonOnLine = 3;
            Question newQuestion = createSetQuestion(update.getMessage().getText());
            SendMessage sendMessage = inlineQuestionUtils.generateQuestionWithAnswers(update, newQuestion, buttonOnLine);
        }
        return new SendMessage();
    }

    private Question createSetQuestion(String answerName) {
        Question newQuestion = new Question();

        newQuestion.setNameQuestion(String.format("Вы ввели: %s. Для подтверждения нажмите кнопку ниже или введите ответ заново", answerName));

        Answer answer = new Answer();
        answer.setNameAnswer("Подтвердить");
        List<Answer> answers = new ArrayList<>();
        answers.add(answer);

        newQuestion.setAnswer(answers);
        return newQuestion;
    }
}
