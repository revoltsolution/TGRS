package org.api.trabalhodegraduacao.controller.usuario.aluno;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.api.trabalhodegraduacao.Application; // Verifique se esta é sua classe principal

public class DevolutivasAlunoController {

    // (Adicione aqui qualquer @FXML para a TableView, se precisar)

    @FXML
    public void initialize() {
        // Lógica para carregar as devolutivas do aluno na TableView
        System.out.println("Tela de Devolutivas do Aluno carregada.");
    }

    // --- MÉTODOS DA BARRA LATERAL ---

    @FXML
    void devolutivasGeral(ActionEvent event) {
        // Já está nesta tela, não precisa fazer nada
        System.out.println("Já está na tela de Devolutivas.");
    }

    @FXML
    void perfilAluno(ActionEvent event) {
        String fxmlPath = "/org/api/trabalhodegraduacao/view/usuario/aluno/PerfilAluno.fxml";
        String title = "TGRS - Perfil";
        Application.carregarNovaCena(fxmlPath, title, event);
    }

    @FXML
    void secaoGeral(ActionEvent event) {
        String fxmlPath = "/org/api/trabalhodegraduacao/view/usuario/aluno/SecaoAluno.fxml";
        String title = "TGRS - Seção";
        Application.carregarNovaCena(fxmlPath, title, event);
    }

    @FXML
    void tgGeral(ActionEvent event) {
        String fxmlPath = "/org/api/trabalhodegraduacao/view/usuario/aluno/TGAluno.fxml";
        String title = "TGRS - TG";
        Application.carregarNovaCena(fxmlPath, title, event);
    }

    @FXML
    void sair(ActionEvent event) {
        // Este caminho volta para a tela de login
        String fxmlPath = "/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml";
        String title = "TGRS - Bem Vindo";
        Application.carregarNovaCena(fxmlPath, title, event);
    }

    @FXML
    void telaInicial(ActionEvent event) {
        // Assumindo que a "Tela Inicial" do aluno é a tela de Seção
        String fxmlPath = "/org/api/trabalhodegraduacao/view/usuario/aluno/SecaoAluno.fxml";
        String title = "TGRS - Início";
        Application.carregarNovaCena(fxmlPath, title, event);
    }
}