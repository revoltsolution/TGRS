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
    // @FXML private Label lblNomeUsuario; // (Descomente se você tiver um Label para o nome)

    private Usuario usuarioDaSessao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 1. Pega o usuário que o BemVindoController salvou
        SessaoUsuario sessao = SessaoUsuario.getInstance();
        if (sessao.isLogado()) {
            // Recria um objeto Usuario local com os dados da sessão
            this.usuarioDaSessao = new Usuario();
            this.usuarioDaSessao.setEmailCadastrado(sessao.getEmail());
            this.usuarioDaSessao.setNomeCompleto(sessao.getNome());
            this.usuarioDaSessao.setFuncao(sessao.getFuncao());
            this.usuarioDaSessao.setSenha(sessao.getSenha()); // <- Pega a senha correta

            // Ex: if (lblNomeUsuario != null) lblNomeUsuario.setText(usuarioDaSessao.getNomeCompleto());
        } else {
            // Segurança: Se ninguém estiver na sessão, volta ao início
            voltarParaLogin(null);
        }
    }

    @FXML
    void btEntrar(ActionEvent event) {
        String senhaDigitada = txt_Senha.getText();
        String senhaCorreta = this.usuarioDaSessao.getSenha();

        // 2. Compara a senha digitada com a senha da sessão
        if (senhaDigitada != null && senhaDigitada.equals(senhaCorreta)) {
            // Login com sucesso!
            System.out.println("Login com sucesso! Tipo: " + usuarioDaSessao.getFuncao());

            // 3. Redireciona com base no tipo (funcao) do usuário
            if ("aluno".equals(usuarioDaSessao.getFuncao())) {
                Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/AtualizacoesAluno.fxml", "Portal do Aluno", event);
            } else if ("professor".equals(usuarioDaSessao.getFuncao())) {
                Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/AtualizacoesProfessor.fxml", "Portal do Professor", event);
            }

        } else {
            // Senha incorreta
            System.out.println("Senha incorreta!");
            // (Aqui você deve mostrar um Label de erro para o usuário)
        }
    }

    @FXML
    void btVoltar(ActionEvent event) {
        voltarParaLogin(event);
    }

    private void voltarParaLogin(ActionEvent event) {
        // Limpa a sessão e volta para a tela de BemVindo
        SessaoUsuario.getInstance().limparSessao();
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event);
    }
}