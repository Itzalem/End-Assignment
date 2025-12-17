package com.example.endassigment.model;

import java.util.List;

public class Quiz {

    public Quiz(String title, String description, List<Page> pages, String completedHtml, List<CompletedHtmlCondition> completedHtmlCondition) {
        this.title = title;
        this.description = description;
        this.pages = pages;
        this.completedHtml = completedHtml;
        this.completedHtmlCondition = completedHtmlCondition;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public String getCompletedHtml() {
        return completedHtml;
    }

    public void setCompletedHtml(String completedHtml) {
        this.completedHtml = completedHtml;
    }

    public List<CompletedHtmlCondition> getCompletedHtmlCondition() {
        return completedHtmlCondition;
    }

    public void setCompletedHtmlCondition(List<CompletedHtmlCondition> completedHtmlCondition) {
        this.completedHtmlCondition = completedHtmlCondition;
    }

    private String title;
    private String description;
    private List<Page> pages;

    private String completedHtml;
    private List<CompletedHtmlCondition> completedHtmlCondition;
}
