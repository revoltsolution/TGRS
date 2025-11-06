package org.api.trabalhodegraduacao.controller.usuario.aluno;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.api.trabalhodegraduacao.Application;

public class PerfilAlunoController {

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
    private Button bt_TrocarFotoPerfil;

    @FXML
    private Button bt_perfil_geral;

    @FXML
    private Button bt_secao_geral;

    @FXML
    private Button bt_tela_inicial;

    @FXML
    private Button bt_tg_geral;

    @FXML
    private ChoiceBox<?> choiceOrientador;

    @FXML
    private ImageView imgVwFotoPerfil;

    @FXML
    private Label lblEmailCadastrado;

    @FXML
    private Label lbl_NomeUsuario;

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
    void nomeUsuario(MouseEvent event) {

    }


    @FXML
    void trocarFoto(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert bt_EditarPerfil != null : "fx:id=\"bt_EditarPerfil\" was not injected: check your FXML file 'PerfilAluno.fxml'.";
        assert bt_Sair != null : "fx:id=\"bt_Sair\" was not injected: check your FXML file 'PerfilAluno.fxml'.";
        assert bt_SalvarPerfil != null : "fx:id=\"bt_SalvarPerfil\" was not injected: check your FXML file 'PerfilAluno.fxml'.";
        assert bt_TrocarFotoPerfil != null : "fx:id=\"bt_TrocarFotoPerfil\" was not injected: check your FXML file 'PerfilAluno.fxml'.";
        assert bt_perfil_geral != null : "fx:id=\"bt_perfil_geral\" was not injected: check your FXML file 'PerfilAluno.fxml'.";
        assert bt_secao_geral != null : "fx:id=\"bt_secao_geral\" was not injected: check your FXML file 'PerfilAluno.fxml'.";
        assert bt_tela_inicial != null : "fx:id=\"bt_tela_inicial\" was not injected: check your FXML file 'PerfilAluno.fxml'.";
        assert bt_tg_geral != null : "fx:id=\"bt_tg_geral\" was not injected: check your FXML file 'PerfilAluno.fxml'.";
        assert choiceOrientador != null : "fx:id=\"choiceOrientador\" was not injected: check your FXML file 'PerfilAluno.fxml'.";
        assert imgVwFotoPerfil != null : "fx:id=\"imgVwFotoPerfil\" was not injected: check your FXML file 'PerfilAluno.fxml'.";
        assert lblEmailCadastrado != null : "fx:id=\"lblEmailCadastrado\" was not injected: check your FXML file 'PerfilAluno.fxml'.";
        assert lbl_NomeUsuario != null : "fx:id=\"lbl_NomeUsuario\" was not injected: check your FXML file 'PerfilAluno.fxml'.";
        assert txtGitHub != null : "fx:id=\"txtGitHub\" was not injected: check your FXML file 'PerfilAluno.fxml'.";
        assert txtLinkedin != null : "fx:id=\"txtLinkedin\" was not injected: check your FXML file 'PerfilAluno.fxml'.";
        assert txtSenha != null : "fx:id=\"txtSenha\" was not injected: check your FXML file 'PerfilAluno.fxml'.";

    }
    public void sair(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event);
    }
    public void perfilAluno (ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/PerfilAluno.fxml", "Perfil Aluno", event);
    }
    public void secaoGeral(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/SecaoAluno.fxml", "Secao Geral", event);
    }
    public void tgGeral(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/TGAluno.fxml", "TG Aluno", event);
    }
    public void telaInicial(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/AtualizacoesAluno.fxml", "Tela Inicial", event);
    }

}
