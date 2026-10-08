package com.example.excusegenerator;

public class ExcuseController {
    private final ExcuseModel model;

    public ExcuseController(ExcuseModel model) {
        this.model = model;
    }

    public void handleGenerateAction(String category) {
        model.generateExcuse(category);
    }
}