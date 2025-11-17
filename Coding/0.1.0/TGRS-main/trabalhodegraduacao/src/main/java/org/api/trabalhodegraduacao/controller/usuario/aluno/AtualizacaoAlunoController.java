package org.api.trabalhodegraduacao.controller.usuario.aluno;

import java.io.File;
import java.sql.SQLException;
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

    // --- ADICIONE O FX:ID DA FOTO ---
    @FXML private ImageView imgVwFotoPerfil;

    private NotificacaoDAO notificacaoDAO;
    private UsuarioDAO usuarioDAO;

    @FXML
    public void initialize() {
        this.notificacaoDAO = new NotificacaoDAO();
        this.usuarioDAO = new UsuarioDAO(); // Inicializa o DAO de usuário

        carregarNotificacoes();
        carregarFotoPerfil(); // Chama o método para carregar a foto
    }

    // --- MÉTODO NOVO PARA CARREGAR FOTO ---
    private void carregarFotoPerfil() {
        SessaoUsuario sessao = SessaoUsuario.getInstance();
        if (sessao.isLogado()) {
            try {
                Usuario usuario = usuarioDAO.exibirPerfil(sessao.getEmail());
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

                    // Aplica o recorte redondo
                    configurarImagemRedonda(imgVwFotoPerfil, imagem);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // --- MÉTODO DE RECORTE REDONDO (O mesmo das outras telas) ---
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

    private void carregarNotificacoes() {
        SessaoUsuario sessao = SessaoUsuario.getInstance();
        if (sessao.isLogado()) {
            try {
                List<Notificacao> notificacoes = notificacaoDAO.buscarUltimasNotificacoes(sessao.getEmail());
                ObservableList<Notificacao> items = FXCollections.observableArrayList(notificacoes);
                listaNotificacoes.setItems(items);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // --- Navegação ---
    @FXML void sair(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event); }
    @FXML void perfilAluno (ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/PerfilAluno.fxml", "Perfil Aluno", event); }
    @FXML void secaoGeral(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/SecaoAluno.fxml", "Seção", event); }
    @FXML void tgGeral(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/TGAluno.fxml", "TG Aluno", event); }
    @FXML void telaInicial(ActionEvent event) { System.out.println("Já está na tela inicial."); }
    @FXML void devolutivasGeral(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/DevolutivasAluno.fxml", "TGRS - Devolutivas", event); }
}