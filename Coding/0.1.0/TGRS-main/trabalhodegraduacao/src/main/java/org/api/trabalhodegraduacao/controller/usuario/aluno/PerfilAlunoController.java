package org.api.trabalhodegraduacao.controller.usuario.aluno;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField; // Importação corrigida/adicionada
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

    // --- VARIÁVEIS ADICIONADAS ---
    @FXML
    private Button bt_devolutivas_geral; // Botão que faltava

    @FXML
    private TextField txtCurso; // Campo que faltava

    @FXML
    private TextField txtHistorico; // Campo que faltava

    @FXML
    private TextField txtMotivacao; // Campo que faltava
    // --- FIM DAS VARIÁVEIS ADICIONADAS ---

    @FXML
    private Label lblOrientador; // Variável correta (Label)

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
    private PasswordField txtSenha; // Tipo corrigido de TextField para PasswordField

    @FXML
    void atualizar(ActionEvent event) {
        // Lógica para salvar o perfil
    }

    @FXML
    void nomeUsuario(MouseEvent event) {
        // Lógica do onDragDetected
    }

    @FXML
    void trocarFoto(ActionEvent event) {
        // Lógica para trocar foto
    }

    // --- MÉTODO INITIALIZE CORRIGIDO ---
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
        assert imgVwFotoPerfil != null : "fx:id=\"imgVwFotoPerfil\" was not injected: check your FXML file 'PerfilAluno.fxml'.";
        assert lblEmailCadastrado != null : "fx:id=\"lblEmailCadastrado\" was not injected: check your FXML file 'PerfilAluno.fxml'.";
        assert lbl_NomeUsuario != null : "fx:id=\"lbl_NomeUsuario\" was not injected: check your FXML file 'PerfilAluno.fxml'.";
        assert txtGitHub != null : "fx:id=\"txtGitHub\" was not injected: check your FXML file 'PerfilAluno.fxml'.";
        assert txtLinkedin != null : "fx:id=\"txtLinkedin\" was not injected: check your FXML file 'PerfilAluno.fxml'.";
        assert txtSenha != null : "fx:id=\"txtSenha\" was not injected: check your FXML file 'PerfilAluno.fxml'.";

        // Asserts corrigidos e adicionados
        assert lblOrientador != null : "fx:id=\"lblOrientador\" was not injected: check your FXML file 'PerfilAluno.fxml'."; // Corrigido
        assert bt_devolutivas_geral != null : "fx:id=\"bt_devolutivas_geral\" was not injected: check your FXML file 'PerfilAluno.fxml'."; // Adicionado
        assert txtCurso != null : "fx:id=\"txtCurso\" was not injected: check your FXML file 'PerfilAluno.fxml'."; // Adicionado
        assert txtHistorico != null : "fx:id=\"txtHistorico\" was not injected: check your FXML file 'PerfilAluno.fxml'."; // Adicionado
        assert txtMotivacao != null : "fx:id=\"txtMotivacao\" was not injected: check your FXML file 'PerfilAluno.fxml'."; // Adicionado
    }

    // --- MÉTODOS DE NAVEGAÇÃO ---

    @FXML
    public void sair(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event);
    }

    @FXML
    public void perfilAluno (ActionEvent event) {
        // Já está nesta tela
        System.out.println("Já está na tela de Perfil.");
    }

    @FXML
    public void secaoGeral(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/SecaoAluno.fxml", "Secao Geral", event);
    }

    @FXML
    public void tgGeral(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/TGAluno.fxml", "TG Aluno", event);
    }

    @FXML
    public void telaInicial(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/AtualizacoesAluno.fxml", "Tela Inicial", event);
    }

    @FXML
    void devolutivasGeral(ActionEvent event) {
        String fxmlPath = "/org/api/trabalhodegraduacao/view/usuario/aluno/DevolutivasAluno.fxml";
        String title = "TGRS - Devolutivas";
        Application.carregarNovaCena(fxmlPath, title, event);
    }
}