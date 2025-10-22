package org.api.trabalhodegraduacao.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class UserController {

    @FXML
    private Button bt_entrar;
    @FXML
    private TextField texto_login;
    @FXML
    private void initialize() {

        bt_entrar.setOnAction(e -> {
            Alert alert;
            String login = texto_login.getText();
            if (login.equals("123456789")) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/api/trabalhodegraduacao/view/telaalunosenha.fxml"));
                    Scene scene1 = new Scene(loader.load(), 1366, 768);
                    Stage stage = (Stage) bt_entrar.getScene().getWindow();
                    stage.setScene(scene1); stage.setTitle("Tela Aluno"); stage.show();

                } catch (Exception ex) {
                    ex.printStackTrace();

                }
            } else {
            }
        });

    }
}