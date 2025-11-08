package org.api.trabalhodegraduacao.controller.usuario.aluno;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.api.trabalhodegraduacao.Application;
// import org.api.trabalhodegraduacao.model.Aluno;
// import org.api.trabalhodegraduacao.service.SessaoService;

public class PerfilAlunoController {

    // --- Variável de Estado ---
    private boolean isEditMode = false;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    // --- Componentes FXML ---
    @FXML private Button bt_EditarSalvar;
    @FXML private Button bt_Sair;
    @FXML private Button bt_TrocarFotoPerfil;
    @FXML private Button bt_devolutivas_geral;
    @FXML private Button bt_perfil_geral;
    @FXML private Button bt_secao_geral;
    @FXML private Button bt_tela_inicial;
    @FXML private Button bt_tg_geral;
    @FXML private ImageView imgVwFotoPerfil;

    // Campos de Dados (Não Editáveis)
    @FXML private Label lbl_NomeUsuario;
    @FXML private Label lblEmailCadastrado;
    @FXML private Label lblOrientador;

    // Campos de Dados (Editáveis - Labels de Visualização)
    @FXML private Label lblCurso;
    @FXML private Label lblDataNascimento; // NOVO
    @FXML private Label lblLinkedin;
    @FXML private Label lblGitHub;
    @FXML private Label lblSenha;

    // Campos de Dados (Editáveis - Campos de Edição)
    @FXML private TextField txtCurso;
    @FXML private DatePicker dpDataNascimento; // NOVO
    @FXML private TextField txtLinkedin;
    @FXML private TextField txtGitHub;
    @FXML private PasswordField txtSenha;


    @FXML
    void initialize() {
        loadData();
        setViewMode(true); // true = View Mode
    }

    private void loadData() {
        // (Aqui você puxa os dados do banco e preenche os LABELS)
        // Aluno aluno = SessaoService.getAlunoLogado();
        // ...
        // lblCurso.setText(aluno.getCurso());
        // lblLinkedin.setText(aluno.getLinkedin());
        // ...
        // if (aluno.getDataNascimento() != null) {
        //    lblDataNascimento.setText(aluno.getDataNascimento().format(dateFormatter));
        // } else {
        //    lblDataNascimento.setText("(Não informado)");
        // }


    }

    private void setViewMode(boolean viewMode) {
        // Alterna os Labels
        lblCurso.setVisible(viewMode);
        lblDataNascimento.setVisible(viewMode);
        lblLinkedin.setVisible(viewMode);
        lblGitHub.setVisible(viewMode);
        lblSenha.setVisible(viewMode);

        lblCurso.setManaged(viewMode);
        lblDataNascimento.setManaged(viewMode);
        lblLinkedin.setManaged(viewMode);
        lblGitHub.setManaged(viewMode);
        lblSenha.setManaged(viewMode);

        // Alterna os Campos de Edição
        txtCurso.setVisible(!viewMode);
        dpDataNascimento.setVisible(!viewMode);
        txtLinkedin.setVisible(!viewMode);
        txtGitHub.setVisible(!viewMode);
        txtSenha.setVisible(!viewMode);

        txtCurso.setManaged(!viewMode);
        dpDataNascimento.setManaged(!viewMode);
        txtLinkedin.setManaged(!viewMode);
        txtGitHub.setManaged(!viewMode);
        txtSenha.setManaged(!viewMode);

        // Alterna o botão de trocar foto
        bt_TrocarFotoPerfil.setVisible(!viewMode);
        bt_TrocarFotoPerfil.setManaged(!viewMode);
    }

    @FXML
    void onToggleEditSave(ActionEvent event) {
        if (isEditMode) {
            // --- MODO SALVAR ---
            // 1. Salvar os dados (lógica do onSalvar)
            // (Aqui você pega os dados dos TextFields e salva no banco)
            // Ex: aluno.setCurso(txtCurso.getText());
            //     aluno.setDataNascimento(dpDataNascimento.getValue());

            // 2. Atualizar os Labels com os novos dados
            lblCurso.setText(txtCurso.getText());
            lblLinkedin.setText(txtLinkedin.getText());
            lblGitHub.setText(txtGitHub.getText());

            LocalDate dataSelecionada = dpDataNascimento.getValue();
            if (dataSelecionada != null) {
                lblDataNascimento.setText(dataSelecionada.format(dateFormatter));
            } else {
                lblDataNascimento.setText("(Não informado)");
            }

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
            txtCurso.setText(lblCurso.getText());
            txtLinkedin.setText(lblLinkedin.getText());
            txtGitHub.setText(lblGitHub.getText());
            txtSenha.clear();

            try {
                LocalDate dataNascimento = LocalDate.parse(lblDataNascimento.getText(), dateFormatter);
                dpDataNascimento.setValue(dataNascimento);
            } catch (Exception e) {
                dpDataNascimento.setValue(null); // Limpa se o texto não for uma data válida
            }

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
    void trocarFoto(ActionEvent event) {
        System.out.println("Botão Trocar Foto clicado.");
    }

    @FXML
    void nomeUsuario(MouseEvent event) {
        // Lógica do onDragDetected
    }

    // --- MÉTODOS DE NAVEGAÇÃO ---

    @FXML
    public void sair(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event);
    }

    @FXML
    public void perfilAluno (ActionEvent event) {
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