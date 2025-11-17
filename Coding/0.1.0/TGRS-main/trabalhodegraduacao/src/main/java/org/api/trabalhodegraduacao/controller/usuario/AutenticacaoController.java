package org.api.trabalhodegraduacao.controller.usuario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Circle;
import java.io.File;
import java.sql.SQLException;

import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
import org.api.trabalhodegraduacao.entities.Usuario;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;

public class AutenticacaoController {

    @FXML private ImageView imgFotoPerfil;
    @FXML private Label lblNomeUsuario;
    @FXML private PasswordField txt_Senha;
    @FXML private Button bt_Entrar;
    @FXML private Button bt_voltar;

    private UsuarioDAO usuarioDAO;
    private Usuario usuarioAutenticando;

    @FXML
    void initialize() {
        this.usuarioDAO = new UsuarioDAO();
        SessaoUsuario sessao = SessaoUsuario.getInstance();

        // Caminho da imagem padrão (caso tudo dê errado)
        String caminhoPadrao = "/org/api/trabalhodegraduacao/images/imgFotoPerfil.png";

        if (sessao.isLogado()) {
            try {
                this.usuarioAutenticando = usuarioDAO.exibirPerfil(sessao.getEmail());

                if (this.usuarioAutenticando != null) {
                    if (lblNomeUsuario != null) {
                        lblNomeUsuario.setText(this.usuarioAutenticando.getNomeCompleto());
                    }

                    // --- LÓGICA DE CARREGAMENTO DE IMAGEM BLINDADA ---
                    Image imagemFinal = null;
                    String caminhoFoto = this.usuarioAutenticando.getFotoPerfil();

                    // 1. Tenta carregar a foto do usuário se existir
                    if (caminhoFoto != null && !caminhoFoto.isEmpty()) {
                        try {
                            if (caminhoFoto.startsWith("file:") || caminhoFoto.startsWith("http")) {
                                imagemFinal = new Image(caminhoFoto, false); // false = carrega em background (opcional)
                            } else {
                                File arquivo = new File(caminhoFoto);
                                if (arquivo.exists()) {
                                    imagemFinal = new Image(arquivo.toURI().toString());
                                }
                            }
                        } catch (Exception e) {
                            System.err.println("Erro ao ler arquivo de imagem do usuário: " + e.getMessage());
                        }
                    }

                    // 2. Se a imagem do usuário falhou ou não existe, carrega a padrão
                    if (imagemFinal == null || imagemFinal.isError()) {
                        System.out.println("Usando imagem padrão.");
                        imagemFinal = new Image(getClass().getResourceAsStream(caminhoPadrao));
                    }

                    // 3. Aplica o recorte redondo
                    configurarImagemRedonda(imgFotoPerfil, imagemFinal);
                    // -------------------------------------------------

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Se não estiver logado, carrega padrão
            Image padrao = new Image(getClass().getResourceAsStream(caminhoPadrao));
            configurarImagemRedonda(imgFotoPerfil, padrao);
        }
    }

    /**
     * Ajusta e recorta a imagem para ficar perfeitamente redonda.
     */
    private void configurarImagemRedonda(ImageView imageView, Image imagem) {
        if (imagem == null) return;

        imageView.setImage(imagem);

        // Cálculos para o Center Crop (Recorte Centralizado)
        double w = imagem.getWidth();
        double h = imagem.getHeight();

        // Evita divisão por zero se a imagem não carregou dimensões ainda
        if (w <= 0 || h <= 0) return;

        double tamanhoQuadrado = Math.min(w, h);
        double x = (w - tamanhoQuadrado) / 2;
        double y = (h - tamanhoQuadrado) / 2;

        imageView.setViewport(new Rectangle2D(x, y, tamanhoQuadrado, tamanhoQuadrado));
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        // O raio deve ser metade da largura definida no FXML (ex: 150 / 2 = 75)
        double raio = imageView.getFitWidth() / 2;
        Circle clip = new Circle(raio, raio, raio);
        imageView.setClip(clip);
    }

    @FXML
    void btEntrar(ActionEvent event) {
        if (usuarioAutenticando != null) {
            String senhaDigitada = txt_Senha.getText();
            // ATENÇÃO: Se sua senha no banco tem espaços, use .trim()
            // if (senhaDigitada.equals(usuarioAutenticando.getSenha().trim())) { ... }
            if (senhaDigitada.equals(usuarioAutenticando.getSenha())) {
                System.out.println("Login com sucesso: " + usuarioAutenticando.getEmailCadastrado());

                if ("aluno".equalsIgnoreCase(usuarioAutenticando.getFuncao())) {
                    Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/AtualizacoesAluno.fxml", "Aluno - Início", event);
                } else if ("professor".equalsIgnoreCase(usuarioAutenticando.getFuncao())) {
                    Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/AtualizacoesProfessor.fxml", "Professor - Início", event);
                } else {
                    System.out.println("Função desconhecida: " + usuarioAutenticando.getFuncao());
                }
            } else {
                System.out.println("Senha incorreta.");
                // Adicione um Label de erro na tela se quiser feedback visual
            }
        }
    }

    @FXML
    void btVoltar(ActionEvent event) {
        SessaoUsuario.getInstance().limparSessao();
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event);
    }
}