package org.api.trabalhodegraduacao.controller.usuario.aluno;

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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
import org.api.trabalhodegraduacao.entities.Usuario;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;
import org.api.trabalhodegraduacao.utils.GerenciadorImagens;

public class PerfilAlunoController {

    private boolean isEditMode = false;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Usuario usuarioLogado;
    private UsuarioDAO usuarioDAO;

    @FXML private Button bt_EditarSalvar;
    @FXML private Button bt_Sair;
    @FXML private Button bt_TrocarFotoPerfil;
    @FXML private ImageView imgVwFotoPerfil;

    @FXML private Label lbl_NomeUsuario;
    @FXML private Label lblEmailCadastrado;
    @FXML private Label lblOrientador;
    @FXML private Label lblCurso;
    @FXML private Label lblDataNascimento;
    @FXML private Label lblLinkedin;
    @FXML private Label lblGitHub;
    @FXML private Label lblSenha;

    @FXML private TextField txtCurso;
    @FXML private DatePicker dpDataNascimento;
    @FXML private TextField txtLinkedin;
    @FXML private TextField txtGitHub;
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

                    Image imagem = null;
                    String caminhoFoto = usuarioLogado.getFotoPerfil();

                    if (caminhoFoto != null && !caminhoFoto.isEmpty()) {
                        if (caminhoFoto.startsWith("file:") || caminhoFoto.startsWith("http")) {
                            imagem = new Image(caminhoFoto);
                        } else {
                            File arquivoImagem = new File(caminhoFoto);
                            if (arquivoImagem.exists()) {
                                imagem = new Image(arquivoImagem.toURI().toString());
                            } else {
                                imagem = new Image(getClass().getResourceAsStream("/org/api/trabalhodegraduacao/images/imgFotoPerfil.png"));
                            }
                        }
                     } else {
                        imagem = new Image(getClass().getResourceAsStream("/org/api/trabalhodegraduacao/images/imgFotoPerfil.png"));
                    }

                    configurarImagemRedonda(imgVwFotoPerfil, imagem);

                }
            } catch (SQLException e) {
                System.err.println("Erro ao carregar perfil: " + e.getMessage());
                e.printStackTrace();
            }
        }
        setViewMode(true);
    }
    private void preencherLabelsComDados() {
        lbl_NomeUsuario.setText(usuarioLogado.getNomeCompleto());
        lblEmailCadastrado.setText(usuarioLogado.getEmailCadastrado());

        String nomeOrientador = usuarioLogado.getNomeOrientador();
        lblOrientador.setText((nomeOrientador != null && !nomeOrientador.isEmpty()) ? nomeOrientador : "(Não definido)");

        lblCurso.setText(getTextoOuPadrao(usuarioLogado.getCurso()));
        lblLinkedin.setText(getTextoOuPadrao(usuarioLogado.getLinkedin()));
        lblGitHub.setText(getTextoOuPadrao(usuarioLogado.getGitHub()));
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
        lblLinkedin.setVisible(viewMode);
        lblGitHub.setVisible(viewMode);
        lblSenha.setVisible(viewMode);

        lblCurso.setManaged(viewMode);
        lblDataNascimento.setManaged(viewMode);
        lblLinkedin.setManaged(viewMode);
        lblGitHub.setManaged(viewMode);
        lblSenha.setManaged(viewMode);

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

        bt_TrocarFotoPerfil.setVisible(!viewMode);
        bt_TrocarFotoPerfil.setManaged(!viewMode);
    }

    @FXML
    void onToggleEditSave(ActionEvent event) {
        if (isEditMode) {
            try {
                usuarioLogado.setCurso(txtCurso.getText());
                usuarioLogado.setLinkedin(txtLinkedin.getText());
                usuarioLogado.setGitHub(txtGitHub.getText());
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
            txtCurso.setText(usuarioLogado.getCurso());
            txtLinkedin.setText(usuarioLogado.getLinkedin());
            txtGitHub.setText(usuarioLogado.getGitHub());
            dpDataNascimento.setValue(usuarioLogado.getDataNascimento());
            txtSenha.clear();

            setViewMode(false);
            bt_EditarSalvar.setText("Salvar");
            bt_EditarSalvar.getStyleClass().setAll("profile-button-primary");
            isEditMode = true;
        }
    }

    @FXML
    void trocarFotoPerfil(ActionEvent event) {
        System.out.println("Botão Trocar Foto clicado.");
        Stage stage = (Stage) bt_TrocarFotoPerfil.getScene().getWindow();

        String nomeSeguro = usuarioLogado.getEmailCadastrado().replaceAll("[^a-zA-Z0-9.-]", "_");

        String novoCaminho = GerenciadorImagens.selecionarESalvarNovaFoto(stage, nomeSeguro);

        if (novoCaminho != null) {
            usuarioLogado.setFotoPerfil(novoCaminho);

            File arquivoImagem = new File(novoCaminho);
            Image novaImagem = new Image(arquivoImagem.toURI().toString());

            configurarImagemRedonda(imgVwFotoPerfil, novaImagem);

            try {
                usuarioDAO.atualizar(usuarioLogado);
                System.out.println("Foto atualizada no banco!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void configurarImagemRedonda(ImageView imageView, Image imagem) {
        if (imagem == null) return;

        imageView.setImage(imagem);

        double w = imagem.getWidth();
        double h = imagem.getHeight();
        double tamanhoQuadrado = Math.min(w, h);
        double x = (w - tamanhoQuadrado) / 2;
        double y = (h - tamanhoQuadrado) / 2;

        imageView.setViewport(new Rectangle2D(x, y, tamanhoQuadrado, tamanhoQuadrado));
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        double raio = imageView.getFitWidth() / 2;
        Circle clip = new Circle(raio, raio, raio);
        imageView.setClip(clip);
    }

    @FXML public void sair(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event); }
    @FXML public void perfilAluno(ActionEvent event) { System.out.println("Já está na tela."); }
    @FXML public void secaoGeral(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/SecaoAluno.fxml", "Secao Geral", event); }
    @FXML public void tgGeral(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/TGAluno.fxml", "TG Aluno", event); }
    @FXML public void telaInicial(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/AtualizacoesAluno.fxml", "Tela Inicial", event); }
    @FXML void devolutivasGeral(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/DevolutivasAluno.fxml", "TGRS - Devolutivas", event); }
}