module com.example.excusegenerator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    requires org.controlsfx.controls;

    opens com.example.excusegenerator to javafx.fxml;
    exports com.example.excusegenerator;
}