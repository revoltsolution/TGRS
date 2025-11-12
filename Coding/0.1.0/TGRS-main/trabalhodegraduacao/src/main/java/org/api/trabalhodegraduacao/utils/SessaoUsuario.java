package org.api.trabalhodegraduacao.utils;

public class SessaoUsuario {

    private static SessaoUsuario instance;

    private String email;
    private String nome;
    private String funcao;
    private String senha;

    private boolean logado = false;

    private SessaoUsuario() {
    }

    public static SessaoUsuario getInstance() {
        if (instance == null) {
            instance = new SessaoUsuario();
        }
        return instance;
    }


    public void iniciarSessao(String email, String nome, String funcao, String senha) {
        this.email = email;
        this.nome = nome;
        this.funcao = funcao;
        this.senha = senha;
        this.logado = true;
    }

    public void limparSessao() {
        this.email = null;
        this.nome = null;
        this.funcao = null;
        this.senha = null;
        this.logado = false;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getFuncao() {
        return funcao;
    }
    public String getSenha() {
        return senha;
    }

    public boolean isLogado() {
        return this.logado;
    }
}