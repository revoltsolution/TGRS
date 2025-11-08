package org.api.trabalhodegraduacao.controller.usuario.professor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.api.trabalhodegraduacao.Application; // Verifique a importação
// Importe seu modelo de Professor
// import org.api.trabalhodegraduacao.model.Professor;
// import org.api.trabalhodegraduacao.service.SessaoService;

public class PerfilProfessorController {

    // --- Variável de Estado ---
    private boolean isEditMode = false;

    // --- Componentes FXML ---
    @FXML private Button bt_EditarSalvar;
    @FXML private Button bt_Sair;
    @FXML private Button bt_TrocarFotoPerfil;
    @FXML private Button bt_alunos_geral;
    @FXML private Button bt_devolutivas_geral;
    @FXML private Button bt_perfil_geral;
    @FXML private Button bt_tela_inicial;
    @FXML private ImageView imgVwFotoPerfil;

    // Campos de Dados (Não Editáveis)
    @FXML private Label lblNome;
    @FXML private Label lblEmail;

    // Campos de Dados (Editáveis - Labels de Visualização)
    @FXML private Label lblHistorico;
    @FXML private Label lblLinkedin;
    @FXML private Label lblGitHub;
    @FXML private Label lblSenha;

    // Campos de Dados (Editáveis - Campos de Edição)
    @FXML private TextField txtHistorico;
    @FXML private TextField txtLinkedin;
    @FXML private TextField txtGitHub;
    @FXML private PasswordField txtSenha;


    @FXML
    void initialize() {
        loadData();
        setViewMode(true); // true = View Mode
    }

    /**
     * Carrega os dados do professor logado e preenche os campos.
     */
    private void loadData() {
        // (Aqui você puxa os dados do banco e preenche os LABELS)
        // Professor prof = SessaoService.getProfessorLogado();
        // if (prof == null) return;

        // lblNome.setText(prof.getNome());
        // lblEmail.setText(prof.getEmail());
        // lblHistorico.setText(prof.getHistorico());
        // lblLinkedin.setText(prof.getLinkedin());
        // lblGitHub.setText(prof.getGithub());


    }

    /**
     * Alterna a interface entre o modo de visualização (Labels) e o modo de edição (TextFields).
     */
    private void setViewMode(boolean viewMode) {
        // Alterna os Labels
        lblHistorico.setVisible(viewMode);
        lblLinkedin.setVisible(viewMode);
        lblGitHub.setVisible(viewMode);
        lblSenha.setVisible(viewMode);

        lblHistorico.setManaged(viewMode);
        lblLinkedin.setManaged(viewMode);
        lblGitHub.setManaged(viewMode);
        lblSenha.setManaged(viewMode);

        // Alterna os Campos de Edição
        txtHistorico.setVisible(!viewMode);
        txtLinkedin.setVisible(!viewMode);
        txtGitHub.setVisible(!viewMode);
        txtSenha.setVisible(!viewMode);

        txtHistorico.setManaged(!viewMode);
        txtLinkedin.setManaged(!viewMode);
        txtGitHub.setManaged(!viewMode);
        txtSenha.setManaged(!viewMode);

        // Alterna o botão de trocar foto
        bt_TrocarFotoPerfil.setVisible(!viewMode);
        bt_TrocarFotoPerfil.setManaged(!viewMode);
    }

    /**
     * Chamado pelo único botão "Editar Perfil" / "Salvar".
     */
    @FXML
    void onToggleEditSave(ActionEvent event) {
        if (isEditMode) {
            // --- MODO SALVAR ---
            // 1. Salvar os dados (lógica do onSalvar)
            // (Aqui você pega os dados dos TextFields e salva no banco)
            // Ex: prof.setHistorico(txtHistorico.getText());

            // 2. Atualizar os Labels com os novos dados
            lblHistorico.setText(txtHistorico.getText());
            lblLinkedin.setText(txtLinkedin.getText());
            lblGitHub.setText(txtGitHub.getText());

            // 3. Trocar para o modo de visualização
            setViewMode(true);

            // 4. Atualizar o botão
            bt_EditarSalvar.setText("Editar Perfil");
            bt_EditarSalvar.getStyleClass().setAll("profile-button-secondary");

            // 5. Atualizar estado
            isEditMode = false;

        } else {
            // --- MODO EDITAR ---
            // 1. Copiar dados dos Labels para os TextFields (lógica do onEditar)
            txtHistorico.setText(lblHistorico.getText());
            txtLinkedin.setText(lblLinkedin.getText());
            txtGitHub.setText(lblGitHub.getText());
            txtSenha.clear(); // Limpa a senha por segurança

            // 2. Trocar para o modo de edição
            setViewMode(false);

            // 3. Atualizar o botão
            bt_EditarSalvar.setText("Salvar");
            bt_EditarSalvar.getStyleClass().setAll("profile-button-primary");

            // 4. Atualizar estado
            isEditMode = true;
        }
    }

    @FXML
    void trocarFotoPerfil(ActionEvent event) {
        // Lógica para trocar foto
        System.out.println("Botão Trocar Foto clicado.");
    }

    // --- MÉTODOS DE NAVEGAÇÃO ---

    @FXML
    void sair(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event);
    }

    @FXML
    void perfilProfessor(ActionEvent event) {
        System.out.println("Já está na tela de Perfil.");
    }

    @FXML
    void alunos(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/Alunos.fxml", "Alunos", event);
    }

    @FXML
    void devolutivas(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/Historico.fxml", "Devolutivas", event);
    }

    @FXML
    void telaInicial(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/AtualizacoesProfessor.fxml", "Tela Inicial", event);
    }
}