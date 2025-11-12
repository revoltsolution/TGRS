package org.api.trabalhodegraduacao.controller.usuario.aluno;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.bancoDeDados.ConexaoDB;
import org.api.trabalhodegraduacao.dao.SecaoDAO;
import org.api.trabalhodegraduacao.entities.Secao;
import org.api.trabalhodegraduacao.entities.Usuario;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;

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

    @FXML private TextField txtHistoricoProfissional;
    @FXML private TextField txtHistoricoAcademico;
    @FXML private TextField txtMotivacao;
    @FXML private TextField txtAno;
    @FXML private TextField txtPeriodo;
    @FXML private TextField txtSemestre;
    @FXML private TextField txtIdTG;


    @FXML
    public void sair(ActionEvent event) {
        SessaoUsuario.getInstance().limparSessao();
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event);
    }

    @FXML
    public void perfilAluno (ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/PerfilAluno.fxml", "Perfil Aluno", event);
    }

    @FXML
    public void secaoGeral(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/SecaoAluno.fxml", "Secao Geral", event);
    }

    @FXML
    public void tgGeral(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/TGAluno.fxml", "TG Aluno", event);
    }

    @FXML
    public void telaInicial(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/AtualizacoesAluno.fxml", "Tela Inicial", event);
    }

    @FXML
    void devolutivasGeral(ActionEvent event) {
        String fxmlPath = "/org/api/trabalhodegraduacao/view/usuario/aluno/DevolutivasAluno.fxml";
        String title = "TGRS - Devolutivas";
        Application.carregarNovaCena(fxmlPath, title, event);
    }

    @FXML
    void enviarSecao(ActionEvent event) {
        try (Connection connection = ConexaoDB.getConexao()) {

            SecaoDAO secaoDAO = new SecaoDAO(connection);
            SessaoUsuario sessao = SessaoUsuario.getInstance();

            Usuario alunoLogado = secaoDAO.buscarUsuarioPorEmail(sessao.getEmail());

            if (alunoLogado == null || alunoLogado.getEmailOrientador() == null || alunoLogado.getEmailOrientador().trim().isEmpty()) {
                mostrarAlerta("Erro Crítico: Seu cadastro não possui um Orientador vinculado. Contate o administrador.", Alert.AlertType.ERROR);
                return;
            }

            String emailOrientadorValido = alunoLogado.getEmailOrientador();

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
                    Integer.parseInt(txtAno != null && !txtAno.getText().isEmpty() ? txtAno.getText() : "0"),
                    txtPeriodo != null && !txtPeriodo.getText().isEmpty() ? txtPeriodo.getText().charAt(0) : 'U',
                    txtSemestre != null && !txtSemestre.getText().isEmpty() ? txtSemestre.getText().charAt(0) : 'U',
                    Date.valueOf(LocalDate.now()),
                    Integer.parseInt(txtIdTG != null && !txtIdTG.getText().isEmpty() ? txtIdTG.getText() : "0"),
                    sessao.getEmail(),
                    emailOrientadorValido
            );

            secaoDAO.inserir(secao);

            mostrarAlerta("Seção enviada com sucesso!", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de preenchimento: Ano e ID do TG devem ser números válidos.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro ao enviar a seção: " + e.getMessage(), Alert.AlertType.ERROR);
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