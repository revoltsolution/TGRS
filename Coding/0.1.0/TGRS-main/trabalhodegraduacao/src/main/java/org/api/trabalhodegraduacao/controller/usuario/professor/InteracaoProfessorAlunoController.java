package org.api.trabalhodegraduacao.controller.usuario.professor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.entities.Usuario;
import org.api.trabalhodegraduacao.utils.AlunoSelecionado;

import java.time.format.DateTimeFormatter;

public class InteracaoProfessorAlunoController {

    // --- FXML Barra Lateral ---
    @FXML private Button bt_perfil_geral, bt_alunos_geral, bt_devolutivas_geral, bt_tela_inicial, bt_Sair;

    // --- FXML Conteúdo Principal ---
    @FXML private Label lblNomeAlunoHeader;
    @FXML private ImageView imgVwFotoPerfil;

    // FXML do GridPane (Corrigido)
    @FXML private Label lblNome;
    @FXML private Label lblEmail;
    @FXML private Label lblCurso;
    @FXML private Label lblDataNascimento;
    @FXML private Label lblLinkedin;
    @FXML private Label lblGitHub;
    @FXML private Label lblOrientador;

    // Botões de Ação
    @FXML private Button bt_professor_secao_aluno;
    @FXML private Button bt_professor_tg_aluno;

    private Usuario alunoSelecionado;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    public void initialize() {
        this.alunoSelecionado = AlunoSelecionado.getInstance().getAluno();

        if (this.alunoSelecionado == null) {
            System.err.println("Erro: Nenhum aluno foi selecionado. Voltando para a lista.");
            lblNomeAlunoHeader.setText("ERRO - ALUNO NÃO ENCONTRADO");
            return;
        }

        preencherDadosAluno();
    }

    private void preencherDadosAluno() {
        lblNomeAlunoHeader.setText(alunoSelecionado.getNomeCompleto());

        // Preenche os labels do GridPane
        lblNome.setText(getTextoOuPadrao(alunoSelecionado.getNomeCompleto()));
        lblEmail.setText(getTextoOuPadrao(alunoSelecionado.getEmailCadastrado()));
        lblCurso.setText(getTextoOuPadrao(alunoSelecionado.getCurso()));
        lblLinkedin.setText(getTextoOuPadrao(alunoSelecionado.getLinkedin()));
        lblGitHub.setText(getTextoOuPadrao(alunoSelecionado.getGitHub()));
        lblOrientador.setText(getTextoOuPadrao(alunoSelecionado.getNomeOrientador()));

        // Formata a data
        if (alunoSelecionado.getDataNascimento() != null) {
            lblDataNascimento.setText(alunoSelecionado.getDataNascimento().format(dateFormatter));
        } else {
            lblDataNascimento.setText("(Não informado)");
        }

        // (Lógica para carregar a foto do aluno)
    }

    private String getTextoOuPadrao(String texto) {
        return (texto != null && !texto.trim().isEmpty()) ? texto : "(Não informado)";
    }

    @FXML
    void onAbrirSecaoAluno(ActionEvent event) {
        if (this.alunoSelecionado == null) {
            System.err.println("Nenhum aluno selecionado para abrir a seção.");
            return;
        }

        Application.carregarNovaCena(
                "/org/api/trabalhodegraduacao/view/usuario/professor/CorrecaoSecao.fxml",
                "Corrigir Seção",
                event
        );
    }

    // --- Métodos de Navegação (Barra Lateral do Professor) ---

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
    void devolutivas(ActionEvent event) {
        AlunoSelecionado.getInstance().limparSelecao();
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/Historico.fxml", "Devolutivas", event);
    }

    @FXML
    void telaInicial(ActionEvent event) {
        AlunoSelecionado.getInstance().limparSelecao();
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/AtualizacoesProfessor.fxml", "Tela Inicial", event);
    }
}