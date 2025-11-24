package org.api.trabalhodegraduacao.controller.usuario.professor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Circle;
import java.io.File;
import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.dao.NotificacaoDAO;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
import org.api.trabalhodegraduacao.entities.Notificacao;
import org.api.trabalhodegraduacao.entities.Usuario;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;

import java.sql.SQLException;
import java.util.List;

public class AtualizacaoProfessorController {

    @FXML private Button bt_Gestao;
    @FXML private ListView<Notificacao> listaNotificacoes;
    @FXML private ImageView imgVwFotoPerfil;

    private NotificacaoDAO notificacaoDAO;
    private UsuarioDAO usuarioDAO;

    @FXML
    public void initialize() {
        this.notificacaoDAO = new NotificacaoDAO();
        this.usuarioDAO = new UsuarioDAO();


        Usuario professorLogado = carregarDadosProfessor();

        if (professorLogado == null) {
            System.out.println("ERRO: Objeto professorLogado veio NULO.");
        } else {
            System.out.println("Professor carregado: " + professorLogado.getNomeCompleto());
        }

        carregarAtualizacoes(professorLogado);
        verificarPermissaoAdmin();
    }

    private void verificarPermissaoAdmin() {
        SessaoUsuario sessao = SessaoUsuario.getInstance();
        UsuarioDAO dao = new UsuarioDAO();
        var funcao = dao.buscarFuncaoProfessor(sessao.getEmail());

        if (funcao.gerenciador) {
            bt_Gestao.setVisible(true);
            bt_Gestao.setManaged(true);
            bt_Gestao.setStyle("-fx-text-fill: #a7d1ed;");
        }
    }

    @FXML
    void acessarGestao(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/GestaoCursos.fxml", "Gestão Administrativa", event);
    }

    private Usuario carregarDadosProfessor() {
        SessaoUsuario sessao = SessaoUsuario.getInstance();
        Usuario professor = null;

        if (sessao.isLogado()) {
            try {
                professor = usuarioDAO.exibirPerfil(sessao.getEmail());
                if (professor != null) {
                    carregarImagemSegura(imgVwFotoPerfil, professor.getFotoPerfil());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return professor;
    }

    private void carregarImagemSegura(ImageView imageView, String caminho) {
        Image imagem = null;
        if (caminho != null && !caminho.isEmpty()) {
            try {
                if (caminho.startsWith("file:") || caminho.startsWith("http")) {
                    imagem = new Image(caminho);
                } else {
                    File arquivo = new File(caminho);
                    if (arquivo.exists()) imagem = new Image(arquivo.toURI().toString());
                }
            } catch (Exception e) { System.err.println("Erro img: " + e.getMessage()); }
        }

        if (imagem == null) imagem = new Image(getClass().getResourceAsStream("/org/api/trabalhodegraduacao/images/imgFotoPerfil.png"));

        configurarImagemRedonda(imageView, imagem);
    }

    private void configurarImagemRedonda(ImageView imageView, Image imagem) {
        if (imagem == null || imageView == null) return;
        imageView.setImage(imagem);
        double w = imagem.getWidth(); double h = imagem.getHeight();
        double tamanho = Math.min(w, h);
        double x = (w - tamanho) / 2; double y = (h - tamanho) / 2;
        imageView.setViewport(new Rectangle2D(x, y, tamanho, tamanho));
        imageView.setPreserveRatio(true); imageView.setSmooth(true);
        double raio = imageView.getFitWidth() / 2;
        imageView.setClip(new Circle(raio, raio, raio));
    }

    private void carregarAtualizacoes(Usuario professor) {
        SessaoUsuario sessao = SessaoUsuario.getInstance();

        ObservableList<Notificacao> items = FXCollections.observableArrayList();

        if (sessao.isLogado()) {
            try {
                List<Notificacao> notificacoes = notificacaoDAO.buscarEnviosRecentesParaProfessor(sessao.getEmail());
                if (notificacoes != null) {
                    items.addAll(notificacoes);
                }
            } catch (SQLException e) {
                System.err.println("Erro ao buscar notificações no banco: " + e.getMessage());
            }

            if (professor != null && isPerfilIncompleto(professor)) {

                Notificacao aviso = new Notificacao();
                aviso.setMensagem("PERFIL INCOMPLETO: Preencha seus dados na aba Perfil.");
                aviso.setData(java.time.LocalDate.now());

                items.add(0, aviso);
            }
        }

        listaNotificacoes.setItems(items);
    }

    private boolean isPerfilIncompleto(Usuario u) {
        String curso = u.getCurso();
        java.time.LocalDate nasc = u.getDataNascimento();

        boolean cursoVazio = curso == null || curso.trim().isEmpty();
        boolean dataNascVazia = nasc == null;
        return cursoVazio || dataNascVazia;
    }

    @FXML void sair(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event); }
    @FXML void perfilProfessor(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/PerfilProfessor.fxml", "Perfil", event); }
    @FXML void alunos(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/Alunos.fxml", "Alunos", event); }
    @FXML void telaInicial(ActionEvent event) { System.out.println("Já está na tela inicial."); }
}