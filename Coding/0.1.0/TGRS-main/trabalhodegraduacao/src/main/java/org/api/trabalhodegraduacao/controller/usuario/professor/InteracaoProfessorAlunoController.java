package org.api.trabalhodegraduacao.controller.usuario.professor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.dao.SecaoDAO;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
import org.api.trabalhodegraduacao.entities.Secao;
import org.api.trabalhodegraduacao.entities.Usuario;
import org.api.trabalhodegraduacao.utils.AlunoSelecionado;
import org.api.trabalhodegraduacao.utils.SessaoTG;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;

import java.io.File;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class InteracaoProfessorAlunoController {

    @FXML private Button bt_perfil_geral, bt_alunos_geral, bt_tela_inicial, bt_Sair;
    @FXML private Button bt_Gestao;
    @FXML private Button btVoltar;

    @FXML private Label lblNomeAlunoHeader;
    @FXML private ImageView imgVwFotoPerfil;

    @FXML private Label lblNome;
    @FXML private Label lblEmail;
    @FXML private Label lblCurso;
    @FXML private Label lblDataNascimento;
    @FXML private Label lblLinkedin;
    @FXML private Label lblGitHub;
    @FXML private Label lblOrientador;

    @FXML private Button bt_professor_secao_aluno;
    @FXML private Button bt_professor_tg_aluno;

    private Usuario alunoSelecionado;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private SecaoDAO secaoDAO = new SecaoDAO();

    @FXML
    public void initialize() {
        this.alunoSelecionado = AlunoSelecionado.getInstance().getAluno();

        if (this.alunoSelecionado == null) {
            System.err.println("Erro: Nenhum aluno foi selecionado. Voltando para a lista.");
            lblNomeAlunoHeader.setText("ERRO - ALUNO NÃO ENCONTRADO");
            return;
        }

        preencherDadosAluno();
        carregarFotoAluno();
        verificarPermissaoAdmin();
    }

    @FXML
    void onAbrirSecaoAluno(ActionEvent event) {
        if (this.alunoSelecionado == null) {
            System.err.println("Nenhum aluno selecionado para abrir a seção.");
            return;
        }

        try {
            Secao ultimaSecao = secaoDAO.buscarSecaoMaisRecente(
                    alunoSelecionado.getEmailCadastrado(),
                    alunoSelecionado.getEmailOrientador()
            );

            int idParaAbrir = (ultimaSecao != null && ultimaSecao.getIdTG() > 0) ? ultimaSecao.getIdTG() : 1;

            SessaoTG.getInstance().setIdTgAtual(idParaAbrir);

            Application.carregarNovaCena(
                    "/org/api/trabalhodegraduacao/view/usuario/professor/CorrecaoSecao.fxml",
                    "Corrigir Seção",
                    event
            );

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao buscar seção atual: " + e.getMessage());
        }
    }

    @FXML
    void onAbrirTgsAluno(ActionEvent event) {
        if (this.alunoSelecionado == null) return;

        Application.carregarNovaCena(
                "/org/api/trabalhodegraduacao/view/usuario/professor/ListaTgsAluno.fxml",
                "TGs de " + alunoSelecionado.getNomeCompleto(),
                event
        );
    }

    private void carregarFotoAluno() {
        if (imgVwFotoPerfil == null) return;

        Image imagem = null;
        String caminhoFoto = alunoSelecionado.getFotoPerfil();

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

    private void preencherDadosAluno() {
        lblNomeAlunoHeader.setText(alunoSelecionado.getNomeCompleto());

        lblNome.setText(getTextoOuPadrao(alunoSelecionado.getNomeCompleto()));
        lblEmail.setText(getTextoOuPadrao(alunoSelecionado.getEmailCadastrado()));
        lblCurso.setText(getTextoOuPadrao(alunoSelecionado.getCurso()));
        lblLinkedin.setText(getTextoOuPadrao(alunoSelecionado.getLinkedin()));
        lblGitHub.setText(getTextoOuPadrao(alunoSelecionado.getGitHub()));
        lblOrientador.setText(getTextoOuPadrao(alunoSelecionado.getNomeOrientador()));

        if (alunoSelecionado.getDataNascimento() != null) {
            lblDataNascimento.setText(alunoSelecionado.getDataNascimento().format(dateFormatter));
        } else {
            lblDataNascimento.setText("(Não informado)");
        }
    }

    private String getTextoOuPadrao(String texto) {
        return (texto != null && !texto.trim().isEmpty()) ? texto : "(Não informado)";
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
                    bt_Gestao.setStyle("-fx-text-fill: #a7d1ed;");
                }
            }
        }
    }
    @FXML
    void onVoltar(ActionEvent event) {
        org.api.trabalhodegraduacao.utils.AlunoSelecionado.getInstance().limparSelecao();
        Application.carregarNovaCena(
                "/org/api/trabalhodegraduacao/view/usuario/professor/Alunos.fxml",
                "Alunos",
                event
        );
    }

    @FXML
    void acessarGestao(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/GestaoCursos.fxml", "Gestão Administrativa", event);
    }

    @FXML
    void sair(ActionEvent event) {
        AlunoSelecionado.getInstance().limparSelecao();
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event);
    }

    @FXML
    void perfilProfessor(ActionEvent event) {
        AlunoSelecionado.getInstance().limparSelecao();
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/PerfilProfessor.fxml", "Perfil", event);
    }

    @FXML
    void alunos(ActionEvent event) {
        AlunoSelecionado.getInstance().limparSelecao();
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/Alunos.fxml", "Alunos", event);
    }

    @FXML
    void telaInicial(ActionEvent event) {
        AlunoSelecionado.getInstance().limparSelecao();
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/AtualizacoesProfessor.fxml", "Tela Inicial", event);
    }
}