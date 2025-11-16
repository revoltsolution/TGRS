package org.api.trabalhodegraduacao.controller.usuario.aluno;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar; // Importe
import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.dao.SecaoDAO; // Importe
import org.api.trabalhodegraduacao.dao.UsuarioDAO; // Importe
import org.api.trabalhodegraduacao.entities.Usuario; // Importe
import org.api.trabalhodegraduacao.utils.SessaoUsuario; // Importe

import java.sql.SQLException;

public class TGAlunoController {

    // --- FXML Barra Lateral ---
    @FXML private Button bt_Sair, bt_devolutivas_geral, bt_perfil_geral, bt_secao_geral, bt_tela_inicial, bt_tg_geral;

    // --- FXML Barras de Progresso ---
    @FXML private ProgressBar pbSecao1;
    @FXML private ProgressBar pbSecao2;
    @FXML private ProgressBar pbSecao3;
    @FXML private ProgressBar pbSecao4;
    @FXML private ProgressBar pbSecao5;
    @FXML private ProgressBar pbSecao6;

    // --- DAOs e Dados ---
    private UsuarioDAO usuarioDAO;
    private SecaoDAO secaoDAO;
    private Usuario usuarioLogado;

    @FXML
    public void initialize() {
        this.usuarioDAO = new UsuarioDAO();
        this.secaoDAO = new SecaoDAO();

        SessaoUsuario sessao = SessaoUsuario.getInstance();
        if (sessao.isLogado()) {
            try {
                // 1. Carrega o usuário logado para pegar o email do orientador
                this.usuarioLogado = usuarioDAO.exibirPerfil(sessao.getEmail());

                if (usuarioLogado != null && usuarioLogado.getEmailOrientador() != null) {
                    // 2. Carrega o progresso de cada seção
                    carregarProgresso();
                } else {
                    System.err.println("Aluno não encontrado ou sem orientador. Barras de progresso não serão carregadas.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // (Mostrar Alerta de erro)
            }
        }
    }

    /**
     * Busca no DAO o progresso de cada seção e atualiza as barras.
     */
    private void carregarProgresso() throws SQLException {
        String emailAluno = usuarioLogado.getEmailCadastrado();
        String emailOrientador = usuarioLogado.getEmailOrientador();

        // Busca o progresso de cada TG (assumindo que os IDs são de 1 a 6)
        pbSecao1.setProgress(secaoDAO.buscarProgressoSecao(1, emailAluno, emailOrientador));
        pbSecao2.setProgress(secaoDAO.buscarProgressoSecao(2, emailAluno, emailOrientador));
        pbSecao3.setProgress(secaoDAO.buscarProgressoSecao(3, emailAluno, emailOrientador));
        pbSecao4.setProgress(secaoDAO.buscarProgressoSecao(4, emailAluno, emailOrientador));
        pbSecao5.setProgress(secaoDAO.buscarProgressoSecao(5, emailAluno, emailOrientador));
        pbSecao6.setProgress(secaoDAO.buscarProgressoSecao(6, emailAluno, emailOrientador));
    }

    // --- Métodos de Navegação ---
    @FXML void sair(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event); }
    @FXML void perfilAluno (ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/PerfilAluno.fxml", "Perfil Aluno", event); }
    @FXML void secaoGeral(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/SecaoAluno.fxml", "Seção", event); }
    @FXML void tgGeral(ActionEvent event) { System.out.println("Já está na tela de TG."); }
    @FXML void telaInicial(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/AtualizacoesAluno.fxml", "Tela Inicial", event); }
    @FXML void devolutivasGeral(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/DevolutivasAluno.fxml", "TGRS - Devolutivas", event); }
}