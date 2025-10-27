package org.api.trabalhodegraduacao.controller.usuario.aluno;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import org.api.trabalhodegraduacao.dao.Sessao;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
import org.api.trabalhodegraduacao.entities.Usuario;

import java.sql.SQLException;

public class PerfilAlunoController {

    private Usuario usuarioLogado; // Armazena o objeto do usuário (com o email original)
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public TextField txtNome;
    public TextField txtLinkedin;
    public TextField txtGitHub;
    public TextField txtSenha;

    public Label lblEmailCadastrado;

    public RadioButton radioBt_Masculino;
    public RadioButton radioBt_Feminino;

    public Button bt_EditarPerfil;
    public Button bt_SalvarPerfil;

    public ImageView imgVwFotoPerfil;
    public ChoiceBox choiceOrientador;

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;


        UsuarioDAO dao = new UsuarioDAO();


        txtNome.setText(usuario.getNomeCompleto());

    }

    public void atualizar() {

        Usuario usuarioParaAtualizar = Sessao.getUsuarioLogado();


        if (usuarioParaAtualizar == null) {
            System.err.println("Nenhum usuário está logado. Retornando para o Login.");
            // Lógica para forçar logout ou voltar ao login
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




