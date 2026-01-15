package com.example.endassigment.model;

public class Element {
    private String type;
    private String name;
    private String title;
    private boolean isRequired;

    public Element() {
    }

    public Element(String type, String name, String title, boolean isRequired) {
        this.type = type;
        this.name = name;
        this.title = title;
        this.isRequired = isRequired;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setIsRequired(boolean required) {
        isRequired = required;
    }


}
