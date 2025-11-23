package org.api.trabalhodegraduacao.controller.usuario.professor;

import java.io.File;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
import org.api.trabalhodegraduacao.entities.Usuario;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;

public class PerfilProfessorController {

    private boolean isEditMode = false;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Usuario usuarioLogado;
    private UsuarioDAO usuarioDAO;

    @FXML private Button bt_EditarSalvar;
    @FXML private Button bt_TrocarFotoPerfil;
    @FXML private ImageView imgVwFotoPerfil;
    @FXML private Button bt_Gestao;

    @FXML private Label lblNome;
    @FXML private Label lblEmail;
    @FXML private Label lblDataNascimento;
    @FXML private Label lblSenha;

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
                    carregarFotoPerfil();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        setViewMode(true);
        verificarPermissaoAdmin();
    }

    private void preencherLabelsComDados() {
        lblNome.setText(usuarioLogado.getNomeCompleto());
        lblEmail.setText(usuarioLogado.getEmailCadastrado());


        lblSenha.setText("**********");

        if (usuarioLogado.getDataNascimento() != null) {
            lblDataNascimento.setText(usuarioLogado.getDataNascimento().format(dateFormatter));
        } else {
            lblDataNascimento.setText("(Não informado)");
        }
    }

    private void setViewMode(boolean viewMode) {
        lblDataNascimento.setVisible(viewMode);
        lblSenha.setVisible(viewMode);

        lblDataNascimento.setManaged(viewMode);
        lblSenha.setManaged(viewMode);

        dpDataNascimento.setVisible(!viewMode);
        txtSenha.setVisible(!viewMode);

        dpDataNascimento.setManaged(!viewMode);
        txtSenha.setManaged(!viewMode);

        bt_TrocarFotoPerfil.setVisible(!viewMode);
        bt_TrocarFotoPerfil.setManaged(!viewMode);
    }

    @FXML
    void onToggleEditSave(ActionEvent event) {
        if (isEditMode) {
            try {
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

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            txtSenha.clear();
            dpDataNascimento.setValue(usuarioLogado.getDataNascimento());

            setViewMode(false);
            bt_EditarSalvar.setText("Salvar");
            bt_EditarSalvar.getStyleClass().setAll("profile-button-primary");
            isEditMode = true;
        }
    }

    private void carregarFotoPerfil() {
        if (imgVwFotoPerfil == null) return;
        Image imagem = null;
        String caminho = usuarioLogado.getFotoPerfil();
        if (caminho != null && !caminho.isEmpty()) {
            try {
                if (caminho.startsWith("file:") || caminho.startsWith("http")) {
                    imagem = new Image(caminho);
                } else {
                    File arquivo = new File(caminho);
                    if (arquivo.exists()) imagem = new Image(arquivo.toURI().toString());
                }
            } catch (Exception e) { }
        }
        if (imagem == null) imagem = new Image(getClass().getResourceAsStream("/org/api/trabalhodegraduacao/images/imgFotoPerfil.png"));
        configurarImagemRedonda(imgVwFotoPerfil, imagem);
    }

    private void configurarImagemRedonda(ImageView imageView, Image imagem) {
        if (imagem == null) return;
        imageView.setImage(imagem);
        double w = imagem.getWidth(); double h = imagem.getHeight();
        if (w <= 0 || h <= 0) return;
        double tamanho = Math.min(w, h);
        double x = (w - tamanho) / 2; double y = (h - tamanho) / 2;
        imageView.setViewport(new Rectangle2D(x, y, tamanho, tamanho));
        imageView.setPreserveRatio(true); imageView.setSmooth(true);
        double raio = imageView.getFitWidth() / 2;
        imageView.setClip(new Circle(raio, raio, raio));
    }
    private void verificarPermissaoAdmin() {
        SessaoUsuario sessao = SessaoUsuario.getInstance();
        if (sessao.isLogado()) {
            UsuarioDAO dao = new UsuarioDAO();
            var funcao = dao.buscarFuncaoProfessor(sessao.getEmail());

            if (funcao.gerenciador) {
                if (bt_Gestao != null) {
                    bt_Gestao.setVisible(true);
                    bt_Gestao.setManaged(true);
                    bt_Gestao.setStyle("-fx-text-fill: #a7d1ed;"); // Opcional: cor de destaque
                }
            }
        }
    }

    @FXML
    void acessarGestao(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/GestaoCursos.fxml", "Gestão Administrativa", event);
    }

    @FXML void trocarFoto(ActionEvent event) { System.out.println("Botão Trocar Foto clicado."); }
    @FXML void sair(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event); }
    @FXML void perfilProfessor(ActionEvent event) { System.out.println("Já está na tela."); }
    @FXML void alunos(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/Alunos.fxml", "Alunos", event); }
    @FXML void telaInicial(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/AtualizacoesProfessor.fxml", "Tela Inicial", event); }
}