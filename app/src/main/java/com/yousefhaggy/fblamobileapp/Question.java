package com.yousefhaggy.fblamobileapp;

public class Question {
    String question;
    String[] choices;
    String answer;
    private String selectedChoice;
    boolean isWrong;
    public Question(String question, String[] choices,String answer)
    {
        this.question=question;
        this.choices=choices;
        this.answer=answer;
    }
    public String getSelectedChoice() {
        return selectedChoice;
    }

    public void setSelectedChoice(String selectedChoice) {
        this.selectedChoice = selectedChoice;
    }

}
