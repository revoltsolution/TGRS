package org.api.trabalhodegraduacao.controller.usuario.professor;

import java.net.URL;
import java.sql.SQLException;
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
import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
import org.api.trabalhodegraduacao.entities.Usuario;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;

public class PerfilProfessorController {

    // --- Variável de Estado ---
    private boolean isEditMode = false;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // --- Variáveis de Serviço e Dados ---
    private Usuario usuarioLogado;
    private UsuarioDAO usuarioDAO;

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

    // Campos de Dados (Editáveis - Labels)
    @FXML private Label lblCurso;
    @FXML private Label lblDataNascimento;
    @FXML private Label lblSenha;

    // Campos de Dados (Editáveis - Campos)
    @FXML private TextField txtCurso;
    @FXML private DatePicker dpDataNascimento;
    @FXML private PasswordField txtSenha;


    @FXML
    void initialize() {
        this.usuarioDAO = new UsuarioDAO();
        SessaoUsuario sessao = SessaoUsuario.getInstance();

        if (sessao.isLogado()) {
            try {
                this.usuarioLogado = usuarioDAO.exibirPerfil(sessao.getEmail());
                if (this.usuarioLogado != null) {
                    preencherLabelsComDados();
                } else {
                    System.err.println("Usuário da sessão (Professor) não encontrado no banco.");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao carregar perfil do professor: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("Erro: Nenhum usuário na sessão.");
        }
        setViewMode(true);
    }

    private void preencherLabelsComDados() {
        lblNome.setText(usuarioLogado.getNomeCompleto());
        lblEmail.setText(usuarioLogado.getEmailCadastrado());

        lblCurso.setText(getTextoOuPadrao(usuarioLogado.getCurso()));
        lblSenha.setText("**********");

        if (usuarioLogado.getDataNascimento() != null) {
            LocalDate dataNasc = usuarioLogado.getDataNascimento();
            lblDataNascimento.setText(dataNasc.format(dateFormatter));
        } else {
            lblDataNascimento.setText("(Não informado)");
        }
    }

    private String getTextoOuPadrao(String texto) {
        return (texto != null && !texto.trim().isEmpty()) ? texto : "(Não informado)";
    }

    private void setViewMode(boolean viewMode) {
        // Alterna Labels
        lblCurso.setVisible(viewMode);
        lblDataNascimento.setVisible(viewMode);
        lblSenha.setVisible(viewMode);

        lblCurso.setManaged(viewMode);
        lblDataNascimento.setManaged(viewMode);
        lblSenha.setManaged(viewMode);

        // Alterna Campos de Edição
        txtCurso.setVisible(!viewMode);
        dpDataNascimento.setVisible(!viewMode);
        txtSenha.setVisible(!viewMode);

        txtCurso.setManaged(!viewMode);
        dpDataNascimento.setManaged(!viewMode);
        txtSenha.setManaged(!viewMode);

        bt_TrocarFotoPerfil.setVisible(!viewMode);
        bt_TrocarFotoPerfil.setManaged(!viewMode);
    }

    @FXML
    void onToggleEditSave(ActionEvent event) {
        if (isEditMode) {
            // --- MODO SALVAR ---
            try {
                // 1. Atualizar o objeto 'usuarioLogado'
                usuarioLogado.setCurso(txtCurso.getText());
                usuarioLogado.setDataNascimento(dpDataNascimento.getValue());

                String novaSenha = txtSenha.getText();
                if (novaSenha != null && !novaSenha.trim().isEmpty()) {
                    usuarioLogado.setSenha(novaSenha);
                }

                // 2. Salvar no banco (O DAO ainda tentará salvar LinkedIn/GitHub, veja abaixo)
                usuarioDAO.atualizar(this.usuarioLogado);

                // 3. Atualizar os Labels
                preencherLabelsComDados();

                // 4. Trocar para o modo de visualização
                setViewMode(true);
                bt_EditarSalvar.setText("Editar Perfil");
                bt_EditarSalvar.getStyleClass().setAll("profile-button-secondary");
                isEditMode = false;

            } catch (SQLException e) {
                System.err.println("Erro ao salvar perfil: " + e.getMessage());
                e.printStackTrace();
            }

        } else {
            // --- MODO EDITAR ---
            // 1. Copiar dados do objeto para os TextFields
            txtCurso.setText(usuarioLogado.getCurso());
            txtSenha.clear();
            dpDataNascimento.setValue(usuarioLogado.getDataNascimento());

            // 2. Trocar para o modo de edição
            setViewMode(false);
            bt_EditarSalvar.setText("Salvar");
            bt_EditarSalvar.getStyleClass().setAll("profile-button-primary");
            isEditMode = true;
        }
    }

    @FXML
    void trocarFotoPerfil(ActionEvent event) {
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