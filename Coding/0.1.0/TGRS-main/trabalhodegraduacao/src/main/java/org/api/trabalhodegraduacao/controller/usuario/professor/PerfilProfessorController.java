package org.api.trabalhodegraduacao.controller.usuario.professor;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
import org.api.trabalhodegraduacao.entities.Usuario;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;
import org.api.trabalhodegraduacao.utils.GerenciadorImagens;

public class PerfilProfessorController {

    private boolean isEditMode = false;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private Usuario usuarioLogado;
    private UsuarioDAO usuarioDAO;

    @FXML private Button bt_EditarSalvar;
    @FXML private Button bt_TrocarFotoPerfil;
    @FXML private ImageView imgVwFotoPerfil;

    // Labels
    @FXML private Label lblNome;
    @FXML private Label lblEmail;
    @FXML private Label lblCurso; // Ou Departamento
    @FXML private Label lblDataNascimento;
    @FXML private Label lblSenha;

    // Inputs
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
                    GerenciadorImagens.configurarImagemPerfil(imgVwFotoPerfil, usuarioLogado.getFotoPerfil());

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        setViewMode(true);
    }

    private void preencherLabelsComDados() {
        lblNome.setText(usuarioLogado.getNomeCompleto());
        lblEmail.setText(usuarioLogado.getEmailCadastrado());
        lblCurso.setText(getTextoOuPadrao(usuarioLogado.getCurso()));
        lblSenha.setText("**********");

        if (usuarioLogado.getDataNascimento() != null) {
            lblDataNascimento.setText(usuarioLogado.getDataNascimento().format(dateFormatter));
        } else {
            lblDataNascimento.setText("(Não informado)");
        }
    }

    private String getTextoOuPadrao(String texto) {
        return (texto != null && !texto.trim().isEmpty()) ? texto : "(Não informado)";
    }

    private void setViewMode(boolean viewMode) {
        lblCurso.setVisible(viewMode);
        lblDataNascimento.setVisible(viewMode);
        lblSenha.setVisible(viewMode);
        lblCurso.setManaged(viewMode);
        lblDataNascimento.setManaged(viewMode);
        lblSenha.setManaged(viewMode);

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
            try {
                usuarioLogado.setCurso(txtCurso.getText());
                usuarioLogado.setDataNascimento(dpDataNascimento.getValue());

                String novaSenha = txtSenha.getText();
                if (novaSenha != null && !novaSenha.trim().isEmpty()) {
                    usuarioLogado.setSenha(novaSenha);
                }
                usuarioDAO.atualizar(this.usuarioLogado);
                preencherLabelsComDados();

                setViewMode(true);
                bt_EditarSalvar.setText("Editar Perfil");
                bt_EditarSalvar.getStyleClass().setAll("profile-button-secondary");
                isEditMode = false;
            } catch (SQLException e) { e.printStackTrace(); }
        } else {
            txtCurso.setText(usuarioLogado.getCurso());
            dpDataNascimento.setValue(usuarioLogado.getDataNascimento());
            txtSenha.clear();

            setViewMode(false);
            bt_EditarSalvar.setText("Salvar");
            bt_EditarSalvar.getStyleClass().setAll("profile-button-primary");
            isEditMode = true;
        }
    }

    @FXML void trocarFoto(ActionEvent event) {
        System.out.println("Botão Foto Professor.");

        Stage stage = (Stage) bt_TrocarFotoPerfil.getScene().getWindow();

        String nomeSeguro = usuarioLogado.getEmailCadastrado().replaceAll("[^a-zA-Z0-9.-]", "_");

        String novoCaminho = GerenciadorImagens.selecionarESalvarNovaFoto(stage, nomeSeguro);

        if (novoCaminho != null) {
            usuarioLogado.setFotoPerfil(novoCaminho);

            GerenciadorImagens.configurarImagemPerfil(imgVwFotoPerfil, novoCaminho);

            try {
                usuarioDAO.atualizar(usuarioLogado);
                System.out.println("Foto atualizada no banco!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Navegação
    @FXML void sair(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event); }
    @FXML void perfilProfessor(ActionEvent event) { System.out.println("Já está."); }
    @FXML void alunos(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/Alunos.fxml", "Alunos", event); }
    @FXML void devolutivas(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/Historico.fxml", "Devolutivas", event); }
    @FXML void telaInicial(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/AtualizacoesProfessor.fxml", "Tela Inicial", event); }
}