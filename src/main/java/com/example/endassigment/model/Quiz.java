package com.example.endassigment.model;

import java.util.List;

public class Quiz {
    private String quizId;
    private String title;
    private String description;
    private List<Page> pages;

    private String completedHtml;
    private List<CompletedHtmlOnCondition> completedHtmlOnCondition;

    private boolean allowRetry;
    private int randomQuestionCount;

    public Quiz(String title, String description, List<Page> pages, String completedHtml, List<CompletedHtmlOnCondition> completedHtmlOnCondition) {
        this.title = title;
        this.description = description;
        this.pages = pages;
        this.completedHtml = completedHtml;
        this.completedHtmlOnCondition = completedHtmlOnCondition;
        this.allowRetry = allowRetry;
        this.randomQuestionCount = randomQuestionCount;
    }

    public Quiz() {
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

    public List<CompletedHtmlOnCondition> getCompletedHtmlOnCondition() {
        return completedHtmlOnCondition;
    }

    public void setCompletedHtmlOnCondition(List<CompletedHtmlOnCondition> completedHtmlOnCondition) {
        this.completedHtmlOnCondition = completedHtmlOnCondition;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public boolean isAllowRetry() {
        return allowRetry;
    }

    public void setAllowRetry(boolean allowRetry) {
        this.allowRetry = allowRetry;
    }

    public int getRandomQuestionCount() {
        return randomQuestionCount;
    }

    public void setRandomQuestionCount(int randomQuestionCount) {
        this.randomQuestionCount = randomQuestionCount;
    }


}
