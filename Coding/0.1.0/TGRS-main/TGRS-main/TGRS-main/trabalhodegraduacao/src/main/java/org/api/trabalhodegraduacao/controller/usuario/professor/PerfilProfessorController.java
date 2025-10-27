package org.api.trabalhodegraduacao.controller.usuario.professor;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.api.trabalhodegraduacao.dao.Sessao;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
import org.api.trabalhodegraduacao.entities.Usuario;

import java.sql.SQLException;

public class PerfilProfessorController {

    private Usuario usuarioLogado; // Armazena o objeto do usuário (com o email original)
    private UsuarioDAO usuarioDAO = new UsuarioDAO();


    public TextField txtNome;
    public TextField txtLinkedin;
    public TextField txtGitHub;
    public TextField txtSenha;

    public Label lblEmailCadastrado;

    public RadioButton radioBTMasculino;
    public RadioButton radioBtFeminino;

    public Button btnEditarPerfil;
    public Button btnSalvar;

    public void atualizar() {

        Usuario usuarioParaAtualizar = Sessao.getUsuarioLogado();


        if (usuarioParaAtualizar == null) {
            System.err.println("Nenhum usuário está logado. Retornando para o Login.");

            return;
        }

        try {

            usuarioParaAtualizar.setNomeCompleto(txtNome.getText());
            usuarioParaAtualizar.setLinkedin(txtLinkedin.getText());
            usuarioParaAtualizar.setGitHub(txtGitHub.getText());
            usuarioParaAtualizar.setSenha(txtSenha.getText());





            // ... (Continue atualizando todos os campos que foram alterados na tela) ...

            // 4. Chamar o DAO (o DAO usará o Email/PK que já está no objeto)
            usuarioDAO.atualizar(usuarioParaAtualizar);

            System.out.println("Dados salvos com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
