package org.api.trabalhodegraduacao;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.api.trabalhodegraduacao.controller.UserController;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/telabemvindo.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1366, 768);
        stage.setScene(scene); stage.setTitle("Bem-vindo"); stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
