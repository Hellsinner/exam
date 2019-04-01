package com.hellsinner.exam.model.enums;

public enum QuestionType {
    Fill_Blank(1,"填空题"),
    Individual_choice(2,"单项选择题"),
    Multiple_choice(3,"多项选择题"),
    Indefinite_choice(4,"不定项选择题"),
    Judge(5,"判断题"),
    Programm(6,"编程题"),
    Algorithm_Design(7,"算法设计题"),
    Judge_and_Correct_Wrong(8,"判断改错题"),
    Noun_Explanation(9,"名词解释"),
    Short_answer(10,"简单题"),
    Essay(11,"论述题"),
    Other(99,"其他");

    private int type;

    private String name;

    QuestionType(int type, String name){
        this.type = type;
        this.name = name;
    }
}
