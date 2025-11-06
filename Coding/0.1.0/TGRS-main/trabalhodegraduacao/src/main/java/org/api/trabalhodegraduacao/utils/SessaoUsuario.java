package org.api.trabalhodegraduacao.utils;

public class SessaoUsuario {

    private static SessaoUsuario instance;

    private String email;
    private String nome;
    private String funcao;
    private String senha;

    private SessaoUsuario() {
    }

    public static SessaoUsuario getInstance() {
        if (instance == null) {
            instance = new SessaoUsuario();
        }
        return instance;
    }

    public void setUsuarioLogado(String email, String nome, String funcao, String senha) {
        this.email = email;
        this.nome = nome;
        this.funcao = funcao;
        this.senha = senha;
    }

    public void limparSessao() {
        this.email = null;
        this.nome = null;
        this.funcao = null;
        this.senha = null;
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
        return this.email != null;
    }
}

