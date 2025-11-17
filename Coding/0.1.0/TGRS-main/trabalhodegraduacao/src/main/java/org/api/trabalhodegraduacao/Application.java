package org.api.trabalhodegraduacao;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D; // Import necessário
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen; // Import necessário
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

        // --- CORREÇÃO PARA TELA CHEIA SEM COBRIR A BARRA ---
        // 1. Pega as dimensões visuais da tela (tamanho total MENOS a barra de tarefas)
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

        // 2. Aplica essas dimensões à janela
        stage.setX(visualBounds.getMinX());
        stage.setY(visualBounds.getMinY());
        stage.setWidth(visualBounds.getWidth());
        stage.setHeight(visualBounds.getHeight());

        // 3. Define como maximizado para travar nessas dimensões
        stage.setMaximized(true);
        // ---------------------------------------------------

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

            // Passa o tamanho atual da janela para a nova cena para evitar "piscadas" de redimensionamento
            Scene newScene = new Scene(root, currentStage.getWidth(), currentStage.getHeight());

            currentStage.setTitle(title);
            currentStage.setScene(newScene);

            // Garante que continue maximizado na troca de tela
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