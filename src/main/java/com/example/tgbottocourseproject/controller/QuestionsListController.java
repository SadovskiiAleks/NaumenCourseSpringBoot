package com.example.tgbottocourseproject.controller;

import com.example.tgbottocourseproject.DTO.Question;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionsListController {
    List<Question> questions = new ArrayList<>();
    int numberOfQuestion = 0;

    public QuestionsListController() {
        Question question = new Question();
        question.setQuestion("question");
        question.getAnswers().add("1");
        question.getAnswers().add("2");
        question.getAnswers().add("3");
        this.questions.add(question);

    }

    public void addQueryAnswer(Update update) {
        update.getCallbackQuery().getData().toString();
    }

    public void addTextAnswer(Update update) {

    }

//System.out.println(message.getText());
//            List<String> listOfQ = new ArrayList<>();
//            listOfQ.add("Yes");
//            listOfQ.add("No");
//            listOfQ.add("No");
//            listOfQ.add("No");
//            listOfQ.add("No");
//            setView(questionUtils.generateQuestionWithAnswers(update, "Are your a programmer?", listOfQ, 2));


}
