package org.api.trabalhodegraduacao.controller.usuario.professor;

import org.api.trabalhodegraduacao.tg.TGRegistro; // Certifique-se de que este pacote está correto
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HistoricoController {


    @FXML
    private ListView<String> listaAlunos; // Lista de alunos na lateral

    @FXML
    private TableView<TGRegistro> tabelaRegistros;

    @FXML
    private TableColumn<TGRegistro, String> colunaDataHora;

    @FXML
    private TableColumn<TGRegistro, String> colunaValorTG;

    @FXML
    private Label labelStatus;


    private static final String ARQUIVO_PREFIXO = "TG_";
    private static final String ARQUIVO_SUFIXO = ".csv";
    private static final String DELIMITADOR = ";";


    private final Map<String, String> mapaAlunos = new HashMap<>();

    @FXML
    public void initialize() {

        colunaDataHora.setCellValueFactory(cellData -> cellData.getValue().dataHoraProperty());
        colunaValorTG.setCellValueFactory(cellData -> cellData.getValue().valorTGProperty());


        preencherListaAlunos();


        listaAlunos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                String alunoID = mapaAlunos.get(newValue);
                carregarDadosCSV(alunoID);
            }
        });

        labelStatus.setText("Selecione um aluno na lista para visualizar o histórico de TG.");
    }


    private void preencherListaAlunos() {

        mapaAlunos.put("samir", "samir@kassen.com");


        ObservableList<String> nomesAlunos = FXCollections.observableArrayList(mapaAlunos.keySet());
        listaAlunos.setItems(nomesAlunos);
    }


    private void carregarDadosCSV(String alunoID) {
        String nomeArquivo = ARQUIVO_PREFIXO + alunoID + ARQUIVO_SUFIXO;
        ObservableList<TGRegistro> dados = FXCollections.observableArrayList();

        labelStatus.setText("Carregando dados para " + alunoID + "...");

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {

            // Pular o cabeçalho
            br.readLine();

            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] valores = linha.split(DELIMITADOR);

                if (valores.length >= 2) {
                    dados.add(new TGRegistro(valores[0], valores[1]));
                }
            }

            tabelaRegistros.setItems(dados);
            labelStatus.setText("Histórico de TG de " + alunoID + " carregado com " + dados.size() + " registros.");

        } catch (IOException e) {
            tabelaRegistros.setItems(FXCollections.emptyObservableList());
            labelStatus.setText("ERRO: Arquivo de histórico '" + nomeArquivo + "' não encontrado ou inacessível.");
            System.err.println("Erro ao ler o arquivo CSV: " + e.getMessage());
        }
    }

    @FXML
    private void recarregarDados() {
        String alunoSelecionadoNome = listaAlunos.getSelectionModel().getSelectedItem();
        if (alunoSelecionadoNome != null) {
            String alunoID = mapaAlunos.get(alunoSelecionadoNome);
            carregarDadosCSV(alunoID);
        } else {
            labelStatus.setText("Selecione um aluno para recarregar os dados.");
        }
    }
}