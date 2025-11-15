package org.api.trabalhodegraduacao.controller.usuario.professor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*; // Importe tudo de .control
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
import org.api.trabalhodegraduacao.entities.Usuario;
import org.api.trabalhodegraduacao.utils.AlunoSelecionado;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;

import java.sql.SQLException;
import java.util.List;

public class AlunosController {

    // --- FXML Barra Lateral ---
    @FXML private Button bt_Sair, bt_alunos_geral, bt_devolutivas_geral, bt_perfil_geral, bt_tela_inicial;

    // --- FXML da Tabela ---
    @FXML private TableView<Usuario> tabelaAlunos;
    @FXML private TableColumn<Usuario, String> colNome;
    @FXML private TableColumn<Usuario, String> colTG;
    @FXML private TableColumn<Usuario, String> colSecao;

    // --- MUDANÇA AQUI: O tipo agora é Double ---
    @FXML private TableColumn<Usuario, Double> colProgresso;

    private UsuarioDAO usuarioDAO;

    @FXML
    void initialize() {
        this.usuarioDAO = new UsuarioDAO();

        // 1. Configura a coluna "NOME"
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));

        // --- MUDANÇA AQUI: Configura a coluna "PROGRESSO" ---

        // 2. Linka a coluna ao getter "getProgresso()" (que retorna um Double 0.0-1.0)
        colProgresso.setCellValueFactory(new PropertyValueFactory<>("progresso"));

        // 3. (Opcional) Mostra uma barra de progresso em vez de um número
        colProgresso.setCellFactory(column -> {
            return new TableCell<Usuario, Double>() {
                private final ProgressBar pb = new ProgressBar();

                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        pb.setProgress(item); // 'item' é o double (0.0 a 1.0)

                        // Reutiliza o estilo que já criamos para a tela do aluno
                        pb.getStyleClass().add("progress-bar-custom");
                        pb.setMaxWidth(Double.MAX_VALUE); // Faz a barra preencher a célula
                        setGraphic(pb);
                    }
                }
            };
        });
        // --- FIM DA MUDANÇA ---

        // 4. Carrega os dados (agora com o progresso)
        carregarAlunosDaTabela();

        // 5. Adiciona o clique-duplo
        tabelaAlunos.setOnMouseClicked(this::handleRowClick);
    }

    // ... (O resto da sua classe AlunosController) ...

    /**
     * Chamado quando uma linha da tabela é clicada (clique-duplo).
     */
    private void handleRowClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Usuario alunoSelecionado = tabelaAlunos.getSelectionModel().getSelectedItem();

            if (alunoSelecionado != null) {
                AlunoSelecionado.getInstance().setAluno(alunoSelecionado);

                System.out.println("Abrindo perfil do aluno: " + alunoSelecionado.getNomeCompleto());
                Application.carregarNovaCena(
                        "/org/api/trabalhodegraduacao/view/usuario/professor/InteracaoProfessorAluno.fxml",
                        "Perfil de " + alunoSelecionado.getNomeCompleto(),
                        new ActionEvent(event.getSource(), event.getTarget())
                );
            }
        }
    }

    private void carregarAlunosDaTabela() {
        SessaoUsuario sessao = SessaoUsuario.getInstance();
        if (!sessao.isLogado()) {
            System.err.println("Professor não está logado!");
            return;
        }

        try {
            String emailProfessor = sessao.getEmail();
            List<Usuario> alunos = usuarioDAO.buscarAlunosPorOrientador(emailProfessor);
            ObservableList<Usuario> listaObservavel = FXCollections.observableArrayList(alunos);
            tabelaAlunos.setItems(listaObservavel);

        } catch (SQLException e) {
            System.err.println("Erro de SQL ao buscar alunos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- Métodos de Navegação ---
    @FXML void alunos(ActionEvent event) { System.out.println("Já está na tela de Alunos."); }
    @FXML void devolutivas(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/Historico.fxml", "Devolutivas", event); }
    @FXML void perfilProfessor(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/PerfilProfessor.fxml", "Perfil", event); }
    @FXML void sair(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event); }
    @FXML void telaInicial(ActionEvent event) { Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/professor/AtualizacoesProfessor.fxml", "Tela Inicial", event); }
}