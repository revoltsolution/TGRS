package org.api.trabalhodegraduacao.controller.usuario.aluno;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.dao.NotificacaoDAO;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
import org.api.trabalhodegraduacao.entities.Notificacao;
import org.api.trabalhodegraduacao.entities.Usuario;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;

public class AtualizacaoAlunoController {

    @FXML private Button bt_Sair, bt_devolutivas_geral, bt_perfil_geral, bt_secao_geral, bt_tela_inicial, bt_tg_geral;
    @FXML private ListView<Notificacao> listaNotificacoes;

    @FXML private ImageView imgVwFotoPerfil;

    private NotificacaoDAO notificacaoDAO;
    private UsuarioDAO usuarioDAO;

    @FXML
    public void initialize() {
        this.notificacaoDAO = new NotificacaoDAO();
        this.usuarioDAO = new UsuarioDAO();

        Usuario usuarioLogado = carregarFotoPerfil();
        if (usuarioLogado == null) {
            System.out.println("ERRO: O objeto usuarioLogado está NULO. Verifique o DAO ou a Sessão.");
        } else {
            System.out.println("Usuario carregado: " + usuarioLogado.getNomeCompleto());
        }
        carregarNotificacoes(usuarioLogado);
    }

    private Usuario carregarFotoPerfil() {
        SessaoUsuario sessao = SessaoUsuario.getInstance();
        Usuario usuario = null;

        if (sessao.isLogado()) {
            try {
                usuario = usuarioDAO.exibirPerfil(sessao.getEmail());
                if (usuario != null) {
                    Image imagem = null;
                    String caminhoFoto = usuario.getFotoPerfil();

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
                e.printStackTrace();
            }
        }
        return usuario;
    }

    private void configurarImagemRedonda(ImageView imageView, Image imagem) {
        if (imagem == null || imageView == null) return;

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

    private void carregarNotificacoes(Usuario usuario) {
        SessaoUsuario sessao = SessaoUsuario.getInstance();
        if (sessao.isLogado()) {
            try {
                List<Notificacao> notificacoes = notificacaoDAO.buscarUltimasNotificacoes(sessao.getEmail());
                ObservableList<Notificacao> items = FXCollections.observableArrayList(notificacoes);

                if (usuario != null) {
                    boolean incompleto = isPerfilIncompleto(usuario);
                    if (incompleto) {

                        String texto = "PERFIL INCOMPLETO: Preencha seus dados na aba Perfil.";
                        Notificacao aviso = new Notificacao(texto, java.time.LocalDate.now());
                        items.add(0, aviso);
                    }
                }

                listaNotificacoes.setItems(items);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isPerfilIncompleto(Usuario u) {
        String curso = u.getCurso();
        String linkedin = u.getLinkedin();
        String github = u.getGitHub();
        java.time.LocalDate nasc = u.getDataNascimento();

        boolean cursoVazio = curso == null || curso.trim().isEmpty();
        boolean linkedinVazio = linkedin == null || linkedin.trim().isEmpty();
        boolean githubVazio = github == null || github.trim().isEmpty();
        boolean dataNascVazia = nasc == null;
        return cursoVazio || linkedinVazio || githubVazio || dataNascVazia;
    }

    @FXML void sair(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event); }
    @FXML void perfilAluno (ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/PerfilAluno.fxml", "Perfil Aluno", event); }
    @FXML void secaoGeral(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/SecaoAluno.fxml", "Seção", event); }
    @FXML void tgGeral(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/TGAluno.fxml", "TG Aluno", event); }
    @FXML void telaInicial(ActionEvent event) { System.out.println("Já está na tela inicial."); }
    @FXML void devolutivasGeral(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/DevolutivasAluno.fxml", "TGRS - Devolutivas", event); }
}