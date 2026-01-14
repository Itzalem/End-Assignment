package com.example.endassigment.model;

public class CompletedHtmlOnCondition {
    private String expression;
    private String html;

    public CompletedHtmlOnCondition(String expression, String html) {
        this.expression = expression;
        this.html = html;
    }

    public CompletedHtmlOnCondition() {
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
