package com.example.endassigment.model;

public class ElementFactory {

    public static Element createElement(String type) {
        if (type == null) return null;

        switch (type.toLowerCase()) {
            case "radiogroup":
                return new RadiogroupElement();
            case "boolean":
                return new BooleanElement();
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}