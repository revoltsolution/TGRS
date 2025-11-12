package org.api.trabalhodegraduacao.controller.usuario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.entities.Usuario;
import org.api.trabalhodegraduacao.utils.SessaoUsuario; // Importa sua classe de Sessão

import java.net.URL;
import java.util.ResourceBundle;

public class AutenticacaoController implements Initializable {

    @FXML private PasswordField txt_Senha;

    private Usuario usuarioDaSessao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SessaoUsuario sessao = SessaoUsuario.getInstance();
        if (sessao.isLogado()) {
            this.usuarioDaSessao = new Usuario();
            this.usuarioDaSessao.setEmailCadastrado(sessao.getEmail());
            this.usuarioDaSessao.setNomeCompleto(sessao.getNome());
            this.usuarioDaSessao.setFuncao(sessao.getFuncao());
            this.usuarioDaSessao.setSenha(sessao.getSenha());
        } else {
            voltarParaLogin(null);
        }
    }

    @FXML
    void btEntrar(ActionEvent event) {
        String senhaDigitada = txt_Senha.getText();
        String senhaCorreta = this.usuarioDaSessao.getSenha();

        if (senhaDigitada != null && senhaDigitada.equals(senhaCorreta)) {
            System.out.println("Login com sucesso! Tipo: " + usuarioDaSessao.getFuncao());
            if ("aluno".equals(usuarioDaSessao.getFuncao())) {
                Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/AtualizacoesAluno.fxml", "Portal do Aluno", event);
            } else if ("professor".equals(usuarioDaSessao.getFuncao())) {
                Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/AtualizacoesProfessor.fxml", "Portal do Professor", event);
            }

        } else {
            System.out.println("Senha incorreta!");
        }
    }

    @FXML
    void btVoltar(ActionEvent event) {
        voltarParaLogin(event);
    }

    private void voltarParaLogin(ActionEvent event) {
        SessaoUsuario.getInstance().limparSessao();
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event);
    }
}