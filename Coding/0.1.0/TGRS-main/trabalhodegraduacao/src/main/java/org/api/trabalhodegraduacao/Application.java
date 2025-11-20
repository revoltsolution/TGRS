package org.api.trabalhodegraduacao;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;

public class Application extends javafx.application.Application {

    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        Application.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("TGRS - Revolt Solutions");
        stage.setScene(scene);

        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

        stage.setX(visualBounds.getMinX());
        stage.setY(visualBounds.getMinY());
        stage.setWidth(visualBounds.getWidth());
        stage.setHeight(visualBounds.getHeight());

        stage.setMaximized(true);

        stage.show();
    }

    public static void carregarNovaCena(String fxmlPath, String title, Event event) {
        try {
            Stage currentStage;
            if (event != null && event.getSource() instanceof Node) {
                currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            } else {
                currentStage = Application.stage;
            }

            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(fxmlPath));
            Parent root = fxmlLoader.load();

            Scene newScene = new Scene(root, currentStage.getWidth(), currentStage.getHeight());

            currentStage.setTitle(title);
            currentStage.setScene(newScene);

            currentStage.setMaximized(true);

            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro crítico ao carregar a cena: " + fxmlPath);
        }
    }

    public static void main(String[] args) {
        try {
            org.api.trabalhodegraduacao.bancoDeDados.GerenciadorDB gbd = new org.api.trabalhodegraduacao.bancoDeDados.GerenciadorDB();
            gbd.criarBancoDeDados("TGRSDB");
            gbd.criarTodasAsTabelas();
        } catch (Exception e) {
            e.printStackTrace();
        }

        launch();
    }
}