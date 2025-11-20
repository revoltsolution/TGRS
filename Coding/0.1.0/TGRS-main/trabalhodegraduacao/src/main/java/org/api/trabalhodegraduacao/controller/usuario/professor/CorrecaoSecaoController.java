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
import org.api.trabalhodegraduacao.utils.SessaoTG;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;

import java.sql.SQLException;
import java.time.LocalDate;

public class CorrecaoSecaoController {

    @FXML private Button bt_Sair, bt_alunos_geral, bt_perfil_geral, bt_tela_inicial;

    @FXML private Label lblTituloAluno;
    @FXML private TextArea txtIdentificacaoProjeto, txtEmpresaParceira, txtProblema, txtSolucao, txtLinkRepositorio, txtTecnologiasUtilizadas, txtContribuicoesPessoais, txtDescricaoHard, txtDescricaoSoft, txtHistoricoProfissional, txtHistoricoAcademico, txtMotivacao, txtAno, txtPeriodo, txtSemestre;

    @FXML private TextArea txtDevolutiva;
    @FXML private Button btEnviarCorrecao;
    @FXML private CheckBox cbIdentificacao, cbEmpresa, cbProblema, cbSolucao, cbLink, cbTecnologias, cbContribuicoes, cbHardSkills, cbSoftSkills, cbHistProf, cbHistAcad, cbMotivacao, cbAno, cbPeriodo, cbSemestre;

    private SecaoDAO secaoDAO;
    private CorrecaoDAO correcaoDAO;
    private Usuario alunoSelecionado;
    private Usuario professorLogado;
    private Secao secaoAtual;
    private int idTgAtual;

    @FXML
    public void initialize() {
        this.secaoDAO = new SecaoDAO();
        this.correcaoDAO = new CorrecaoDAO();
        this.alunoSelecionado = AlunoSelecionado.getInstance().getAluno();

        this.idTgAtual = SessaoTG.getInstance().getIdTgAtual();
        if (this.idTgAtual == 0) this.idTgAtual = 1;

        SessaoUsuario sessao = SessaoUsuario.getInstance();
        if (sessao.isLogado()) {
            this.professorLogado = new Usuario();
            this.professorLogado.setEmailCadastrado(sessao.getEmail());
        }

        if (alunoSelecionado == null || professorLogado == null) {
            lblTituloAluno.setText("ERRO: Aluno ou Professor não encontrado.");
            return;
        }

        lblTituloAluno.setText("Correção: " + alunoSelecionado.getNomeCompleto() + " (ID " + idTgAtual + ")");

        carregarDadosSecao();
    }

    private void carregarDadosSecao() {
        try {
            int idTgAlvo = SessaoTG.getInstance().getIdTgAtual();

            if (idTgAlvo == 0) {
                this.secaoAtual = secaoDAO.buscarSecaoMaisRecente(
                        alunoSelecionado.getEmailCadastrado(),
                        professorLogado.getEmailCadastrado()
                );
            } else {
                this.secaoAtual = secaoDAO.buscarUltimaVersaoPorIdTg(
                        alunoSelecionado.getEmailCadastrado(),
                        professorLogado.getEmailCadastrado(),
                        idTgAlvo
                );
            }

            if (this.secaoAtual != null) {
                preencherCamposDeTexto();
                preencherCheckBoxes();

                lblTituloAluno.setText("Correção: " + alunoSelecionado.getNomeCompleto() + " (TG " + getNomeTg(secaoAtual.getIdTG()) + ")");

                Correcao ultimaCorrecao = correcaoDAO.buscarCorrecaoMaisRecente(
                        secaoAtual.getData(),
                        secaoAtual.getEmailAluno(),
                        secaoAtual.getEmailOrientador()
                );

                if (ultimaCorrecao != null && "Aprovada".equalsIgnoreCase(ultimaCorrecao.getStatus())) {
                    txtDevolutiva.setText("SEÇÃO CONCLUÍDA. Feedback final: " + ultimaCorrecao.getConteudo());
                    bloquearEdicao();
                } else if (ultimaCorrecao != null) {
                    txtDevolutiva.setText(ultimaCorrecao.getConteudo());
                }

            } else {
                lblTituloAluno.setText("O aluno ainda não enviou esta seção.");
                limparCampos();
                bloquearEdicao();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            exibirAlerta("Erro de Banco de Dados", "Não foi possível carregar a seção.", Alert.AlertType.ERROR);
        }
    }

    private String getNomeTg(int id) {
        if (id == 1 || id == 2) return "1";
        return "2";
    }

    private void bloquearEdicao() {
        txtDevolutiva.setEditable(false);
        btEnviarCorrecao.setDisable(true);
        cbIdentificacao.setDisable(true); cbEmpresa.setDisable(true); cbProblema.setDisable(true);
        cbSolucao.setDisable(true); cbLink.setDisable(true); cbTecnologias.setDisable(true);
        cbContribuicoes.setDisable(true); cbHardSkills.setDisable(true); cbSoftSkills.setDisable(true);
        cbHistProf.setDisable(true); cbHistAcad.setDisable(true); cbMotivacao.setDisable(true);
        cbAno.setDisable(true); cbPeriodo.setDisable(true); cbSemestre.setDisable(true);
    }

    private void limparCampos() {
        txtIdentificacaoProjeto.clear(); txtEmpresaParceira.clear(); txtProblema.clear();
        txtSolucao.clear(); txtLinkRepositorio.clear(); txtTecnologiasUtilizadas.clear();
        txtContribuicoesPessoais.clear(); txtDescricaoHard.clear(); txtDescricaoSoft.clear();
        txtHistoricoProfissional.clear(); txtHistoricoAcademico.clear(); txtMotivacao.clear();
        txtAno.clear(); txtPeriodo.clear(); txtSemestre.clear();
        cbIdentificacao.setSelected(false); cbEmpresa.setSelected(false); cbProblema.setSelected(false);
        cbSolucao.setSelected(false); cbLink.setSelected(false); cbTecnologias.setSelected(false);
        cbContribuicoes.setSelected(false); cbHardSkills.setSelected(false); cbSoftSkills.setSelected(false);
        cbHistProf.setSelected(false); cbHistAcad.setSelected(false); cbMotivacao.setSelected(false);
        cbAno.setSelected(false); cbPeriodo.setSelected(false); cbSemestre.setSelected(false);
    }

    @FXML
    void onEnviarCorrecao(ActionEvent event) {
        String textoDevolutiva = txtDevolutiva.getText();
        if (textoDevolutiva == null || textoDevolutiva.trim().isEmpty()) {
            exibirAlerta("Campo Vazio", "Por favor, escreva uma devolutiva antes de enviar.", Alert.AlertType.WARNING);
            return;
        }

        Correcao novaCorrecao = new Correcao();
        novaCorrecao.setConteudo(textoDevolutiva);

        boolean tudoAprovado = verificarSeTudoAprovado();

        if (tudoAprovado) {
            novaCorrecao.setStatus("Aprovada");
        } else {
            novaCorrecao.setStatus("Enviada");
        }

        novaCorrecao.setDataCorrecoes(LocalDate.now());
        novaCorrecao.setDataSecao(secaoAtual.getData());
        novaCorrecao.setEmailAluno(secaoAtual.getEmailAluno());
        novaCorrecao.setEmailOrientador(secaoAtual.getEmailOrientador());

        puxarDadosDosCheckBoxes();

        try {
            correcaoDAO.salvar(novaCorrecao);
            secaoDAO.atualizarStatusSecao(this.secaoAtual);

            if (tudoAprovado) {
                exibirAlerta("Seção Concluída!", "Todos os itens foram aprovados.", Alert.AlertType.INFORMATION);
                bloquearEdicao();
            } else {
                exibirAlerta("Sucesso", "Devolutiva enviada e progresso salvo!", Alert.AlertType.INFORMATION);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            exibirAlerta("Erro no Banco", "Não foi possível salvar a correção: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean verificarSeTudoAprovado() {
        return cbIdentificacao.isSelected() && cbEmpresa.isSelected() && cbProblema.isSelected() &&
                cbSolucao.isSelected() && cbLink.isSelected() && cbTecnologias.isSelected() &&
                cbContribuicoes.isSelected() && cbHardSkills.isSelected() && cbSoftSkills.isSelected() &&
                cbHistProf.isSelected() && cbHistAcad.isSelected() && cbMotivacao.isSelected() &&
                cbAno.isSelected() && cbPeriodo.isSelected() && cbSemestre.isSelected();
    }


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


    @FXML void sair(ActionEvent event) { AlunoSelecionado.getInstance().limparSelecao(); Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event); }
    @FXML void perfilProfessor(ActionEvent event) { AlunoSelecionado.getInstance().limparSelecao(); Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/PerfilProfessor.fxml", "Perfil", event); }
    @FXML void alunos(ActionEvent event) { AlunoSelecionado.getInstance().limparSelecao(); Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/Alunos.fxml", "Alunos", event); }
    @FXML void telaInicial(ActionEvent event) { AlunoSelecionado.getInstance().limparSelecao(); Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/AtualizacoesProfessor.fxml", "Tela Inicial", event); }
}