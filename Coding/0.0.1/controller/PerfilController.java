package org.api.trabalhodegraduacao.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PerfilController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button bt_sair;

    @FXML
    void initialize() {
        assert bt_sair != null : "fx:id=\"bt_sair\" was not injected: check your FXML file 'Perfil.fxml'.";

    }

}
