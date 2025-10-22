package org.api.trabalhodegraduacao.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PassController {


    @FXML
    private Button bt_entrar_aluno;
    @FXML
    private TextField texto_senha_aluno;

    @FXML
    private void initialize() {

        bt_entrar_aluno.setOnAction(e -> {

            String login = texto_senha_aluno.getText();
            if (login.equals("1234")) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/api/trabalhodegraduacao/view/telaatualizacoes.fxml"));
                    Scene scene1 = new Scene(loader.load(), 1366, 768);

                    Stage stage = (Stage) bt_entrar_aluno.getScene().getWindow();
                    stage.setScene(scene1);
                    stage.setTitle("Tela Aluno");
                    stage.show();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {

            }
        });
    }
}