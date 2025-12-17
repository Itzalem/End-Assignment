package com.example.endassigment.model;

import java.util.List;

public class RadiogroupElement extends Element {
    public RadiogroupElement(String type, String name, String title, boolean isRequired, String choicesOrder, List<String> choices, String correctAnswer) {
        super(type, name, title, isRequired);
        this.choicesOrder = choicesOrder;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
    }

    public String getChoicesOrder() {
        return choicesOrder;
    }

    public void setChoicesOrder(String choicesOrder) {
        this.choicesOrder = choicesOrder;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    private String choicesOrder;
    private List<String> choices;
    private String correctAnswer;
}
