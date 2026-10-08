package com.example.excusegenerator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        ExcuseModel model = new ExcuseModel();
        ExcuseController controller = new ExcuseController(model);
        MainView view = new MainView(model, controller);

        Scene scene = new Scene(view.createContent(primaryStage), 480, 260);

        // Подключаем CSS
        if (getClass().getResource("style.css") != null) {
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        }

        primaryStage.setTitle("Вариант 26 — Генератор оправданий");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}