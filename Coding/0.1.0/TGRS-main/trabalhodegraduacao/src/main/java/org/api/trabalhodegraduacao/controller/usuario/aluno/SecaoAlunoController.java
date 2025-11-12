package org.api.trabalhodegraduacao.controller.usuario.aluno;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.bancoDeDados.ConexaoDB;
import org.api.trabalhodegraduacao.dao.SecaoDAO;
import org.api.trabalhodegraduacao.entities.Secao;
import org.api.trabalhodegraduacao.entities.Usuario;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class SecaoAlunoController {

    @FXML private TextField txtIdentificacaoProjeto;
    @FXML private TextField txtEmpresaParceira;
    @FXML private TextArea txtProblema;
    @FXML private TextArea txtSolucao;
    @FXML private TextField txtLinkRepositorio;
    @FXML private TextArea txtTecnologiasUtilizadas;
    @FXML private TextArea txtContribuicoesPessoais;
    @FXML private TextArea txtDescricaoHard;
    @FXML private TextArea txtDescricaoSoft;
    @FXML private TextArea txtHistoricoProfissional;
    @FXML private TextArea txtHistoricoAcademico;
    @FXML private TextArea txtMotivacao;
    @FXML private TextField txtAno;
    @FXML private CheckBox cbPeriodo1;
    @FXML private CheckBox cbPeriodo2;
    @FXML private CheckBox cbPeriodo3;
    @FXML private CheckBox cbPeriodo4;
    @FXML private CheckBox cbPeriodo5;
    @FXML private CheckBox cbPeriodo6;
    @FXML private CheckBox cbSemestre1;
    @FXML private CheckBox cbSemestre2;
    @FXML private TextField txtIdTG;

    @FXML
    public void initialize() {
        configurarSelecaoUnica(cbPeriodo1, cbPeriodo2, cbPeriodo3, cbPeriodo4, cbPeriodo5, cbPeriodo6);
        configurarSelecaoUnica(cbSemestre1, cbSemestre2);
    }

    private void configurarSelecaoUnica(CheckBox... boxes) {
        List<CheckBox> boxList = Arrays.asList(boxes);
        for (CheckBox box : boxList) {
            box.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    for (CheckBox b : boxList) { if (b != box) b.setSelected(false); }
                }
            });
        }
    }

    @FXML public void sair(ActionEvent event) { SessaoUsuario.getInstance().limparSessao(); Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event); }
    @FXML public void perfilAluno (ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/PerfilAluno.fxml", "Perfil Aluno", event); }
    @FXML public void secaoGeral(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/SecaoAluno.fxml", "Secao Geral", event); }
    @FXML public void tgGeral(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/TGAluno.fxml", "TG Aluno", event); }
    @FXML public void telaInicial(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/AtualizacoesAluno.fxml", "Tela Inicial", event); }
    @FXML void devolutivasGeral(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/DevolutivasAluno.fxml", "TGRS - Devolutivas", event); }

    @FXML
    void enviarSecao(ActionEvent event) {
        try (Connection connection = ConexaoDB.getConexao()) {
            SecaoDAO secaoDAO = new SecaoDAO(connection);
            SessaoUsuario sessao = SessaoUsuario.getInstance();

            Usuario alunoLogado = secaoDAO.buscarUsuarioPorEmail(sessao.getEmail());
            if (alunoLogado == null || alunoLogado.getEmailOrientador() == null || alunoLogado.getEmailOrientador().trim().isEmpty()) {
                mostrarAlerta("Erro Crítico: Seu cadastro não possui um Orientador vinculado.", Alert.AlertType.ERROR);
                return;
            }
            String emailOrientadorValido = alunoLogado.getEmailOrientador();

            int ano = LocalDate.now().getYear();
            if (txtAno != null && !txtAno.getText().isEmpty()) {
                try { ano = Integer.parseInt(txtAno.getText()); }
                catch (NumberFormatException e) { mostrarAlerta("O campo 'Ano' deve conter apenas números.", Alert.AlertType.WARNING); return; }
            }

            char periodo = '0';
            if (cbPeriodo1.isSelected()) periodo = '1';
            else if (cbPeriodo2.isSelected()) periodo = '2';
            else if (cbPeriodo3.isSelected()) periodo = '3';
            else if (cbPeriodo4.isSelected()) periodo = '4';
            else if (cbPeriodo5.isSelected()) periodo = '5';
            else if (cbPeriodo6.isSelected()) periodo = '6';

            if (periodo == '0') { mostrarAlerta("Por favor, selecione o Período (Etapa do Curso).", Alert.AlertType.WARNING); return; }

            char semestre = '0';
            if (cbSemestre1.isSelected()) semestre = '1';
            else if (cbSemestre2.isSelected()) semestre = '2';

            if (semestre == '0') { mostrarAlerta("Por favor, selecione o Semestre do ano (1º ou 2º).", Alert.AlertType.WARNING); return; }

            int idTG = (txtIdTG != null && !txtIdTG.getText().isEmpty()) ? Integer.parseInt(txtIdTG.getText()) : 0;

            Secao secao = new Secao(
                    0,
                    txtIdentificacaoProjeto.getText(),
                    txtEmpresaParceira.getText(),
                    txtProblema.getText(),
                    txtSolucao.getText(),
                    txtLinkRepositorio.getText(),
                    txtTecnologiasUtilizadas.getText(),
                    txtContribuicoesPessoais.getText(),
                    txtDescricaoSoft.getText(),
                    txtDescricaoHard.getText(),
                    txtHistoricoProfissional != null ? txtHistoricoProfissional.getText() : "",
                    txtHistoricoAcademico != null ? txtHistoricoAcademico.getText() : "",
                    txtMotivacao != null ? txtMotivacao.getText() : "",
                    ano,
                    periodo,
                    semestre,
                    // MUDANÇA CRÍTICA AQUI: Usando Timestamp para pegar HH:mm:ss
                    new java.sql.Timestamp(System.currentTimeMillis()),
                    idTG,
                    sessao.getEmail(),
                    emailOrientadorValido
            );

            secaoDAO.inserir(secao);
            mostrarAlerta("Seção enviada com sucesso!", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro ao enviar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Envio de Seção");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}