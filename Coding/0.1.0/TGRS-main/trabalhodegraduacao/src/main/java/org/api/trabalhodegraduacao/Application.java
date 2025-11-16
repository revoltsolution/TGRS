package org.api.trabalhodegraduacao;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.api.trabalhodegraduacao.bancoDeDados.GerenciadorDB;

import java.io.IOException;

public class Application extends javafx.application.Application {

    public static Scene scene;

    @Override
    public void init() throws Exception {
        System.out.println("### INICIALIZANDO BANCO DE DADOS ###");
        GerenciadorDB gbd = new GerenciadorDB();

        gbd.criarBancoDeDados("TGRSDB");
        gbd.criarTodasAsTabelas();

        System.out.println("### BANCO DE DADOS PRONTO ###");
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml"));
        scene = new Scene(fxmlLoader.load(), 1366, 768);
        stage.setScene(scene);
        stage.setTitle("Bem-vindo");
        stage.show();
    }

    public static void carregarNovaCena(String fxmlPath, String title, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource(fxmlPath));
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
    public static void carregarConteudoPane(String fxmlPath, AnchorPane contentPane) {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource(fxmlPath));
            Parent conteudo = loader.load();

            contentPane.getChildren().clear();
            contentPane.getChildren().add(conteudo);

            AnchorPane.setTopAnchor(conteudo, 0.0);
            AnchorPane.setBottomAnchor(conteudo, 0.0);
            AnchorPane.setLeftAnchor(conteudo, 0.0);
            AnchorPane.setRightAnchor(conteudo, 0.0);

        } catch (IOException e) {
            System.err.println("### FALHA AO CARREGAR O CONTEÚDO NO PANE: " + fxmlPath);
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("### ERRO: FXML não encontrado em: " + fxmlPath);
            e.printStackTrace();
        }
    }
}
