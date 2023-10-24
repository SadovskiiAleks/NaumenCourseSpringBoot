package com.example.tgbottocourseproject.repository;

import com.example.tgbottocourseproject.DTO.Question;

import java.util.ArrayList;
import java.util.List;


public class Repository {

    private List<Question> questionList;

    public Repository() {
        List<Question> questionList = new ArrayList<>();

        List<String> answers11 = new ArrayList<>();
        answers11.add("5-10");
        answers11.add("10-15");
        answers11.add("15-20");
        answers11.add("25-35");
        Question question11 = new Question(1,1,"How old are you?", answers11);

        Question question12 = new Question(1,1,"How old are you?", null);

        List<String> answers13 = new ArrayList<>();
        answers13.add("5-10");
        answers13.add("10-15");
        answers13.add("15-20");
        answers13.add("25-35");
        Question question13 = new Question(1,1,"would you like language ", answers13);

        List<String> answers21 = new ArrayList<>();
        answers21.add("5");
        answers21.add("10");
        answers21.add("2");
        answers21.add("4");
        Question question21 = new Question(1,1,"Password?", answers21);


        Question question23 = new Question(1,1,"what your name?", null);

        questionList.add(question11);
        questionList.add(question12);
        questionList.add(question13);
        questionList.add(question21);
        questionList.add(question23);

        this.questionList = questionList;
    }
}
