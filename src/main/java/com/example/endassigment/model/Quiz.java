package com.example.endassigment.model;

import java.util.List;

public class Quiz {

    public String title;
    public String description;
    public List<Page> pages;

    public String completedHtml;
    public List<CompletedHtmlCondition> completedHtmlCondition;
}
