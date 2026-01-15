package com.example.endassigment.model;


public class BooleanElement extends Element {
    private String labelTrue;
    private String labelFalse;
    private boolean correctAnswer;

    public String getLabelTrue() {
        return labelTrue;
    }

    public void setLabelTrue(String labelTrue) {
        this.labelTrue = labelTrue;
    }

    public String getLabelFalse() {
        return labelFalse;
    }

    public void setLabelFalse(String labelFalse) {
        this.labelFalse = labelFalse;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public BooleanElement(String labelTrue, String labelFalse, boolean correctAnswer) {
        this.labelTrue = labelTrue;
        this.labelFalse = labelFalse;
        this.correctAnswer = correctAnswer;
    }

    public BooleanElement() {

    }

}


