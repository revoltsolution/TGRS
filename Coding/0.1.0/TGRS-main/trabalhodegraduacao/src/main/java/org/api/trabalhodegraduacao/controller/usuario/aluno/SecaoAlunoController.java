package org.api.trabalhodegraduacao.controller.usuario.aluno;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.dao.CorrecaoDAO;
import org.api.trabalhodegraduacao.dao.SecaoDAO;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
import org.api.trabalhodegraduacao.entities.Correcao;
import org.api.trabalhodegraduacao.entities.Secao;
import org.api.trabalhodegraduacao.entities.Usuario;
import org.api.trabalhodegraduacao.utils.SessaoTG;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;
import org.api.trabalhodegraduacao.utils.SessaoVisualizacao; // Importe SessaoVisualizacao
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Circle;
import java.io.File;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class SecaoAlunoController {
    // --- FXML Barra Lateral ---
    @FXML private ImageView imgVwFotoPerfil; // Adicionado fx:id para a imagem do perfil
    @FXML private Button bt_Sair, bt_devolutivas_geral, bt_perfil_geral, bt_secao_geral, bt_tela_inicial, bt_tg_geral;

    // --- FXML do Formulário ---
    @FXML private TextField txtIdentificacaoProjeto, txtEmpresaParceira, txtLinkRepositorio, txtAno;
    @FXML private TextArea txtProblema, txtSolucao, txtTecnologiasUtilizadas, txtContribuicoesPessoais, txtDescricaoHard, txtDescricaoSoft, txtHistoricoProfissional, txtHistoricoAcademico, txtMotivacao;

    // FXML para RadioButtons
    @FXML private ToggleGroup grupoPeriodo, grupoSemestre;
    @FXML private RadioButton rbPeriodo1, rbPeriodo2, rbPeriodo3, rbPeriodo4, rbPeriodo5, rbPeriodo6;
    @FXML private RadioButton rbSemestre1, rbSemestre2;
    @FXML private HBox hbPeriodo, hbSemestre;

    // --- FXML do Feedback e Controle ---
    @FXML private TextArea txtFeedbackProfessor;
    @FXML private VBox painelSucesso;
    @FXML private Button btEnviar;

    // --- DAOs e Dados ---
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

        if (painelSucesso != null) {
            painelSucesso.setVisible(false);
            painelSucesso.setManaged(false);
        }

        // --- 1. VERIFICAÇÃO DE MODO HISTÓRICO ---
        SessaoVisualizacao sessaoVis = SessaoVisualizacao.getInstance();
        if (sessaoVis.getSecaoHistorica() != null) {
            // Estamos abrindo uma versão antiga!
            carregarModoHistorico(sessaoVis);
            // Mesmo no modo histórico, pode ser útil tentar carregar a foto do usuário logado
            carregarFotoPerfil(); // <--- CHAMA AQUI TAMBÉM
            return; // PARE AQUI! Não carregue a versão atual.
        }
        // ----------------------------------------

        SessaoUsuario sessao = SessaoUsuario.getInstance();

        // Recupera o ID do TG atual
        int idTgAlvo = SessaoTG.getInstance().getIdTgAtual();
        if (idTgAlvo == 0) idTgAlvo = 1;

        if (sessao.isLogado()) {
            try {
                this.usuarioLogado = usuarioDAO.exibirPerfil(sessao.getEmail());

                // --- CARREGA A FOTO DE PERFIL AQUI ---
                if (this.usuarioLogado != null) {
                    carregarFotoPerfil();
                }
                // ------------------------------------

                if (usuarioLogado == null || usuarioLogado.getEmailOrientador() == null || usuarioLogado.getEmailOrientador().isEmpty()) {
                    exibirAlerta("Erro de Configuração", "Você não está vinculado a um orientador. Contate a coordenação.", Alert.AlertType.ERROR);
                    return;
                }

                // Busca a seção mais recente do TG ESPECÍFICO (Fluxo Normal)
                this.secaoAtual = secaoDAO.buscarUltimaVersaoPorIdTg(
                        usuarioLogado.getEmailCadastrado(),
                        usuarioLogado.getEmailOrientador(),
                        idTgAlvo
                );

                if (this.secaoAtual != null) {
                    preencherCampos(this.secaoAtual);

                    this.correcaoAtual = correcaoDAO.buscarCorrecaoMaisRecente(
                            secaoAtual.getData(),
                            secaoAtual.getEmailAluno(),
                            secaoAtual.getEmailOrientador()
                    );

                    if (this.correcaoAtual == null) {
                        txtFeedbackProfessor.setText("Status: AGUARDANDO CORREÇÃO.\nVocê enviou esta seção e o professor ainda não visualizou. Aguarde.");
                        bloquearTodosOsCampos();
                        if (btEnviar != null) btEnviar.setVisible(false);
                    } else {
                        txtFeedbackProfessor.setText("Último Feedback: " + this.correcaoAtual.getConteudo());

                        if ("Aprovada".equalsIgnoreCase(this.correcaoAtual.getStatus())) {
                            if (painelSucesso != null) {
                                painelSucesso.setVisible(true);
                                painelSucesso.setManaged(true);
                            }
                            if (btEnviar != null) btEnviar.setVisible(false);
                            bloquearTodosOsCampos();
                        } else {
                            if (btEnviar != null) btEnviar.setVisible(true);
                            aplicarStatusDaCorrecao(this.secaoAtual);
                        }
                    }

                } else {
                    // Seção nova
                    System.out.println("Nenhuma seção anterior encontrada para o TG " + idTgAlvo);
                    this.secaoAtual = new Secao();
                    this.secaoAtual.setIdTG(idTgAlvo);

                    txtFeedbackProfessor.setText("Iniciando TG " + idTgAlvo + ". Preencha e envie.");
                    aplicarStatusDaCorrecao(null);
                    if (btEnviar != null) btEnviar.setVisible(true);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                exibirAlerta("Erro de Banco de Dados", "Não foi possível carregar os dados.", Alert.AlertType.ERROR);
            }
        } else {
            // Se o usuário não está logado na sessão, carrega a imagem padrão
            Image imagemPadrao = new Image(getClass().getResourceAsStream("/org/api/trabalhodegraduacao/images/imgFotoPerfil.png"));
            configurarImagemRedonda(imgVwFotoPerfil, imagemPadrao);
        }
    }

    /**
     * Carrega a foto de perfil do usuário logado e a exibe com recorte redondo.
     */
    private void carregarFotoPerfil() {
        if (imgVwFotoPerfil == null) return; // Garante que o ImageView existe

        Image imagem = null;
        String caminhoFoto = (usuarioLogado != null) ? usuarioLogado.getFotoPerfil() : null;

        if (caminhoFoto != null && !caminhoFoto.isEmpty()) {
            try {
                if (caminhoFoto.startsWith("file:") || caminhoFoto.startsWith("http")) {
                    imagem = new Image(caminhoFoto, false);
                } else {
                    File arquivo = new File(caminhoFoto);
                    if (arquivo.exists()) {
                        imagem = new Image(arquivo.toURI().toString());
                    }
                }
            } catch (Exception e) {
                System.err.println("Erro ao carregar foto do usuário (" + caminhoFoto + "): " + e.getMessage());
            }
        }

        // Se a imagem do usuário falhou ou não existe, carrega a padrão
        if (imagem == null || imagem.isError()) {
            System.out.println("Usando imagem padrão para o perfil.");
            imagem = new Image(getClass().getResourceAsStream("/org/api/trabalhodegraduacao/images/imgFotoPerfil.png"));
        }

        configurarImagemRedonda(imgVwFotoPerfil, imagem);
    }

    /**
     * Ajusta uma imagem para caber perfeitamente em um ImageView circular (Center Crop).
     */
    private void configurarImagemRedonda(ImageView imageView, Image imagem) {
        if (imagem == null || imageView == null) return;

        imageView.setImage(imagem);

        double w = imagem.getWidth();
        double h = imagem.getHeight();

        // Evita divisão por zero se a imagem não carregou dimensões ainda
        if (w <= 0 || h <= 0) return;

        double tamanhoQuadrado = Math.min(w, h);

        // Calcula as coordenadas para centralizar o recorte
        double x = (w - tamanhoQuadrado) / 2;
        double y = (h - tamanhoQuadrado) / 2;

        imageView.setViewport(new Rectangle2D(x, y, tamanhoQuadrado, tamanhoQuadrado));
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        // O raio deve ser metade da largura/altura definida no FXML (ex: 150 / 2 = 75)
        double raio = imageView.getFitWidth() / 2;
        Circle clip = new Circle(raio, raio, raio);
        imageView.setClip(clip);
    }

    /**
     * Configura a tela para exibir um histórico antigo (apenas leitura).
     */
    private void carregarModoHistorico(SessaoVisualizacao sessaoVis) {
        this.secaoAtual = sessaoVis.getSecaoHistorica();
        this.correcaoAtual = sessaoVis.getCorrecaoHistorica();

        // Preenche os campos com os dados daquela época
        preencherCampos(this.secaoAtual);

        // Mostra o feedback daquela época
        String dataFormatada = this.correcaoAtual.getDataCorrecoes() != null ? this.correcaoAtual.getDataCorrecoes().toString() : "N/A";
        txtFeedbackProfessor.setText("VISUALIZAÇÃO DE HISTÓRICO (" + dataFormatada + "):\n" + this.correcaoAtual.getConteudo());

        // Mostra quais checkboxes estavam marcados naquela época
        aplicarStatusDaCorrecao(this.secaoAtual);

        // Bloqueia TUDO (não se edita histórico)
        bloquearTodosOsCampos();
        if (btEnviar != null) btEnviar.setVisible(false); // Remove o botão de enviar

        // Limpa a sessão para que a próxima navegação seja normal
        sessaoVis.limpar();
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

        try {
            Secao novaSecao = new Secao();
            puxarDadosDosCampos(novaSecao);

            novaSecao.setData(LocalDateTime.now());
            novaSecao.setEmailAluno(usuarioLogado.getEmailCadastrado());
            novaSecao.setEmailOrientador(usuarioLogado.getEmailOrientador());
            novaSecao.setIdTG(this.secaoAtual != null && this.secaoAtual.getIdTG() != 0 ? this.secaoAtual.getIdTG() : 1);

            // Copia os status da seção anterior
            if (this.secaoAtual != null) {
                copiarStatus(this.secaoAtual, novaSecao);
            }

            secaoDAO.inserirSecao(novaSecao);

            this.secaoAtual = novaSecao;
            this.correcaoAtual = null;

            exibirAlerta("Sucesso", "Seção enviada com sucesso!", Alert.AlertType.INFORMATION);

            initialize(); // Recarrega a tela para mostrar o novo status

        } catch (NumberFormatException e) {
            exibirAlerta("Erro de Formato", "O campo 'Ano' deve ser um número válido (ex: 2024).", Alert.AlertType.WARNING);
        } catch (Exception e) {
            e.printStackTrace();
            exibirAlerta("Erro ao Salvar", "Ocorreu um erro ao salvar a seção: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void copiarStatus(Secao origem, Secao destino) {
        destino.setIdentificacaoOk(origem.isIdentificacaoOk());
        destino.setEmpresaOk(origem.isEmpresaOk());
        destino.setProblemaOk(origem.isProblemaOk());
        destino.setSolucaoOk(origem.isSolucaoOk());
        destino.setLinkOk(origem.isLinkOk());
        destino.setTecnologiasOk(origem.isTecnologiasOk());
        destino.setContribuicoesOk(origem.isContribuicoesOk());
        destino.setHardskillsOk(origem.isHardskillsOk());
        destino.setSoftskillsOk(origem.isSoftskillsOk());
        destino.setHistProfOk(origem.isHistProfOk());
        destino.setHistAcadOk(origem.isHistAcadOk());
        destino.setMotivacaoOk(origem.isMotivacaoOk());
        destino.setAnoOk(origem.isAnoOk());
        destino.setPeriodoOk(origem.isPeriodoOk());
        destino.setSemestreOk(origem.isSemestreOk());
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
        } else {
            txtAno.clear();
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
        if (rbSemestre1.isSelected()) return '1';
        if (rbSemestre2.isSelected()) return '2';
        return '0';
    }
    private void setPeriodo(char p) {
        // Deseleciona todos primeiro para evitar múltiplos selecionados caso haja bug
        grupoPeriodo.selectToggle(null);
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
        // Deseleciona todos primeiro
        grupoSemestre.selectToggle(null);
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