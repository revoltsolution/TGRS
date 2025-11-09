module org.api.trabalhodegraduacao {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.xml.crypto;
    requires javafx.base;
    requires java.sql;
    requires java.desktop;


    opens org.api.trabalhodegraduacao.controller to javafx.fxml;

    opens org.api.trabalhodegraduacao.view to javafx.fxml;

    opens org.api.trabalhodegraduacao.images to javafx.fxml;

    exports org.api.trabalhodegraduacao;
    opens org.api.trabalhodegraduacao.controller.usuario.professor to javafx.fxml;
    opens org.api.trabalhodegraduacao.controller.usuario to javafx.fxml;
    opens org.api.trabalhodegraduacao.controller.usuario.aluno to javafx.fxml;
}