package com.example.excusegenerator;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class ExcuseModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private String selectedCategory = "";
    private String generatedExcuse = "";

    // Кэш для запоминания и восстановления последних введенных данных
    private String lastCategory = "";

    private final Map<String, List<String>> excusesMap = new HashMap<>();

    public ExcuseModel() {
        excusesMap.put("Учеба", Arrays.asList(
                "Извините, у меня отгрузился не тот коммит из-за сбоя Git.",
                "Я написал идеальный код, но его съел локальный антивирус."
        ));
        excusesMap.put("Работа", Arrays.asList(
                "Прод упал из-за внезапного обновления зависимостей.",
                "Застрял на созвоне, который мог бы быть одним сообщением."
        ));
        excusesMap.put("Опоздание", Arrays.asList(
                "Маршрутка попала во временную аномалию.",
                "Кот лег на ключи, пришлось ждать его пробуждения."
        ));
        excusesMap.put("Забывчивость", Arrays.asList(
                "Память переполнилась, сработал Garbage Collector.",
                "Запись об этой задаче не синхронизировалась с облаком."
        ));
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void generateExcuse(String category) {
        if (category == null || !excusesMap.containsKey(category)) {
            support.firePropertyChange("error", null, "Выберите корректную категорию!");
            return;
        }

        this.lastCategory = category;
        this.selectedCategory = category;

        List<String> list = excusesMap.get(category);
        String newExcuse = list.get(new Random().nextInt(list.size()));

        String oldExcuse = this.generatedExcuse;
        this.generatedExcuse = newExcuse;

        // Активная модель уведомляет слушателей (View)
        support.firePropertyChange("excuse", oldExcuse, newExcuse);
    }

    public String getLastCategory() {
        return lastCategory;
    }
}