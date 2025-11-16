package org.api.trabalhodegraduacao;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Alert;
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
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Application.class.getResource(fxmlPath));

            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            if (stage == null && scene != null) {
                stage = (Stage) scene.getWindow();
            }

            if (stage == null) {
                throw new IllegalStateException("Stage principal não encontrado. A aplicação pode não ter sido iniciada corretamente.");
            }

            Scene novaScene = new Scene(root, 1366, 768);

            stage.setScene(novaScene);
            stage.setTitle(title);
            stage.show();

        } catch (Throwable e) {
            System.err.println("### FALHA CRÍTICA AO CARREGAR A CENA: " + fxmlPath);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro Crítico de Navegação");
            alert.setHeaderText("A tela não pôde ser carregada.");
            alert.setContentText("Caminho do FXML: " + fxmlPath + "\n\nDetalhes do erro: " + e.getMessage() + "\n\nVerifique o caminho do FXML e dos recursos (CSS/Imagens) na pasta 'resources'.");
            alert.showAndWait();

            e.printStackTrace();
        }
    }

    public static void carregarConteudoPane(String fxmlPath, AnchorPane contentPane) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Application.class.getResource(fxmlPath));

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