package org.api.trabalhodegraduacao.controller.usuario.professor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
import java.util.List;

public class CorrecaoSecaoController {

    @FXML private Label lblTituloAluno;

    @FXML private TextArea txtIdentificacaoProjeto, txtEmpresaParceira, txtProblema, txtSolucao;
    @FXML private TextArea txtLinkRepositorio, txtTecnologiasUtilizadas, txtContribuicoesPessoais;
    @FXML private TextArea txtDescricaoHard, txtDescricaoSoft, txtHistoricoProfissional;
    @FXML private TextArea txtHistoricoAcademico, txtMotivacao, txtAno, txtPeriodo, txtSemestre;

    @FXML private CheckBox cbIdentificacao, cbEmpresa, cbProblema, cbSolucao;
    @FXML private CheckBox cbLink, cbTecnologias, cbContribuicoes, cbHardSkills, cbSoftSkills;
    @FXML private CheckBox cbHistProf, cbHistAcad, cbMotivacao, cbAno, cbPeriodo, cbSemestre;

    @FXML private TextArea txtDevolutiva;
    @FXML private Button btEnviarCorrecao;

    private SecaoDAO secaoDAO;
    private CorrecaoDAO correcaoDAO;
    private Usuario alunoSelecionado;
    private Secao secaoAtual;

    @FXML
    public void initialize() {
        this.secaoDAO = new SecaoDAO();
        this.correcaoDAO = new CorrecaoDAO();
        this.alunoSelecionado = AlunoSelecionado.getInstance().getAluno();
        int idTg = SessaoTG.getInstance().getIdTgAtual();

        if (alunoSelecionado != null && idTg != 0) {
            lblTituloAluno.setText("SEÇÃO " + idTg + " - " + alunoSelecionado.getNomeCompleto().toUpperCase());

            try {
                this.secaoAtual = secaoDAO.buscarUltimaVersaoPorIdTg(
                        alunoSelecionado.getEmailCadastrado(),
                        alunoSelecionado.getEmailOrientador(),
                        idTg
                );

                if (this.secaoAtual != null) {
                    preencherCampos();

                    verificarModoVisualizacao();

                } else {
                    txtDevolutiva.setText("O aluno ainda não enviou esta seção.");
                    bloquearTudo();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void verificarModoVisualizacao() throws SQLException {
        SessaoUsuario sessao = SessaoUsuario.getInstance();
        String emailLogado = sessao.getEmail();
        String emailOrientadorReal = alunoSelecionado.getEmailOrientador();

        if (emailOrientadorReal != null && emailOrientadorReal.equalsIgnoreCase(emailLogado)) {

            Correcao ultima = correcaoDAO.buscarCorrecaoMaisRecente(secaoAtual.getData(), alunoSelecionado.getEmailCadastrado(), emailOrientadorReal);
            if (ultima != null) txtDevolutiva.setText(ultima.getConteudo());

        } else {
            bloquearTudo();
            lblTituloAluno.setText(lblTituloAluno.getText() + " (Somente Leitura)");

            List<Correcao> historico = correcaoDAO.buscarHistoricoPorSecao(secaoAtual.getData(), alunoSelecionado.getEmailCadastrado());
            montarTextoHistorico(historico);
        }
    }

    private void montarTextoHistorico(List<Correcao> historico) {
        StringBuilder sb = new StringBuilder();

        sb.append("=== 📂 ENTREGA DO ALUNO ===\n");
        sb.append("Data Envio: ").append(secaoAtual.getData().toLocalDate()).append("\n");
        sb.append("(Conteúdo preenchido nos campos acima)\n");
        sb.append("--------------------------------------------------\n\n");

        if (historico.isEmpty()) {
            sb.append("⏳ Nenhuma correção realizada pelo orientador ainda.");
        } else {
            for (Correcao c : historico) {
                sb.append("=== 🎓 FEEDBACK DO ORIENTADOR ===\n");
                sb.append("Data: ").append(c.getDataCorrecoes()).append("\n");
                sb.append("Status: ").append(c.getStatus()).append("\n");
                sb.append("Mensagem: ").append(c.getConteudo()).append("\n");
                sb.append("--------------------------------------------------\n\n");
            }
        }
        txtDevolutiva.setText(sb.toString());
    }

    private void bloquearTudo() {
        cbIdentificacao.setDisable(true); cbEmpresa.setDisable(true); cbProblema.setDisable(true);
        cbSolucao.setDisable(true); cbLink.setDisable(true); cbTecnologias.setDisable(true);
        cbContribuicoes.setDisable(true); cbHardSkills.setDisable(true); cbSoftSkills.setDisable(true);
        cbHistProf.setDisable(true); cbHistAcad.setDisable(true); cbMotivacao.setDisable(true);
        cbAno.setDisable(true); cbPeriodo.setDisable(true); cbSemestre.setDisable(true);

        btEnviarCorrecao.setDisable(true);
        btEnviarCorrecao.setVisible(false);
        txtDevolutiva.setEditable(false);
    }

    private void preencherCampos() {
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

    @FXML
    void onEnviarCorrecao(ActionEvent event) {
        try {
            secaoAtual.setIdentificacaoOk(cbIdentificacao.isSelected());
            secaoDAO.atualizarStatusSecao(secaoAtual);

            Correcao c = new Correcao();
            c.setDataCorrecoes(LocalDate.now());
            c.setConteudo(txtDevolutiva.getText());
            c.setStatus("Correção Enviada");
            c.setDataSecao(secaoAtual.getData());
            c.setEmailAluno(alunoSelecionado.getEmailCadastrado());
            c.setEmailOrientador(SessaoUsuario.getInstance().getEmail());

            correcaoDAO.salvar(c);

            Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/ListaTgsAluno.fxml", "TGs", event);
        } catch (Exception e) { e.printStackTrace(); }
    }
    @FXML void sair(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event); }
    @FXML void perfilProfessor(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/PerfilProfessor.fxml", "Perfil", event); }
    @FXML void alunos(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/Alunos.fxml", "Alunos", event); }
    @FXML void telaInicial(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/AtualizacoesProfessor.fxml", "Tela Inicial", event); }
}