package org.api.trabalhodegraduacao.controller.usuario.aluno;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.api.trabalhodegraduacao.Application;

public class DevolutivasAlunoController {


    @FXML
    public void initialize() {
        System.out.println("Tela de Devolutivas do Aluno carregada.");
    }


    @FXML
    void devolutivasGeral(ActionEvent event) {
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
        String fxmlPath = "/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml";
        String title = "TGRS - Bem Vindo";
        Application.carregarNovaCena(fxmlPath, title, event);
    }

    @FXML
    void telaInicial(ActionEvent event) {
        String fxmlPath = "/org/api/trabalhodegraduacao/view/usuario/aluno/SecaoAluno.fxml";
        String title = "TGRS - Início";
        Application.carregarNovaCena(fxmlPath, title, event);
    }
}