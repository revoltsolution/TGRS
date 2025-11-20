package org.api.trabalhodegraduacao.controller.usuario.aluno;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Circle;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.dao.SecaoDAO;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
import org.api.trabalhodegraduacao.entities.Secao;
import org.api.trabalhodegraduacao.entities.Usuario;
import org.api.trabalhodegraduacao.utils.SessaoTG;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;

public class TGAlunoController {

    @FXML private Button bt_Sair, bt_devolutivas_geral, bt_perfil_geral, bt_secao_geral, bt_tela_inicial, bt_tg_geral;
    @FXML private ImageView imgVwFotoPerfil;
    @FXML private ProgressBar pbSecao1, pbSecao2, pbSecao3, pbSecao4, pbSecao5, pbSecao6;

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
                this.usuarioLogado = usuarioDAO.exibirPerfil(sessao.getEmail());

                if (usuarioLogado != null) {
                    carregarFotoPerfil();
                    if (usuarioLogado.getEmailOrientador() != null) {
                        carregarProgresso();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Image imagemPadrao = new Image(getClass().getResourceAsStream("/org/api/trabalhodegraduacao/images/imgFotoPerfil.png"));
            configurarImagemRedonda(imgVwFotoPerfil, imagemPadrao);
        }
    }

    @FXML
    void gerarRelatorioMD(ActionEvent event) {
        if (usuarioLogado == null) return;

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Selecione onde salvar seu Portfolio Completo");

        Stage stage = (Stage) imgVwFotoPerfil.getScene().getWindow();
        File pastaSelecionada = directoryChooser.showDialog(stage);

        if (pastaSelecionada == null) return;

        try {
            List<Secao> secoesEncontradas = new ArrayList<>();
            for (int i = 1; i <= 6; i++) {
                Secao s = secaoDAO.buscarUltimaVersaoPorIdTg(
                        usuarioLogado.getEmailCadastrado(),
                        usuarioLogado.getEmailOrientador(),
                        i
                );
                if (s != null) {
                    secoesEncontradas.add(s);
                }
            }

            if (secoesEncontradas.isEmpty()) {
                exibirAlerta("Atenção", "Nenhuma seção finalizada foi encontrada para gerar o Portfolio.", Alert.AlertType.WARNING);
                return;
            }

            String nomeArquivo = gerarNomePortfolio(usuarioLogado.getNomeCompleto());
            File arquivoFinal = new File(pastaSelecionada, nomeArquivo);

            salvarPortfolioCompleto(arquivoFinal, secoesEncontradas);

            exibirAlerta("Sucesso", "Portfolio Completo gerado com sucesso!\nArquivo: " + arquivoFinal.getName() + "\nLocal: " + pastaSelecionada.getAbsolutePath(), Alert.AlertType.INFORMATION);

        } catch (SQLException e) {
            e.printStackTrace();
            exibirAlerta("Erro no Banco", "Falha ao buscar dados.", Alert.AlertType.ERROR);
        } catch (IOException e) {
            e.printStackTrace();
            exibirAlerta("Erro de Arquivo", "Falha ao gravar o arquivo.", Alert.AlertType.ERROR);
        }
    }

    private String gerarNomePortfolio(String nome) {
        if (nome == null) nome = "Aluno";
        String nfdNormalizedString = Normalizer.normalize(nome, Normalizer.Form.NFD);
        String pattern = "\\p{InCombiningDiacriticalMarks}+";
        String semAcento = nfdNormalizedString.replaceAll(pattern, "");
        String tudoJunto = semAcento.replaceAll("[^a-zA-Z0-9]", "");
        return tudoJunto + "Portfolio.md";
    }

    private void salvarPortfolioCompleto(File arquivo, List<Secao> secoes) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {

            // --- CABEÇALHO GERAL DO ALUNO ---
            writer.write("# Portfólio Acadêmico - " + usuarioLogado.getNomeCompleto() + "\n\n");

            writer.write("![Developer](https://img.shields.io/badge/Developer-Student-blue) ");
            writer.write("![Fatec](https://img.shields.io/badge/Instituição-FATEC-red)\n\n");

            writer.write("Bem-vindo ao meu portfólio! Aqui estão consolidadas todas as entregas do meu Trabalho de Graduação.\n\n");

            writer.write("- 🎓 **Curso:** " + (usuarioLogado.getCurso() != null ? usuarioLogado.getCurso() : "Gestão da Produção Industrial") + "\n");
            writer.write("- 📧 **E-mail:** " + usuarioLogado.getEmailCadastrado() + "\n");
            if (usuarioLogado.getLinkedin() != null && !usuarioLogado.getLinkedin().isEmpty()) {
                writer.write("- 💼 **LinkedIn:** [" + usuarioLogado.getLinkedin() + "](" + usuarioLogado.getLinkedin() + ")\n");
            }
            writer.write("\n---\n\n");

            writer.write("# 📚 Projetos e Entregas\n\n");

            for (Secao secao : secoes) {
                String periodoStr = (secao.getPeriodo() != '0') ? secao.getPeriodo() + "º Semestre" : "Período N/A";
                String anoStr = (secao.getAno() > 0) ? String.valueOf(secao.getAno()) : "????";
                String semestreDoAnoStr = (secao.getSemestre() != '0') ? secao.getSemestre() + "º" : "?";

                String nomeProjeto = (secao.getIdentificacaoProjeto() != null && !secao.getIdentificacaoProjeto().isEmpty())
                        ? secao.getIdentificacaoProjeto()
                        : "Projeto Sem Título";

                writer.write("## 🚀 " + periodoStr + " (" + anoStr + " - " + semestreDoAnoStr + ") - " + nomeProjeto + "\n\n");

                writer.write("### 🎯 Problema\n");
                writer.write("> " + formatarTexto(secao.getProblema()).replace("\n", "\n> ") + "\n\n");

                writer.write("### 💡 Solução\n");
                writer.write(formatarTexto(secao.getSolucao()) + "\n\n");

                writer.write("### 🛠 Tecnologias\n");
                String[] techs = formatarTexto(secao.getTecnologiasUtilizadas()).split("[,\\n]");
                for (String t : techs) {
                    if (!t.trim().isEmpty()) {
                        String techClean = t.trim().replace(" ", "%20");
                        writer.write("![Tech](https://img.shields.io/badge/-" + techClean + "-gray?style=flat-square) ");
                    }
                }
                writer.write("\n\n");

                if (secao.getLinkRepositorio() != null && !secao.getLinkRepositorio().isEmpty()) {
                    writer.write("🔗 **[Código Fonte desta Etapa](" + secao.getLinkRepositorio() + ")**\n\n");
                }

                writer.write("<details>\n<summary><b>Ver Detalhes (Competências e Histórico)</b></summary>\n\n");

                writer.write("**Contribuições:**\n" + formatarTexto(secao.getContribuicoesPessoais()) + "\n\n");

                writer.write("| Hard Skills | Soft Skills |\n| :--- | :--- |\n");
                String hard = formatarTexto(secao.getDescricaoHard()).replace("\n", "<br>");
                String soft = formatarTexto(secao.getDescricaoSoft()).replace("\n", "<br>");
                writer.write("| " + hard + " | " + soft + " |\n\n");

                writer.write("</details>\n\n");

                writer.write("---\n\n");
            }

            // --- RODAPÉ ---
            writer.write("<div align=\"center\">\n");
            writer.write("  <sub>Gerado automaticamente via <b>TGRS - Revolt Solutions</b> ⚡</sub>\n");
            writer.write("</div>");
        }
    }

    private String formatarTexto(String texto) {
        return (texto == null || texto.trim().isEmpty()) ? "*(Não informado)*" : texto.trim();
    }

    private void exibirAlerta(String titulo, String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }


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
                    if (arquivo.exists()) imagem = new Image(arquivo.toURI().toString());
                }
            } catch (Exception e) {}
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