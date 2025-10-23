package org.api.trabalhodegraduacao.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class AtualizacaoController {

    @FXML private Button bt_sair;
    @FXML private Button bt_Turmas;
    @FXML private Button bt_Perfil;
    @FXML private Button bt_Historico;

    @FXML
    void btSair(ActionEvent event) {
        carregarNovaCena("/org/api/trabalhodegraduacao/view/Autenticacao.fxml", "Autenticação", event);
    }

    @FXML
    void btTurmas(ActionEvent event){
        carregarNovaCena("/org/api/trabalhodegraduacao/view/Turmas.fxml", "Turmas", event);
    }

    @FXML
    void btPerfil(ActionEvent event){
        carregarNovaCena("/org/api/trabalhodegraduacao/view/Perfil.fxml", "Perfil", event);
    }

    @FXML
    void btHistorico(ActionEvent event){
        carregarNovaCena("/org/api/trabalhodegraduacao/view/Historico.fxml", "Histórico", event);
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