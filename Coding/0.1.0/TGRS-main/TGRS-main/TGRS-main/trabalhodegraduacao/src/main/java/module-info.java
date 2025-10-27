module org.api.trabalhodegraduacao {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.xml.crypto;
    requires javafx.base;
    requires java.sql;
    requires java.desktop;

    // --- PERMISSÕES OBRIGATÓRIAS ---

    // 1. Abre seus CONTROLLERS para o JavaFX
    opens org.api.trabalhodegraduacao.controller to javafx.fxml;

    // 2. (A CORREÇÃO) Abre seus FXMLs (pasta 'view') para o JavaFX
    opens org.api.trabalhodegraduacao.view to javafx.fxml;

    // 3. (IMPORTANTE) Abre suas IMAGENS (pasta 'images') para o JavaFX
    opens org.api.trabalhodegraduacao.images to javafx.fxml;

    // Exporta seu pacote principal (para a classe Application)
    exports org.api.trabalhodegraduacao;
    opens org.api.trabalhodegraduacao.controller.usuario.professor to javafx.fxml;
    opens org.api.trabalhodegraduacao.controller.usuario to javafx.fxml;
    opens org.api.trabalhodegraduacao.controller.usuario.aluno to javafx.fxml;
}