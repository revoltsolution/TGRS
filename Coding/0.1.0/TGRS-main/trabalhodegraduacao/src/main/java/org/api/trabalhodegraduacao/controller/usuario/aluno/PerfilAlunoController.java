package org.api.trabalhodegraduacao.controller.usuario.aluno;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.api.trabalhodegraduacao.Application;
import org.api.trabalhodegraduacao.dao.UsuarioDAO;
import org.api.trabalhodegraduacao.entities.Usuario;
import org.api.trabalhodegraduacao.utils.SessaoUsuario;
// import org.api.trabalhodegraduacao.model.Aluno;
// import org.api.trabalhodegraduacao.service.SessaoService;

public class PerfilAlunoController {

    // --- Variáveis de Estado ---
    private boolean isEditMode = false;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // --- Variáveis de Serviço e Dados ---
    private Usuario usuarioLogado; // Guarda o usuário carregado do banco
    private UsuarioDAO usuarioDAO; // Instância do DAO para comunicar com o banco

    // --- Componentes FXML ---
    @FXML private Button bt_EditarSalvar;
    @FXML private Button bt_Sair;
    @FXML private Button bt_TrocarFotoPerfil;
    @FXML private Button bt_devolutivas_geral;
    @FXML private Button bt_perfil_geral;
    @FXML private Button bt_secao_geral;
    @FXML private Button bt_tela_inicial;
    @FXML private Button bt_tg_geral;
    @FXML private ImageView imgVwFotoPerfil;

    // Campos de Dados (Não Editáveis)
    @FXML private Label lbl_NomeUsuario;
    @FXML private Label lblEmailCadastrado;
    @FXML private Label lblOrientador;

    // Campos de Dados (Editáveis - Labels de Visualização)
    @FXML private Label lblCurso;
    @FXML private Label lblDataNascimento; // NOVO
    @FXML private Label lblLinkedin;
    @FXML private Label lblGitHub;
    @FXML private Label lblSenha;

    // Campos de Dados (Editáveis - Campos de Edição)
    @FXML private TextField txtCurso;
    @FXML private DatePicker dpDataNascimento; // NOVO
    @FXML private TextField txtLinkedin;
    @FXML private TextField txtGitHub;
    @FXML private PasswordField txtSenha;


    @FXML
    void initialize() {
        // 1. Inicializa o DAO (agora como variável da classe)
        this.usuarioDAO = new UsuarioDAO();

        // 2. Pega o e-mail da sessão
        SessaoUsuario sessao = SessaoUsuario.getInstance();
        if (sessao.isLogado()) {
            String emailDoUsuarioLogado = sessao.getEmail();

            // 3. CARREGA os dados do banco
            try {
                // Usamos o método 'exibirPerfil' do SEU DAO
                this.usuarioLogado = usuarioDAO.exibirPerfil(emailDoUsuarioLogado);

                // 4. Preenche os labels com os dados carregados
                if (this.usuarioLogado != null) {
                    preencherLabelsComDados();
                } else {
                    // Tratar caso o usuário não seja encontrado no banco
                    System.err.println("Usuário da sessão não encontrado no banco.");
                    // (Talvez mostrar um alerta e voltar para o login)
                }

            } catch (SQLException e) {
                // Tratar erro de banco de dados (ex: mostrar um alerta)
                System.err.println("Erro ao carregar perfil: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            // Lógica para caso não tenha ninguém logado
            System.err.println("Erro: Nenhum usuário na sessão.");
            // (Talvez redirecionar para o login)
        }

        // 5. Configura o modo de visualização inicial
        setViewMode(true); // true = View Mode
    }

    /**
     * Método auxiliar para preencher os labels com os dados do objeto 'usuarioLogado'.
     * Isso centraliza a lógica de exibição.
     */
    private void preencherLabelsComDados() {
        // Campos fixos (não editáveis)
        lbl_NomeUsuario.setText(usuarioLogado.getNomeCompleto());

        // *** CORREÇÃO 1 ***: O método na sua entidade é 'getEmailCadastrado'
        lblEmailCadastrado.setText(usuarioLogado.getEmailCadastrado());

        // TODO: Você precisa implementar a lógica para buscar e setar o nome do orientador
        String nomeOrientador = usuarioLogado.getNomeOrientador();
        if (nomeOrientador != null && !nomeOrientador.isEmpty()) {
            lblOrientador.setText(nomeOrientador);
        } else {
            lblOrientador.setText("(Não definido)");
        }
        // Campos editáveis (Labels de visualização)
        lblCurso.setText(getTextoOuPadrao(usuarioLogado.getCurso()));
        lblLinkedin.setText(getTextoOuPadrao(usuarioLogado.getLinkedin()));
        lblGitHub.setText(getTextoOuPadrao(usuarioLogado.getGitHub()));
        lblSenha.setText("**********"); // Sempre mostrar asteriscos

        if (usuarioLogado.getDataNascimento() != null) {
            // *** CORREÇÃO 2 ***: 'getDataNascimento()' já retorna um LocalDate.
            LocalDate dataNasc = usuarioLogado.getDataNascimento(); // <--- CORRIGIDO
            lblDataNascimento.setText(dataNasc.format(dateFormatter));
        } else {
            lblDataNascimento.setText("(Não informado)");
        }
    }

    /**
     * Método utilitário para evitar que "null" ou "" apareça na interface.
     */
    private String getTextoOuPadrao(String texto) {
        return (texto != null && !texto.trim().isEmpty()) ? texto : "(Não informado)";
    }


    private void setViewMode(boolean viewMode) {
        // Alterna os Labels
        lblCurso.setVisible(viewMode);
        lblDataNascimento.setVisible(viewMode);
        lblLinkedin.setVisible(viewMode);
        lblGitHub.setVisible(viewMode);
        lblSenha.setVisible(viewMode);

        lblCurso.setManaged(viewMode);
        lblDataNascimento.setManaged(viewMode);
        lblLinkedin.setManaged(viewMode);
        lblGitHub.setManaged(viewMode);
        lblSenha.setManaged(viewMode);

        // Alterna os Campos de Edição
        txtCurso.setVisible(!viewMode);
        dpDataNascimento.setVisible(!viewMode);
        txtLinkedin.setVisible(!viewMode);
        txtGitHub.setVisible(!viewMode);
        txtSenha.setVisible(!viewMode);

        txtCurso.setManaged(!viewMode);
        dpDataNascimento.setManaged(!viewMode);
        txtLinkedin.setManaged(!viewMode);
        txtGitHub.setManaged(!viewMode);
        txtSenha.setManaged(!viewMode);

        // Alterna o botão de trocar foto
        bt_TrocarFotoPerfil.setVisible(!viewMode);
        bt_TrocarFotoPerfil.setManaged(!viewMode);
    }

    @FXML
    void onToggleEditSave(ActionEvent event) {
        if (isEditMode) {
            // --- MODO SALVAR ---
            try {
                // 1. Atualizar o objeto 'usuarioLogado' com os dados dos campos de texto
                usuarioLogado.setCurso(txtCurso.getText());
                usuarioLogado.setLinkedin(txtLinkedin.getText());
                usuarioLogado.setGitHub(txtGitHub.getText());

                if (dpDataNascimento.getValue() != null) {
                    // *** CORREÇÃO 3 ***: O setter 'setDataNascimento' espera um 'LocalDate'
                    usuarioLogado.setDataNascimento(dpDataNascimento.getValue()); // <--- CORRIGIDO
                } else {
                    usuarioLogado.setDataNascimento(null);
                }

                // Lógica para senha: só atualiza se o campo não estiver vazio
                String novaSenha = txtSenha.getText();
                if (novaSenha != null && !novaSenha.trim().isEmpty()) {
                    // (Idealmente, você deve criptografar a senha aqui antes de salvar)
                    usuarioLogado.setSenha(novaSenha);
                }
                // Se estiver vazio, a senha antiga (já no objeto 'usuarioLogado') é mantida

                // 2. !! PONTO IMPORTANTE: Mandar o DAO salvar no banco !!
                usuarioDAO.atualizar(this.usuarioLogado); // Usando o método 'atualizar' do SEU DAO

                // 3. Atualizar os Labels (puxando do objeto que acabamos de salvar)
                preencherLabelsComDados();

                // 4. Trocar para o modo de visualização
                setViewMode(true);
                bt_EditarSalvar.setText("Editar Perfil");
                bt_EditarSalvar.getStyleClass().setAll("profile-button-secondary");
                isEditMode = false;

            } catch (SQLException e) {
                // Mostrar um alerta para o usuário sobre o erro de salvamento
                System.err.println("Erro ao salvar perfil: " + e.getMessage());
                e.printStackTrace();
                // (Aqui você deve mostrar um Alert para o usuário)
            }

        } else {
            // --- MODO EDITAR ---
            // 1. Copiar dados do objeto 'usuarioLogado' para os TextFields
            txtCurso.setText(usuarioLogado.getCurso());
            txtLinkedin.setText(usuarioLogado.getLinkedin());
            txtGitHub.setText(usuarioLogado.getGitHub());
            txtSenha.clear(); // Sempre limpar a senha

            if (usuarioLogado.getDataNascimento() != null) {
                // *** CORREÇÃO 4 ***: 'getDataNascimento()' já retorna um LocalDate.
                dpDataNascimento.setValue(usuarioLogado.getDataNascimento()); // <--- CORRIGIDO
            } else {
                dpDataNascimento.setValue(null);
            }

            // 2. Trocar para o modo de edição
            setViewMode(false);
            bt_EditarSalvar.setText("Salvar");
            bt_EditarSalvar.getStyleClass().setAll("profile-button-primary");
            isEditMode = true;
        }
    }

    @FXML
    void trocarFoto(ActionEvent event) {
        System.out.println("Botão Trocar Foto clicado.");
        // (Aqui virá a lógica para abrir um FileChooser)
    }

    @FXML
    void nomeUsuario(MouseEvent event) {
        // Lógica do onDragDetected (se houver)
    }

    // --- MÉTODOS DE NAVEGAÇÃO ---

    @FXML
    public void sair(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/BemVindo.fxml", "Bem-vindo", event);
    }

    @FXML
    public void perfilAluno (ActionEvent event) {
        System.out.println("Já está na tela de Perfil.");
    }

    @FXML
    public void secaoGeral(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/SecaoAluno.fxml", "Secao Geral", event);
    }

    @FXML
    public void tgGeral(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/TGAluno.fxml", "TG Aluno", event);
    }

    @FXML
    public void telaInicial(ActionEvent event) {
        Application.carregarNovaCena("/org/api/trabalhodegraduacao/view/usuario/aluno/AtualizacoesAluno.fxml", "Tela Inicial", event);
    }

    @FXML
    void devolutivasGeral(ActionEvent event) {
        String fxmlPath = "/org/api/trabalhodegraduacao/view/usuario/aluno/DevolutivasAluno.fxml";
        String title = "TGRS - Devolutivas";
        Application.carregarNovaCena(fxmlPath, title, event);
    }
}