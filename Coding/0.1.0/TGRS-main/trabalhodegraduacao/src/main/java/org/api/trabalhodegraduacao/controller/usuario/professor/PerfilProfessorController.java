package org.api.trabalhodegraduacao.controller.usuario.professor;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.api.trabalhodegraduacao.Application;

public class PerfilProfessorController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button bt_EditarPerfil;

    @FXML
    private Button bt_Sair;

    @FXML
    private Button bt_SalvarPerfil;

    @FXML
    private Button bt_Trocar_Foto_Perfil;

    @FXML
    private Button bt_alunos_geral;

    @FXML
    private Button bt_devolutivas_geral;

    @FXML
    private Button bt_perfil_geral;

    @FXML
    private Button bt_tela_inicial;

    @FXML
    private Label lblEmailCadastrado;

    @FXML
    private TextField txtGitHub;

    @FXML
    private TextField txtLinkedin;

    @FXML
    private TextField txtSenha;

    @FXML
    void atualizar(ActionEvent event) {

    }

    @FXML
    void trocarFotoPerfil(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert bt_EditarPerfil != null : "fx:id=\"bt_EditarPerfil\" was not injected: check your FXML file 'PerfilProfessor.fxml'.";
        assert bt_Sair != null : "fx:id=\"bt_Sair\" was not injected: check your FXML file 'PerfilProfessor.fxml'.";
        assert bt_SalvarPerfil != null : "fx:id=\"bt_SalvarPerfil\" was not injected: check your FXML file 'PerfilProfessor.fxml'.";
        assert bt_Trocar_Foto_Perfil != null : "fx:id=\"bt_Trocar_Foto_Perfil\" was not injected: check your FXML file 'PerfilProfessor.fxml'.";
        assert bt_alunos_geral != null : "fx:id=\"bt_alunos_geral\" was not injected: check your FXML file 'PerfilProfessor.fxml'.";
        assert bt_devolutivas_geral != null : "fx:id=\"bt_devolutivas_geral\" was not injected: check your FXML file 'PerfilProfessor.fxml'.";
        assert bt_perfil_geral != null : "fx:id=\"bt_perfil_geral\" was not injected: check your FXML file 'PerfilProfessor.fxml'.";
        assert bt_tela_inicial != null : "fx:id=\"bt_tela_inical\" was not injected: check your FXML file 'PerfilProfessor.fxml'.";
        assert lblEmailCadastrado != null : "fx:id=\"lblEmailCadastrado\" was not injected: check your FXML file 'PerfilProfessor.fxml'.";
        assert txtGitHub != null : "fx:id=\"txtGitHub\" was not injected: check your FXML file 'PerfilProfessor.fxml'.";
        assert txtLinkedin != null : "fx:id=\"txtLinkedin\" was not injected: check your FXML file 'PerfilProfessor.fxml'.";
        assert txtSenha != null : "fx:id=\"txtSenha\" was not injected: check your FXML file 'PerfilProfessor.fxml'.";

    }
    public void sair(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event);
    }
    public void perfilProfessor (ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/PerfilProfessor.fxml", "Perfil Professor", event);
    }
    public void devolutivas(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/Historico.fxml", "Historico de Devolutivas", event);
    }
    public void alunos(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/Alunos.fxml", "Alunos", event);
    }
    public void telaInicial(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/AtualizacoesProfessor.fxml", "Tela Inicial", event);
    }

}
