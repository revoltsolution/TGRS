package org.api.trabalhodegraduacao.controller.usuario.professor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.dao.SecaoDAO;
import org.api.trabalhodegraduacao.entities.Usuario;
import org.api.trabalhodegraduacao.utils.AlunoSelecionado;
import org.api.trabalhodegraduacao.utils.SessaoTG;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;

import java.sql.SQLException;

public class ListaTgsAlunoController {

    @FXML private Label lblTitulo;
    @FXML private ProgressBar pbSecao1, pbSecao2, pbSecao3, pbSecao4, pbSecao5, pbSecao6;

    @FXML private Button btnAcao1, btnAcao2, btnAcao3, btnAcao4, btnAcao5, btnAcao6;

    private SecaoDAO secaoDAO;
    private Usuario alunoSelecionado;

    @FXML
    public void initialize() {
        this.secaoDAO = new SecaoDAO();
        this.alunoSelecionado = AlunoSelecionado.getInstance().getAluno();

        if (alunoSelecionado != null) {
            lblTitulo.setText("TGs DE " + alunoSelecionado.getNomeCompleto().toUpperCase());

            carregarProgresso();

            configurarBotoes();
        }
    }

    private void configurarBotoes() {
        SessaoUsuario sessao = SessaoUsuario.getInstance();
        String emailLogado = sessao.getEmail();
        String emailOrientadorReal = alunoSelecionado.getEmailOrientador();

        boolean isApenasVisualizacao = emailOrientadorReal != null && !emailOrientadorReal.equalsIgnoreCase(emailLogado);

        String texto = isApenasVisualizacao ? "Visualizar" : "Corrigir";

        if (btnAcao1 != null) btnAcao1.setText(texto);
        if (btnAcao2 != null) btnAcao2.setText(texto);
        if (btnAcao3 != null) btnAcao3.setText(texto);
        if (btnAcao4 != null) btnAcao4.setText(texto);
        if (btnAcao5 != null) btnAcao5.setText(texto);
        if (btnAcao6 != null) btnAcao6.setText(texto);
    }

    private void carregarProgresso() {
        if (alunoSelecionado == null) return;
        try {
            String emailAluno = alunoSelecionado.getEmailCadastrado();
            String emailOrientador = alunoSelecionado.getEmailOrientador();

            pbSecao1.setProgress(secaoDAO.buscarProgressoSecao(1, emailAluno, emailOrientador));
            pbSecao2.setProgress(secaoDAO.buscarProgressoSecao(2, emailAluno, emailOrientador));
            pbSecao3.setProgress(secaoDAO.buscarProgressoSecao(3, emailAluno, emailOrientador));
            pbSecao4.setProgress(secaoDAO.buscarProgressoSecao(4, emailAluno, emailOrientador));
            pbSecao5.setProgress(secaoDAO.buscarProgressoSecao(5, emailAluno, emailOrientador));
            pbSecao6.setProgress(secaoDAO.buscarProgressoSecao(6, emailAluno, emailOrientador));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML void corrigirSecao1(ActionEvent event) { abrirCorrecao(1, event); }
    @FXML void corrigirSecao2(ActionEvent event) { abrirCorrecao(2, event); }
    @FXML void corrigirSecao3(ActionEvent event) { abrirCorrecao(3, event); }
    @FXML void corrigirSecao4(ActionEvent event) { abrirCorrecao(4, event); }
    @FXML void corrigirSecao5(ActionEvent event) { abrirCorrecao(5, event); }
    @FXML void corrigirSecao6(ActionEvent event) { abrirCorrecao(6, event); }

    private void abrirCorrecao(int idTg, ActionEvent event) {
        SessaoTG.getInstance().setIdTgAtual(idTg);
        Application.carregarNovaCena(
                "/org/api/trabalhodegraduacao/view/usuario/professor/CorrecaoSecao.fxml",
                "Detalhes da Seção",
                event
        );
    }

    @FXML void sair(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event); }
    @FXML void perfilProfessor(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/PerfilProfessor.fxml", "Perfil", event); }
    @FXML void alunos(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/Alunos.fxml", "Alunos", event); }
    @FXML void telaInicial(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/AtualizacoesProfessor.fxml", "Tela Inicial", event); }}
