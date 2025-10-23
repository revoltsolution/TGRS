package org.api.trabalhodegraduacao;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    public static Scene scene;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/api/trabalhodegraduacao/view/BemVindo.fxml"));
        scene = new Scene(fxmlLoader.load(), 1366, 768);
        stage.setScene(scene);
        stage.setTitle("Bem-vindo");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    public static void setRoot(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(fxml));
        Parent root = fxmlLoader.load();
        scene.setRoot(root);
    }
}