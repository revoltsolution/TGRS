module org.api.trabalhodegraduacao {
    // --- Módulos que seu projeto usa ---
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics; // Para layouts, imagens, etc.
    requires java.sql;
    requires mysql.connector.j; // Ou o nome exato do módulo do conector


    // --- Permissão para o JavaFX iniciar o App ---
    exports org.api.trabalhodegraduacao;


    // --- Permissões para o FXML acessar os Controllers ---
    opens org.api.trabalhodegraduacao.controller.usuario to javafx.fxml;
    opens org.api.trabalhodegraduacao.controller.usuario.aluno to javafx.fxml;
    opens org.api.trabalhodegraduacao.controller.usuario.professor to javafx.fxml;
    opens org.api.trabalhodegraduacao.utils to javafx.fxml;


    // --- A CORREÇÃO ESTÁ AQUI ---
    // Permite que a TableView (javafx.base) acesse sua entidade 'Usuario'
    opens org.api.trabalhodegraduacao.entities to javafx.base;
}