package org.api.trabalhodegraduacao.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AtuallizationController {

    @FXML
    private Button bt_sair;

    @FXML
    private void initialize() {

        bt_sair.setOnAction(e -> {
            try {

                bt_sair.setText("Sair");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/api/trabalhodegraduacao/view/telabemvindo.fxml"));
                Scene scene1 = new Scene(loader.load(), 1366, 768);

                Stage stage = (Stage) bt_sair.getScene().getWindow();
                stage.setScene(scene1);
                stage.setTitle("Tela Bem-vindo");
                stage.show();

            } catch (Exception ex) {
                ex.printStackTrace();

            }
        });
    }
}

