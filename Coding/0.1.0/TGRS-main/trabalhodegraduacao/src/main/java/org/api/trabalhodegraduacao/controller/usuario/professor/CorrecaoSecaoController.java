package org.api.trabalhodegraduacao.controller.usuario.professor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.dao.CorrecaoDAO;
import org.api.trabalhodegraduacao.dao.SecaoDAO;
import org.api.trabalhodegraduacao.entities.Correcao;
import org.api.trabalhodegraduacao.entities.Secao;
import org.api.trabalhodegraduacao.entities.Usuario;
import org.api.trabalhodegraduacao.utils.AlunoSelecionado;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;

import java.sql.SQLException;
import java.time.LocalDate;

public class CorrecaoSecaoController {

    // --- FXML Barra Lateral ---
    @FXML private Button bt_Sair, bt_alunos_geral, bt_devolutivas_geral, bt_perfil_geral, bt_tela_inicial;

    // --- FXML Conteúdo (Dados Aluno) ---
    @FXML private Label lblTituloAluno;
    @FXML private TextArea txtIdentificacaoProjeto, txtEmpresaParceira, txtProblema, txtSolucao, txtLinkRepositorio, txtTecnologiasUtilizadas, txtContribuicoesPessoais, txtDescricaoHard, txtDescricaoSoft, txtHistoricoProfissional, txtHistoricoAcademico, txtMotivacao, txtAno, txtPeriodo, txtSemestre;

    // --- FXML Conteúdo (Correção Professor) ---
    @FXML private TextArea txtDevolutiva;
    @FXML private Button btEnviarCorrecao;
    @FXML private CheckBox cbIdentificacao, cbEmpresa, cbProblema, cbSolucao, cbLink, cbTecnologias, cbContribuicoes, cbHardSkills, cbSoftSkills, cbHistProf, cbHistAcad, cbMotivacao, cbAno, cbPeriodo, cbSemestre;

    // --- DAOs e Dados ---
    private SecaoDAO secaoDAO;
    private CorrecaoDAO correcaoDAO;
    private Usuario alunoSelecionado;
    private Usuario professorLogado;
    private Secao secaoAtual;

    @FXML
    public void initialize() {
        this.secaoDAO = new SecaoDAO();
        this.correcaoDAO = new CorrecaoDAO();
        this.alunoSelecionado = AlunoSelecionado.getInstance().getAluno();

        SessaoUsuario sessao = SessaoUsuario.getInstance();
        if (sessao.isLogado()) {
            this.professorLogado = new Usuario();
            this.professorLogado.setEmailCadastrado(sessao.getEmail());
        }

        if (alunoSelecionado == null || professorLogado == null) {
            lblTituloAluno.setText("ERRO: Aluno ou Professor não encontrado.");
            return;
        }

        lblTituloAluno.setText("Correção de: " + alunoSelecionado.getNomeCompleto());
        carregarDadosSecao();
    }

    private void carregarDadosSecao() {
        try {
            this.secaoAtual = secaoDAO.buscarSecaoMaisRecente(
                    alunoSelecionado.getEmailCadastrado(),
                    professorLogado.getEmailCadastrado()
            );

            if (this.secaoAtual != null) {
                preencherCamposDeTexto();
                preencherCheckBoxes();
            } else {
                lblTituloAluno.setText(alunoSelecionado.getNomeCompleto() + " - Nenhuma seção enviada.");
                txtDevolutiva.setDisable(true);
                btEnviarCorrecao.setDisable(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            exibirAlerta("Erro de Banco de Dados", "Não foi possível carregar a seção.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onEnviarCorrecao(ActionEvent event) {
        String textoDevolutiva = txtDevolutiva.getText();
        if (textoDevolutiva == null || textoDevolutiva.trim().isEmpty()) {
            exibirAlerta("Campo Vazio", "Por favor, escreva uma devolutiva antes de enviar.", Alert.AlertType.WARNING);
            return;
        }

        // 1. Cria o objeto Correcao
        Correcao novaCorrecao = new Correcao();
        novaCorrecao.setConteudo(textoDevolutiva);
        novaCorrecao.setStatus("Enviada");
        novaCorrecao.setDataCorrecoes(LocalDate.now());
        novaCorrecao.setDataSecao(secaoAtual.getData());
        novaCorrecao.setEmailAluno(secaoAtual.getEmailAluno());
        novaCorrecao.setEmailOrientador(secaoAtual.getEmailOrientador());

        // 2. Atualiza o objeto Secao com o status dos checkboxes
        puxarDadosDosCheckBoxes();

        try {
            // 3. Salva a Devolutiva (na tabela 'correcoes')
            correcaoDAO.salvar(novaCorrecao);

            // 4. Salva o Progresso (na tabela 'Secao')
            // --- CORREÇÃO AQUI ---
            secaoDAO.atualizarStatusSecao(this.secaoAtual); // Chama o método correto de ATUALIZAR

            exibirAlerta("Sucesso", "Devolutiva enviada e progresso salvo!", Alert.AlertType.INFORMATION);
            alunos(event); // Volta para a tela de alunos

        } catch (SQLException e) {
            e.printStackTrace();
            exibirAlerta("Erro no Banco", "Não foi possível salvar a correção: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // --- Métodos Auxiliares ---

    private void preencherCamposDeTexto() {
        txtIdentificacaoProjeto.setText(secaoAtual.getIdentificacaoProjeto());
        txtEmpresaParceira.setText(secaoAtual.getEmpresaParceira());
        txtProblema.setText(secaoAtual.getProblema());
        txtSolucao.setText(secaoAtual.getSolucao());
        txtLinkRepositorio.setText(secaoAtual.getLinkRepositorio());
        txtTecnologiasUtilizadas.setText(secaoAtual.getTecnologiasUtilizadas());
        txtContribuicoesPessoais.setText(secaoAtual.getContribuicoesPessoais());
        txtDescricaoHard.setText(secaoAtual.getDescricaoHard());
        txtDescricaoSoft.setText(secaoAtual.getDescricaoSoft());
        txtHistoricoProfissional.setText(secaoAtual.getHistoricoProfissional());
        txtHistoricoAcademico.setText(secaoAtual.getHistoricoAcademico());
        txtMotivacao.setText(secaoAtual.getMotivacao());
        txtAno.setText(String.valueOf(secaoAtual.getAno()));
        txtPeriodo.setText(String.valueOf(secaoAtual.getPeriodo()));
        txtSemestre.setText(String.valueOf(secaoAtual.getSemestre()));
    }

    private void preencherCheckBoxes() {
        cbIdentificacao.setSelected(secaoAtual.isIdentificacaoOk());
        cbEmpresa.setSelected(secaoAtual.isEmpresaOk());
        cbProblema.setSelected(secaoAtual.isProblemaOk());
        cbSolucao.setSelected(secaoAtual.isSolucaoOk());
        cbLink.setSelected(secaoAtual.isLinkOk());
        cbTecnologias.setSelected(secaoAtual.isTecnologiasOk());
        cbContribuicoes.setSelected(secaoAtual.isContribuicoesOk());
        cbHardSkills.setSelected(secaoAtual.isHardskillsOk());
        cbSoftSkills.setSelected(secaoAtual.isSoftskillsOk());
        cbHistProf.setSelected(secaoAtual.isHistProfOk());
        cbHistAcad.setSelected(secaoAtual.isHistAcadOk());
        cbMotivacao.setSelected(secaoAtual.isMotivacaoOk());
        cbAno.setSelected(secaoAtual.isAnoOk());
        cbPeriodo.setSelected(secaoAtual.isPeriodoOk());
        cbSemestre.setSelected(secaoAtual.isSemestreOk());
    }

    private void puxarDadosDosCheckBoxes() {
        secaoAtual.setIdentificacaoOk(cbIdentificacao.isSelected());
        secaoAtual.setEmpresaOk(cbEmpresa.isSelected());
        secaoAtual.setProblemaOk(cbProblema.isSelected());
        secaoAtual.setSolucaoOk(cbSolucao.isSelected());
        secaoAtual.setLinkOk(cbLink.isSelected());
        secaoAtual.setTecnologiasOk(cbTecnologias.isSelected());
        secaoAtual.setContribuicoesOk(cbContribuicoes.isSelected());
        secaoAtual.setHardskillsOk(cbHardSkills.isSelected());
        secaoAtual.setSoftskillsOk(cbSoftSkills.isSelected());
        secaoAtual.setHistProfOk(cbHistProf.isSelected());
        secaoAtual.setHistAcadOk(cbHistAcad.isSelected());
        secaoAtual.setMotivacaoOk(cbMotivacao.isSelected());
        secaoAtual.setAnoOk(cbAno.isSelected());
        secaoAtual.setPeriodoOk(cbPeriodo.isSelected());
        secaoAtual.setSemestreOk(cbSemestre.isSelected());
    }

    private void exibirAlerta(String titulo, String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    // --- Métodos de Navegação ---

    @FXML void sair(ActionEvent event) { AlunoSelecionado.getInstance().limparSelecao(); Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event); }
    @FXML void perfilProfessor(ActionEvent event) { AlunoSelecionado.getInstance().limparSelecao(); Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/PerfilProfessor.fxml", "Perfil", event); }
    @FXML void alunos(ActionEvent event) { AlunoSelecionado.getInstance().limparSelecao(); Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/Alunos.fxml", "Alunos", event); }
    @FXML void devolutivas(ActionEvent event) { AlunoSelecionado.getInstance().limparSelecao(); Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/Historico.fxml", "Devolutivas", event); }
    @FXML void telaInicial(ActionEvent event) { AlunoSelecionado.getInstance().limparSelecao(); Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/AtualizacoesProfessor.fxml", "Tela Inicial", event); }
}