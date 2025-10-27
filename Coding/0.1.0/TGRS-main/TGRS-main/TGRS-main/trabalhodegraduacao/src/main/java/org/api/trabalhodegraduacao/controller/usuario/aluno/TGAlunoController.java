package org.api.trabalhodegraduacao.controller.usuario.aluno;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TGAlunoController {

    @FXML
    private TextField campoTG;

    @FXML
    private Label labelFeedback;


    private static final String ID_ALUNO_LOGADO = "samir@kassen.com";


    private String getNomeArquivoAluno() {
        return "TG_" + ID_ALUNO_LOGADO + ".csv";
    }

    @FXML
    private void salvarTG() {
        String valorTG = campoTG.getText().trim();

        if (valorTG.isEmpty()) {
            labelFeedback.setText("ERRO: O campo TG não pode estar vazio.");
            return;
        }


        String nomeArquivo = getNomeArquivoAluno();
        String delimitador = ";";
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        String linhaDados = timestamp + delimitador + valorTG;

        try {
            File arquivo = new File(nomeArquivo);
            boolean arquivoExiste = arquivo.exists() && arquivo.length() > 0;

            try (FileWriter fileWriter = new FileWriter(arquivo, true);
                 PrintWriter printWriter = new PrintWriter(fileWriter)) {

                if (!arquivoExiste) {
                    printWriter.println("Data/Hora;Valor_TG");
                }

                printWriter.println(linhaDados);

                labelFeedback.setText("TG Salvo em " + nomeArquivo + ": " + valorTG);
                campoTG.clear();

            }

        } catch (IOException e) {
            labelFeedback.setText("ERRO de I/O ao salvar o arquivo.");
            System.err.println("Erro ao salvar no arquivo CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }
}