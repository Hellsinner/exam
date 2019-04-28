package com.hellsinner.exam.model.enums;

public enum QuestionType {

    Individual_choice(1,"radio"),
    Multiple_choice(2,"checkbox"),
    Fill_Blank(3,"fillBlack"),
    //Indefinite_choice(4,"不定项选择题"),
    Judge(4,"judge"),
    Noun_Explanation(5,"Glossary"),
    Short_answer(6,"shortAnswer"),
    Programm(7,"program"),
    Algorithm_Design(7,"算法设计题"),
    Judge_and_Correct_Wrong(8,"判断改错题"),
    Essay(11,"论述题"),
    Other(99,"其他");

    private int type;

    private String name;

    public static int getType(String name){
        int type=0;
        for (QuestionType questionType : QuestionType.values()){
            if (questionType.name.equals(name)){
                type = questionType.type;
            }
        }
        return type;
    }

    QuestionType(int type, String name){
        this.type = type;
        this.name = name;
    }
}
