package com.example.endassigment.model;

import java.util.List;

public class Page {
    private int timeLimit;
    private List<Element> elements;

    public Page(int timeLimit, List<Element> elements) {
        this.timeLimit = timeLimit;
        this.elements = elements;
    }

    public Page() {
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }


}
