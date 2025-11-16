package org.api.trabalhodegraduacao.controller.usuario.aluno;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.dao.CorrecaoDAO;
import org.api.trabalhodegraduacao.dao.SecaoDAO;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
import org.api.trabalhodegraduacao.entities.Correcao;
import org.api.trabalhodegraduacao.entities.Secao;
import org.api.trabalhodegraduacao.entities.Usuario;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class SecaoAlunoController {

    @FXML private Button bt_Sair, bt_devolutivas_geral, bt_perfil_geral, bt_secao_geral, bt_tela_inicial, bt_tg_geral;

    @FXML private TextField txtIdentificacaoProjeto, txtEmpresaParceira, txtLinkRepositorio, txtAno;
    @FXML private TextArea txtProblema, txtSolucao, txtTecnologiasUtilizadas, txtContribuicoesPessoais, txtDescricaoHard, txtDescricaoSoft, txtHistoricoProfissional, txtHistoricoAcademico, txtMotivacao;

    @FXML private ToggleGroup grupoPeriodo, grupoSemestre;
    @FXML private RadioButton rbPeriodo1, rbPeriodo2, rbPeriodo3, rbPeriodo4, rbPeriodo5, rbPeriodo6;
    @FXML private RadioButton rbSemestre1, rbSemestre2;
    @FXML private HBox hbPeriodo, hbSemestre;

    @FXML private TextArea txtFeedbackProfessor;

    private SecaoDAO secaoDAO;
    private UsuarioDAO usuarioDAO;
    private CorrecaoDAO correcaoDAO;
    private Usuario usuarioLogado;
    private Secao secaoAtual;
    private Correcao correcaoAtual;

    @FXML
    public void initialize() {
        this.secaoDAO = new SecaoDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.correcaoDAO = new CorrecaoDAO();

        SessaoUsuario sessao = SessaoUsuario.getInstance();
        if (sessao.isLogado()) {
            try {
                this.usuarioLogado = usuarioDAO.exibirPerfil(sessao.getEmail());
                if (usuarioLogado == null || usuarioLogado.getEmailOrientador() == null || usuarioLogado.getEmailOrientador().isEmpty()) {
                    exibirAlerta("Erro de Configuração", "Você não está vinculado a um orientador. Contate a coordenação.", Alert.AlertType.ERROR);
                    return;
                }

                this.secaoAtual = secaoDAO.buscarSecaoMaisRecente(
                        usuarioLogado.getEmailCadastrado(),
                        usuarioLogado.getEmailOrientador()
                );

                if (this.secaoAtual != null) {
                    preencherCampos(this.secaoAtual);

                    this.correcaoAtual = correcaoDAO.buscarCorrecaoMaisRecente(
                            secaoAtual.getData(),
                            secaoAtual.getEmailAluno(),
                            secaoAtual.getEmailOrientador()
                    );

                    if (this.correcaoAtual == null) {
                        txtFeedbackProfessor.setText("Status: AGUARDANDO CORREÇÃO.\nVocê enviou esta seção e o professor ainda não visualizou/corrigiu. Os campos estão bloqueados até a devolutiva.");
                        bloquearTodosOsCampos();
                    } else {
                        txtFeedbackProfessor.setText("Último Feedback: " + this.correcaoAtual.getConteudo());
                        aplicarStatusDaCorrecao(this.secaoAtual);
                    }

                } else {
                    System.out.println("Nenhuma seção anterior encontrada. Começando uma nova.");
                    this.secaoAtual = new Secao();
                    txtFeedbackProfessor.setText("Esta é uma nova seção. Preencha todos os campos e envie para seu orientador.");
                    aplicarStatusDaCorrecao(null);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                exibirAlerta("Erro de Banco de Dados", "Não foi possível carregar os dados da seção.", Alert.AlertType.ERROR);
            }
        }
    }

    private void bloquearTodosOsCampos() {
        txtIdentificacaoProjeto.setDisable(true);
        txtEmpresaParceira.setDisable(true);
        txtProblema.setDisable(true);
        txtSolucao.setDisable(true);
        txtLinkRepositorio.setDisable(true);
        txtTecnologiasUtilizadas.setDisable(true);
        txtContribuicoesPessoais.setDisable(true);
        txtDescricaoHard.setDisable(true);
        txtDescricaoSoft.setDisable(true);
        txtHistoricoProfissional.setDisable(true);
        txtHistoricoAcademico.setDisable(true);
        txtMotivacao.setDisable(true);
        txtAno.setDisable(true);
        hbPeriodo.setDisable(true);
        hbSemestre.setDisable(true);
    }

    private void aplicarStatusDaCorrecao(Secao secao) {
        boolean isNova = (secao == null);

        txtIdentificacaoProjeto.setDisable(isNova ? false : secao.isIdentificacaoOk());
        txtEmpresaParceira.setDisable(isNova ? false : secao.isEmpresaOk());
        txtProblema.setDisable(isNova ? false : secao.isProblemaOk());
        txtSolucao.setDisable(isNova ? false : secao.isSolucaoOk());
        txtLinkRepositorio.setDisable(isNova ? false : secao.isLinkOk());
        txtTecnologiasUtilizadas.setDisable(isNova ? false : secao.isTecnologiasOk());
        txtContribuicoesPessoais.setDisable(isNova ? false : secao.isContribuicoesOk());
        txtDescricaoHard.setDisable(isNova ? false : secao.isHardskillsOk());
        txtDescricaoSoft.setDisable(isNova ? false : secao.isSoftskillsOk());
        txtHistoricoProfissional.setDisable(isNova ? false : secao.isHistProfOk());
        txtHistoricoAcademico.setDisable(isNova ? false : secao.isHistAcadOk());
        txtMotivacao.setDisable(isNova ? false : secao.isMotivacaoOk());
        txtAno.setDisable(isNova ? false : secao.isAnoOk());
        hbPeriodo.setDisable(isNova ? false : secao.isPeriodoOk());
        hbSemestre.setDisable(isNova ? false : secao.isSemestreOk());
    }

    @FXML
    void enviarSecao(ActionEvent event) {
        if (usuarioLogado == null) {
            exibirAlerta("Erro", "Usuário não logado.", Alert.AlertType.ERROR);
            return;
        }
        if (this.secaoAtual != null && this.correcaoAtual == null && this.secaoAtual.getIdTG() != 0) {
            exibirAlerta("Aguarde", "Você já enviou esta seção. Aguarde a correção do professor.", Alert.AlertType.WARNING);
            return;
        }

        try {
            Secao novaSecao = new Secao();

            puxarDadosDosCampos(novaSecao);

            novaSecao.setData(LocalDateTime.now());
            novaSecao.setEmailAluno(usuarioLogado.getEmailCadastrado());
            novaSecao.setEmailOrientador(usuarioLogado.getEmailOrientador());
            novaSecao.setIdTG(this.secaoAtual != null && this.secaoAtual.getIdTG() != 0 ? this.secaoAtual.getIdTG() : 1);

            if (this.secaoAtual != null) {
                novaSecao.setIdentificacaoOk(this.secaoAtual.isIdentificacaoOk());
                novaSecao.setEmpresaOk(this.secaoAtual.isEmpresaOk());
                novaSecao.setProblemaOk(this.secaoAtual.isProblemaOk());
                novaSecao.setSolucaoOk(this.secaoAtual.isSolucaoOk());
                novaSecao.setLinkOk(this.secaoAtual.isLinkOk());
                novaSecao.setTecnologiasOk(this.secaoAtual.isTecnologiasOk());
                novaSecao.setContribuicoesOk(this.secaoAtual.isContribuicoesOk());
                novaSecao.setHardskillsOk(this.secaoAtual.isHardskillsOk());
                novaSecao.setSoftskillsOk(this.secaoAtual.isSoftskillsOk());
                novaSecao.setHistProfOk(this.secaoAtual.isHistProfOk());
                novaSecao.setHistAcadOk(this.secaoAtual.isHistAcadOk());
                novaSecao.setMotivacaoOk(this.secaoAtual.isMotivacaoOk());
                novaSecao.setAnoOk(this.secaoAtual.isAnoOk());
                novaSecao.setPeriodoOk(this.secaoAtual.isPeriodoOk());
                novaSecao.setSemestreOk(this.secaoAtual.isSemestreOk());
            }

            secaoDAO.inserirSecao(novaSecao);

            this.secaoAtual = novaSecao;
            this.correcaoAtual = null;

            exibirAlerta("Sucesso", "Seção enviada com sucesso! Aguarde a devolutiva do professor.", Alert.AlertType.INFORMATION);

            initialize();

        } catch (NumberFormatException e) {
            exibirAlerta("Erro de Formato", "O campo 'Ano' deve ser um número válido (ex: 2024).", Alert.AlertType.WARNING);
        } catch (Exception e) {
            e.printStackTrace();
            exibirAlerta("Erro ao Salvar", "Ocorreu um erro ao salvar a seção: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void preencherCampos(Secao secao) {
        txtIdentificacaoProjeto.setText(secao.getIdentificacaoProjeto());
        txtEmpresaParceira.setText(secao.getEmpresaParceira());
        txtProblema.setText(secao.getProblema());
        txtSolucao.setText(secao.getSolucao());
        txtLinkRepositorio.setText(secao.getLinkRepositorio());
        txtTecnologiasUtilizadas.setText(secao.getTecnologiasUtilizadas());
        txtContribuicoesPessoais.setText(secao.getContribuicoesPessoais());
        txtDescricaoHard.setText(secao.getDescricaoHard());
        txtDescricaoSoft.setText(secao.getDescricaoSoft());
        txtHistoricoProfissional.setText(secao.getHistoricoProfissional());
        txtHistoricoAcademico.setText(secao.getHistoricoAcademico());
        txtMotivacao.setText(secao.getMotivacao());
        if(secao.getAno() > 0) {
            txtAno.setText(String.valueOf(secao.getAno()));
        }
        setPeriodo(secao.getPeriodo());
        setSemestre(secao.getSemestre());
    }

    private void puxarDadosDosCampos(Secao secao) throws NumberFormatException {
        secao.setIdentificacaoProjeto(txtIdentificacaoProjeto.getText());
        secao.setEmpresaParceira(txtEmpresaParceira.getText());
        secao.setProblema(txtProblema.getText());
        secao.setSolucao(txtSolucao.getText());
        secao.setLinkRepositorio(txtLinkRepositorio.getText());
        secao.setTecnologiasUtilizadas(txtTecnologiasUtilizadas.getText());
        secao.setContribuicoesPessoais(txtContribuicoesPessoais.getText());
        secao.setDescricaoHard(txtDescricaoHard.getText());
        secao.setDescricaoSoft(txtDescricaoSoft.getText());
        secao.setHistoricoProfissional(txtHistoricoProfissional.getText());
        secao.setHistoricoAcademico(txtHistoricoAcademico.getText());
        secao.setMotivacao(txtMotivacao.getText());

        String anoTexto = txtAno.getText();
        if(anoTexto != null && !anoTexto.trim().isEmpty()) {
            secao.setAno(Integer.parseInt(anoTexto));
        } else {
            secao.setAno(0);
        }

        secao.setPeriodo(getPeriodo());
        secao.setSemestre(getSemestre());
    }

    private char getPeriodo() {
        if (rbPeriodo1.isSelected()) return '1';
        if (rbPeriodo2.isSelected()) return '2';
        if (rbPeriodo3.isSelected()) return '3';
        if (rbPeriodo4.isSelected()) return '4';
        if (rbPeriodo5.isSelected()) return '5';
        if (rbPeriodo6.isSelected()) return '6';
        return '0';
    }
    private char getSemestre() {
        if (rbPeriodo1.isSelected()) return '1';
        if (rbSemestre2.isSelected()) return '2';
        return '0';
    }
    private void setPeriodo(char p) {
        switch (p) {
            case '1': rbPeriodo1.setSelected(true); break;
            case '2': rbPeriodo2.setSelected(true); break;
            case '3': rbPeriodo3.setSelected(true); break;
            case '4': rbPeriodo4.setSelected(true); break;
            case '5': rbPeriodo5.setSelected(true); break;
            case '6': rbPeriodo6.setSelected(true); break;
        }
    }
    private void setSemestre(char s) {
        if (s == '1') {
            rbSemestre1.setSelected(true);
        } else if (s == '2') {
            rbSemestre2.setSelected(true);
        }
    }
    private void exibirAlerta(String titulo, String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML void sair(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event); }
    @FXML void perfilAluno (ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/PerfilAluno.fxml", "Perfil Aluno", event); }
    @FXML void secaoGeral(ActionEvent event) { System.out.println("Já está na tela de Seção."); }
    @FXML void tgGeral(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/TGAluno.fxml", "TG Aluno", event); }
    @FXML void telaInicial(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/AtualizacoesAluno.fxml", "Tela Inicial", event); }
    @FXML void devolutivasGeral(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/DevolutivasAluno.fxml", "TGRS - Devolutivas", event); }
}