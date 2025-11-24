package org.api.trabalhodegraduacao.controller.usuario.aluno;

import java.io.File;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
import org.api.trabalhodegraduacao.entities.Usuario;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;
import org.api.trabalhodegraduacao.dao.CursoDAO;
import org.api.trabalhodegraduacao.entities.Curso;

public class PerfilAlunoController {


    private boolean isEditMode = false;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Usuario usuarioLogado;
    private UsuarioDAO usuarioDAO;

    @FXML private Button bt_Sair, bt_devolutivas_geral, bt_perfil_geral, bt_secao_geral, bt_tela_inicial, bt_tg_geral;

    @FXML private Button bt_EditarSalvar;
    @FXML private Button bt_TrocarFotoPerfil;
    @FXML private ImageView imgVwFotoPerfil;

    @FXML private Label lblEmailCadastrado;
    @FXML private Label lblCurso;
    @FXML private Label lblDataNascimento;
    @FXML private Label lblLinkedin;
    @FXML private Label lblGitHub;
    @FXML private Label lblSenha;
    @FXML private Label lbl_NomeUsuario;
    @FXML private Label lblOrientador;

    @FXML private ComboBox<String> cbCurso;

    @FXML private DatePicker dpDataNascimento;
    @FXML private TextField txtLinkedin;
    @FXML private TextField txtGitHub;
    @FXML private PasswordField txtSenha;

    @FXML
    public void initialize() {
        this.usuarioDAO = new UsuarioDAO();
        CursoDAO cursoDAO = new CursoDAO();

        if (cbCurso != null) {
            java.util.List<Curso> cursosDoBanco = cursoDAO.listarTodos();

            javafx.collections.ObservableList<String> nomes = javafx.collections.FXCollections.observableArrayList();
            for (Curso c : cursosDoBanco) {
                nomes.add(c.getNome());
            }
            cbCurso.setItems(nomes);
        }

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
    }

    private void preencherLabelsComDados() {
        lbl_NomeUsuario.setText(usuarioLogado.getNomeCompleto());
        lblEmailCadastrado.setText(usuarioLogado.getEmailCadastrado());
        lblOrientador.setText(usuarioLogado.getNomeOrientador() != null ? usuarioLogado.getNomeOrientador() : "Não vinculado");

        String curso = usuarioLogado.getCurso();
        lblCurso.setText((curso != null && !curso.isEmpty()) ? curso : "(Não informado)");

        lblLinkedin.setText((usuarioLogado.getLinkedin() != null) ? usuarioLogado.getLinkedin() : "");
        lblGitHub.setText((usuarioLogado.getGitHub() != null) ? usuarioLogado.getGitHub() : "");
        lblSenha.setText("**********");

        if (usuarioLogado.getDataNascimento() != null) {
            lblDataNascimento.setText(usuarioLogado.getDataNascimento().format(dateFormatter));
        } else {
            lblDataNascimento.setText("(Não informado)");
        }
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

        if(cbCurso != null) {
            cbCurso.setVisible(!viewMode);
            cbCurso.setManaged(!viewMode);
        }

        dpDataNascimento.setVisible(!viewMode);
        txtLinkedin.setVisible(!viewMode);
        txtGitHub.setVisible(!viewMode);
        txtSenha.setVisible(!viewMode);

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
                if (cbCurso != null && cbCurso.getValue() != null) {
                    usuarioLogado.setCurso(cbCurso.getValue());
                }

                usuarioLogado.setDataNascimento(dpDataNascimento.getValue());
                usuarioLogado.setLinkedin(txtLinkedin.getText());
                usuarioLogado.setGitHub(txtGitHub.getText());

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
            if (cbCurso != null) {
                cbCurso.setValue(usuarioLogado.getCurso());
            }

            txtLinkedin.setText(usuarioLogado.getLinkedin());
            txtGitHub.setText(usuarioLogado.getGitHub());
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
        if (imagem == null || imageView == null) return;
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

    @FXML void trocarFotoPerfil(ActionEvent event) { System.out.println("Funcionalidade de trocar foto aqui."); }
    @FXML void sair(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event); }
    @FXML void perfilAluno (ActionEvent event) { System.out.println("Já está no Perfil."); }
    @FXML void secaoGeral(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/SecaoAluno.fxml", "Seção", event); }
    @FXML void tgGeral(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/TGAluno.fxml", "TG Aluno", event); }
    @FXML void telaInicial(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/AtualizacoesAluno.fxml", "Tela Inicial", event); }
    @FXML void devolutivasGeral(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/DevolutivasAluno.fxml", "TGRS - Devolutivas", event); }
}