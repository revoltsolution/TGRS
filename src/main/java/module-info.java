module org.api.trabalhodegraduacao {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens org.api.trabalhodegraduacao to javafx.fxml;
    exports org.api.trabalhodegraduacao;
    exports org.api.trabalhodegraduacao.controller;
    opens org.api.trabalhodegraduacao.controller to javafx.fxml;
}