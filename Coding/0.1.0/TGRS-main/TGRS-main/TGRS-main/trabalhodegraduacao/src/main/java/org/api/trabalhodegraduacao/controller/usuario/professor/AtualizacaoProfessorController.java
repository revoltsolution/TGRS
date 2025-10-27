package org.api.trabalhodegraduacao.controller.usuario.professor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.api.trabalhodegraduacao.Application;

public class AtualizacaoProfessorController {

    public Button bt_Alunos;
    public Button bt_Historico;
    public Button bt_Perfil;
    public Button bt_Sair;
    @FXML
    private AnchorPane contentPane;
    @FXML
    public void initialize() {
        Application.carregarConteudoPane("/org/api/trabalhodegraduacao/view/usuario/professor/Alunos.fxml", contentPane);
    }

    @FXML
    void btSair(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Autenticação", event);
    }

    @FXML
    void btAlunos(ActionEvent event) {
        Application.carregarConteudoPane("/org/api/trabalhodegraduacao/view/usuario/professor/Alunos.fxml", contentPane);
    }

    @FXML
    void btPerfil(ActionEvent event) {
       Application.carregarConteudoPane("/org/api/trabalhodegraduacao/view/usuario/professor/PerfilProfessor.fxml", contentPane);
    }

    @FXML
    void btHistorico(ActionEvent event) {
        Application.carregarConteudoPane("/org/api/trabalhodegraduacao/view/usuario/professor/Historico.fxml", contentPane);
    }

}