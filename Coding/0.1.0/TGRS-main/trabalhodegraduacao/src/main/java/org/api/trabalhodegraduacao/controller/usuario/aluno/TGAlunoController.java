package org.api.trabalhodegraduacao.controller.usuario.aluno;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image; // Importe
import javafx.scene.image.ImageView; // Importe
import javafx.geometry.Rectangle2D; // Importe
import javafx.scene.shape.Circle; // Importe
import java.io.File; // Importe

import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.dao.SecaoDAO;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
import org.api.trabalhodegraduacao.entities.Usuario;
import org.api.trabalhodegraduacao.utils.SessaoTG;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;

import java.sql.SQLException;

public class TGAlunoController {

    // --- FXML Barra Lateral ---
    @FXML private Button bt_Sair, bt_devolutivas_geral, bt_perfil_geral, bt_secao_geral, bt_tela_inicial, bt_tg_geral;

    @FXML private ImageView imgVwFotoPerfil; // Adicionado

    // --- FXML Barras de Progresso ---
    @FXML private ProgressBar pbSecao1, pbSecao2, pbSecao3, pbSecao4, pbSecao5, pbSecao6;

    // --- DAOs e Dados ---
    private UsuarioDAO usuarioDAO;
    private SecaoDAO secaoDAO;
    private Usuario usuarioLogado;

    @FXML
    public void initialize() {
        this.usuarioDAO = new UsuarioDAO();
        this.secaoDAO = new SecaoDAO();

        SessaoUsuario sessao = SessaoUsuario.getInstance();
        if (sessao.isLogado()) {
            try {
                // 1. Carrega o usuário logado
                this.usuarioLogado = usuarioDAO.exibirPerfil(sessao.getEmail());

                if (usuarioLogado != null) {
                    // --- CARREGA A FOTO ---
                    carregarFotoPerfil();
                    // ---------------------

                    if (usuarioLogado.getEmailOrientador() != null) {
                        carregarProgresso();
                    } else {
                        // Lógica se não tiver orientador
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Se não logado, carrega foto padrão
            Image imagemPadrao = new Image(getClass().getResourceAsStream("/org/api/trabalhodegraduacao/images/imgFotoPerfil.png"));
            configurarImagemRedonda(imgVwFotoPerfil, imagemPadrao);
        }
    }

    /**
     * Carrega a foto de perfil do usuário logado e a exibe com recorte redondo.
     */
    private void carregarFotoPerfil() {
        if (imgVwFotoPerfil == null) return;

        Image imagem = null;
        String caminhoFoto = (usuarioLogado != null) ? usuarioLogado.getFotoPerfil() : null;

        if (caminhoFoto != null && !caminhoFoto.isEmpty()) {
            try {
                if (caminhoFoto.startsWith("file:") || caminhoFoto.startsWith("http")) {
                    imagem = new Image(caminhoFoto);
                } else {
                    File arquivo = new File(caminhoFoto);
                    if (arquivo.exists()) {
                        imagem = new Image(arquivo.toURI().toString());
                    }
                }
            } catch (Exception e) {
                System.err.println("Erro ao carregar foto: " + e.getMessage());
            }
        }

        if (imagem == null || imagem.isError()) {
            imagem = new Image(getClass().getResourceAsStream("/org/api/trabalhodegraduacao/images/imgFotoPerfil.png"));
        }

        configurarImagemRedonda(imgVwFotoPerfil, imagem);
    }

    private void configurarImagemRedonda(ImageView imageView, Image imagem) {
        if (imagem == null || imageView == null) return;

        imageView.setImage(imagem);
        double w = imagem.getWidth();
        double h = imagem.getHeight();
        if (w <= 0 || h <= 0) return;

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

    private void carregarProgresso() throws SQLException {
        String emailAluno = usuarioLogado.getEmailCadastrado();
        String emailOrientador = usuarioLogado.getEmailOrientador();

        pbSecao1.setProgress(secaoDAO.buscarProgressoSecao(1, emailAluno, emailOrientador));
        pbSecao2.setProgress(secaoDAO.buscarProgressoSecao(2, emailAluno, emailOrientador));
        pbSecao3.setProgress(secaoDAO.buscarProgressoSecao(3, emailAluno, emailOrientador));
        pbSecao4.setProgress(secaoDAO.buscarProgressoSecao(4, emailAluno, emailOrientador));
        pbSecao5.setProgress(secaoDAO.buscarProgressoSecao(5, emailAluno, emailOrientador));
        pbSecao6.setProgress(secaoDAO.buscarProgressoSecao(6, emailAluno, emailOrientador));
    }

    @FXML void acessarSecao1(ActionEvent event) { abrirSecao(1, event); }
    @FXML void acessarSecao2(ActionEvent event) { abrirSecao(2, event); }
    @FXML void acessarSecao3(ActionEvent event) { abrirSecao(3, event); }
    @FXML void acessarSecao4(ActionEvent event) { abrirSecao(4, event); }
    @FXML void acessarSecao5(ActionEvent event) { abrirSecao(5, event); }
    @FXML void acessarSecao6(ActionEvent event) { abrirSecao(6, event); }

    private void abrirSecao(int idTg, ActionEvent event) {
        SessaoTG.getInstance().setIdTgAtual(idTg);
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/SecaoAluno.fxml", "Seção", event);
    }

    @FXML void sair(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event); }
    @FXML void perfilAluno (ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/PerfilAluno.fxml", "Perfil Aluno", event); }
    @FXML void secaoGeral(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/SecaoAluno.fxml", "Seção", event); }
    @FXML void tgGeral(ActionEvent event) { System.out.println("Já está na tela de TG."); }
    @FXML void telaInicial(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/AtualizacoesAluno.fxml", "Tela Inicial", event); }
    @FXML void devolutivasGeral(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/DevolutivasAluno.fxml", "TGRS - Devolutivas", event); }
}