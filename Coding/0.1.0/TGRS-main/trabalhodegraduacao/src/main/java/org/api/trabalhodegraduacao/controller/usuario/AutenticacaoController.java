package org.api.trabalhodegraduacao.controller.usuario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;



public class AutenticacaoController {

    @FXML private TextField txt_Senha;

    @FXML
    void btEntrar(ActionEvent event) {

        String senhaDigitada = txt_Senha.getText();

        String senhaCorreta = SessaoUsuario.getInstance().getSenha();

        if (senhaCorreta == null) {
            System.err.println("Erro: A sessão do usuário está vazia. Voltando ao início.");
            btVoltar(event);
            return;
        }

        if (senhaDigitada.equals(senhaCorreta)) {
            String tipo = SessaoUsuario.getInstance().getFuncao();
            System.out.println("Login com sucesso! Tipo: " + tipo);

            if ("professor".equals(tipo)) {
                Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/AtualizacoesProfessor.fxml", "Atualizações", event);
            } else if ("aluno".equals(tipo)) {
                Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/AtualizacoesAluno.fxml", "Atualizações", event);
            }

        } else {
            System.err.println("Senha incorreta!");
        }
    }

    @FXML
    void btVoltar(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-Vindo", event);
    }
    public void imvUsuario() {

    }
}