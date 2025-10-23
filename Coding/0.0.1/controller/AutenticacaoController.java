package org.api.trabalhodegraduacao.controller;

import javafx.event.ActionEvent; // Importe
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader; // Importe
import javafx.scene.Scene; // Importe
import javafx.stage.Stage; // Importe
import java.io.IOException; // Importe

public class AutenticacaoController {

    @FXML private Button bt_entrar_aluno;
    @FXML private TextField texto_senha_aluno;

    @FXML
    void btEntrarAluno(ActionEvent event) {
        String login = texto_senha_aluno.getText();
        if (login.equals("1")) {
            carregarNovaCena("/org/api/trabalhodegraduacao/view/Atualizacoes.fxml", "Atualizações", event);
        } else {
            // Senha errada
        }
    }
    private void carregarNovaCena(String fxmlPath, String title, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene novaScene = new Scene(loader.load(), 1366, 768);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(novaScene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            System.err.println("### FALHA AO CARREGAR A CENA: " + fxmlPath);
            e.printStackTrace();
        }
    }
}