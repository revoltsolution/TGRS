package org.api.trabalhodegraduacao.controller.usuario.professor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.dao.CorrecaoDAO;
import org.api.trabalhodegraduacao.dao.SecaoDAO;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
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

    @FXML private Button bt_Gestao;
    @FXML private Button btVoltar;

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
        } else {
            if (lblTituloAluno != null) lblTituloAluno.setText("Nenhuma Seção Selecionada");
            if (txtDevolutiva != null) txtDevolutiva.setText("Selecione uma seção específica na lista de TGs.");
            bloquearTudo();
        }

        verificarPermissaoAdmin();
    }

    private void verificarModoVisualizacao() throws SQLException {
        SessaoUsuario sessao = SessaoUsuario.getInstance();
        String emailLogado = sessao.getEmail();
        String emailOrientadorReal = alunoSelecionado.getEmailOrientador();

        boolean modoGerencialAtivo = SessaoTG.getInstance().isModoApenasLeitura();

        if (!modoGerencialAtivo && emailOrientadorReal != null && emailOrientadorReal.equalsIgnoreCase(emailLogado)) {

            Correcao ultima = correcaoDAO.buscarCorrecaoMaisRecente(secaoAtual.getData(), alunoSelecionado.getEmailCadastrado(), emailOrientadorReal);

            if (ultima != null) {
                txtDevolutiva.setText(ultima.getConteudo());
                bloquearTudo();
                lblTituloAluno.setText(lblTituloAluno.getText() + " (Aguardando nova versão do Aluno)");

                List<Correcao> historico = correcaoDAO.buscarHistoricoPorSecao(secaoAtual.getData(), alunoSelecionado.getEmailCadastrado());
                montarTextoHistorico(historico);
            }

        } else {
            bloquearTudo();
            String motivo = modoGerencialAtivo ? " (Modo Gestor)" : " (Somente Leitura)";
            lblTituloAluno.setText(lblTituloAluno.getText() + motivo);

            List<Correcao> historico = correcaoDAO.buscarHistoricoPorSecao(secaoAtual.getData(), alunoSelecionado.getEmailCadastrado());
            montarTextoHistorico(historico);
        }
    }

    private void montarTextoHistorico(List<Correcao> historico) {
        StringBuilder sb = new StringBuilder();

        if (historico.isEmpty()) {
            sb.append("⏳ Nenhuma correção histórica encontrada.");
        } else {
            for (Correcao c : historico) {
                sb.append("==================================================\n");
                sb.append("📅 CORREÇÃO DE: ").append(c.getDataCorrecoes()).append("\n");
                sb.append("📝 Status Geral: ").append(c.getStatus()).append("\n");
                sb.append("💬 Feedback Orientador: \"").append(c.getConteudo()).append("\"\n");

                Secao v = c.getDadosVersao();
                if (v != null) {
                    sb.append("\n--- DETALHE DOS CAMPOS (O que o aluno enviou) ---\n");

                    adicionarDetalheCampo(sb, "Identificação", v.getIdentificacaoProjeto(), v.isIdentificacaoOk());
                    adicionarDetalheCampo(sb, "Empresa", v.getEmpresaParceira(), v.isEmpresaOk());
                    adicionarDetalheCampo(sb, "Problema", v.getProblema(), v.isProblemaOk());
                    adicionarDetalheCampo(sb, "Solução", v.getSolucao(), v.isSolucaoOk());
                    adicionarDetalheCampo(sb, "Link Repo", v.getLinkRepositorio(), v.isLinkOk());
                    adicionarDetalheCampo(sb, "Tecnologias", v.getTecnologiasUtilizadas(), v.isTecnologiasOk());
                    adicionarDetalheCampo(sb, "Contribuições", v.getContribuicoesPessoais(), v.isContribuicoesOk());
                    adicionarDetalheCampo(sb, "Hard Skills", v.getDescricaoHard(), v.isHardskillsOk());
                    adicionarDetalheCampo(sb, "Soft Skills", v.getDescricaoSoft(), v.isSoftskillsOk());
                    adicionarDetalheCampo(sb, "Hist. Profissional", v.getHistoricoProfissional(), v.isHistProfOk());
                    adicionarDetalheCampo(sb, "Hist. Acadêmico", v.getHistoricoAcademico(), v.isHistAcadOk());
                    adicionarDetalheCampo(sb, "Motivação", v.getMotivacao(), v.isMotivacaoOk());
                }
                sb.append("\n");
            }
        }
        txtDevolutiva.setText(sb.toString());
    }

    private void adicionarDetalheCampo(StringBuilder sb, String nomeCampo, String conteudo, boolean aprovado) {
        String statusIcon = aprovado ? "[OK]" : "[PENDENTE]";
        String textoResumido = (conteudo != null && !conteudo.trim().isEmpty())
                ? conteudo.replace("\n", " ").trim()
                : "(Vazio)";

        if (textoResumido.length() > 100) textoResumido = textoResumido.substring(0, 100) + "...";

        sb.append(statusIcon).append(" ").append(nomeCampo).append(": ").append(textoResumido).append("\n");
    }

    private void bloquearTudo() {
        if(cbIdentificacao != null) cbIdentificacao.setDisable(true);
        if(cbEmpresa != null) cbEmpresa.setDisable(true);
        if(cbProblema != null) cbProblema.setDisable(true);
        if(cbSolucao != null) cbSolucao.setDisable(true);
        if(cbLink != null) cbLink.setDisable(true);
        if(cbTecnologias != null) cbTecnologias.setDisable(true);
        if(cbContribuicoes != null) cbContribuicoes.setDisable(true);
        if(cbHardSkills != null) cbHardSkills.setDisable(true);
        if(cbSoftSkills != null) cbSoftSkills.setDisable(true);
        if(cbHistProf != null) cbHistProf.setDisable(true);
        if(cbHistAcad != null) cbHistAcad.setDisable(true);
        if(cbMotivacao != null) cbMotivacao.setDisable(true);
        if(cbAno != null) cbAno.setDisable(true);
        if(cbPeriodo != null) cbPeriodo.setDisable(true);
        if(cbSemestre != null) cbSemestre.setDisable(true);

        if(btEnviarCorrecao != null) {
            btEnviarCorrecao.setDisable(true);
            btEnviarCorrecao.setVisible(false);
        }
        if(txtDevolutiva != null) txtDevolutiva.setEditable(false);
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
        if (secaoAtual == null) {
            System.out.println("Erro: Tentativa de enviar correção sem seção carregada.");
            return;
        }

        try {
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
    void acessarGestao(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/GestaoCursos.fxml", "Gestão Administrativa", event);
    }
    @FXML
    void onVoltar(ActionEvent event) {
        Application.carregarNovaCena(
                "/org/api/trabalhodegraduacao/view/usuario/professor/ListaTgsAluno.fxml",
                "TGs",
                event
        );
    }

    @FXML void sair(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event); }
    @FXML void perfilProfessor(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/PerfilProfessor.fxml", "Perfil", event); }
    @FXML void alunos(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/Alunos.fxml", "Alunos", event); }
    @FXML void telaInicial(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/AtualizacoesProfessor.fxml", "Tela Inicial", event); }
}