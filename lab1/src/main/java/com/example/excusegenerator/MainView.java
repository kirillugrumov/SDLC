package com.example.excusegenerator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainView implements PropertyChangeListener {
    private final Label titleLabel = new Label("Генератор Оправданий");
    private final Label excuseLabel = new Label("Нажмите кнопку ниже, чтобы выбрать ситуацию");
    private final Label statusLabel = new Label("Последняя ситуация: —");
    private final Button openDialogBtn = new Button("Ввести данные");

    private final ExcuseController controller;
    private final ExcuseModel model;

    public MainView(ExcuseModel model, ExcuseController controller) {
        this.model = model;
        this.controller = controller;
        this.model.addPropertyChangeListener(this);
    }

    public VBox createContent(Stage primaryStage) {
        titleLabel.getStyleClass().add("title-label");

        VBox resultCard = new VBox(10, titleLabel, excuseLabel, statusLabel);
        resultCard.getStyleClass().add("result-card");
        resultCard.setAlignment(Pos.CENTER);

        openDialogBtn.setOnAction(e -> showInputDialog(primaryStage));

        VBox root = new VBox(20, resultCard, openDialogBtn);
        root.setPadding(new Insets(25));
        root.setAlignment(Pos.CENTER);
        return root;
    }

    private void showInputDialog(Stage owner) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(owner);
        dialog.setTitle("Выбор ситуации");

        TextField inputField = new TextField();
        inputField.setPromptText("Учеба, Работа, Опоздание...");

        if (!model.getLastCategory().isEmpty()) {
            inputField.setText(model.getLastCategory());
        }

        Button generateBtn = new Button("Сгенерировать");
        generateBtn.setOnAction(e -> {
            controller.handleGenerateAction(inputField.getText());
            dialog.close();
        });

        VBox dialogBox = new VBox(15, new Label("Введите или выберите ситуацию:"), inputField, generateBtn);
        dialogBox.setPadding(new Insets(20));
        dialogBox.setAlignment(Pos.CENTER);

        Scene dialogScene = new Scene(dialogBox, 320, 180);

        // Подключение CSS к диалогу
        if (getClass().getResource("style.css") != null) {
            dialogScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        }

        dialog.setScene(dialogScene);
        dialog.show();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("excuse".equals(evt.getPropertyName())) {
            excuseLabel.setText((String) evt.getNewValue());
            statusLabel.setText("Последняя ситуация: " + model.getLastCategory());
        } else if ("error".equals(evt.getPropertyName())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, (String) evt.getNewValue());
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }
}