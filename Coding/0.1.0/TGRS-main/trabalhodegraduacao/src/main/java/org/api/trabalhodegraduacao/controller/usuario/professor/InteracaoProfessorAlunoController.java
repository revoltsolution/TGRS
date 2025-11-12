package org.api.trabalhodegraduacao.controller.usuario.professor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.api.trabalhodegraduacao.dao.SecaoDAO;
import org.api.trabalhodegraduacao.entities.Secao;
import org.api.trabalhodegraduacao.bancoDeDados.ConexaoDB; // Importação correta

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class InteracaoProfessorAlunoController {

    @FXML
    private TableView<Secao> tabelaSecoes;

    @FXML
    private TableColumn<Secao, String> colProjeto, colAluno, colOrientador;

    @FXML
    private TextArea txtDetalhes;

    @FXML
    private Button btAtualizar, btEnviarFeedback;

    private SecaoDAO secaoDAO;

    private ObservableList<Secao> secoesObservable;

    @FXML
    public void initialize() {
        colProjeto.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getIdentificacaoProjeto()));
        colAluno.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmailAluno()));
        colOrientador.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmailOrientador()));

        carregarSecoes();

        tabelaSecoes.setOnMouseClicked(this::mostrarDetalhes);
    }

    private void carregarSecoes() {
        try (Connection conn = ConexaoDB.getConexao()) {
            secaoDAO = new SecaoDAO(conn);
            List<Secao> secoes = secaoDAO.listarTodas();
            secoesObservable = FXCollections.observableArrayList(secoes);
            tabelaSecoes.setItems(secoesObservable);
        } catch (SQLException e) {
            exibirErro("Erro ao carregar seções: " + e.getMessage());
        }
    }

    private void mostrarDetalhes(MouseEvent event) {
        Secao selecionada = tabelaSecoes.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            txtDetalhes.setText(
                    "Projeto: " + selecionada.getIdentificacaoProjeto() + "\n\n" +
                            "Problema: " + selecionada.getProblema() + "\n\n" +
                            "Solução: " + selecionada.getSolucao() + "\n\n" +
                            "Empresa Parceira: " + selecionada.getEmpresaParceira() + "\n\n" +
                            "Aluno: " + selecionada.getEmailAluno() + "\n" +
                            "Orientador: " + selecionada.getEmailOrientador()
            );
        }
    }

    @FXML
    private void atualizarLista(ActionEvent event) {
        carregarSecoes();
    }

    @FXML
    private void enviarFeedback(ActionEvent event) {
        Secao selecionada = tabelaSecoes.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            exibirErro("Selecione uma seção antes de enviar o feedback.");
            return;
        }

        exibirInfo("Feedback enviado com sucesso para o aluno " + selecionada.getEmailAluno());
    }

    private void exibirErro(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Erro");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void exibirInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Sucesso");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}