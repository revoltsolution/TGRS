package org.api.trabalhodegraduacao.entities;

import java.time.LocalDate;

public class Usuario {

    private String nomeCompleto;
    private String fotoPerfil;
    private LocalDate dataNascimento;
    private String emailCadastrado;
    private String linkedin;
    private String gitHub;
    private String funcao;
    private String senha;
    private String emailOrientador;


    public Usuario(String nomeCompleto, String fotoPerfil, LocalDate dataNascimento, String emailCadastrado, String linkedin, String gitHub, Character sexo, String tipo, String senha, String emailOrientador) {
        this.nomeCompleto = nomeCompleto;
        this.fotoPerfil = fotoPerfil;
        this.dataNascimento = dataNascimento;
        this.emailCadastrado = emailCadastrado;
        this.linkedin = linkedin;
        this.gitHub = gitHub;
        this.funcao = tipo;
        this.senha = senha;
        this.emailOrientador = emailOrientador;
    }

    public Usuario(String tipo, String senha, String emailCadastrado, String nome) {
        this.funcao = tipo;
        this.senha = senha;
        this.emailCadastrado = emailCadastrado;
        this.nomeCompleto = nome;
    }

    public Usuario() {

    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmailCadastrado() {
        return emailCadastrado;
    }

    public void setEmailCadastrado(String emailCadastrado) {
        this.emailCadastrado = emailCadastrado;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getGitHub() {
        return gitHub;
    }

    public void setGitHub(String gitHub) {
        this.gitHub = gitHub;
    }

    public String getTipo() {


        return funcao;
    }

    public void setTipo(String tipo) {
        this.funcao = tipo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmailOrientador() {
        return emailOrientador;
    }

    public void setEmailOrientador(String emailOrientador) {
        this.emailOrientador = emailOrientador;
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "nomeCompleto='" + nomeCompleto + '\'' +
                ", fotoPerfil='" + fotoPerfil + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", emailCadastrado='" + emailCadastrado + '\'' +
                ", linkedin='" + linkedin + '\'' +
                ", gitHub='" + gitHub + '\'' +
                ", funcao='" + funcao + '\'' +
                ", senha='" + senha + '\'' +
                ", emailOrientador='" + emailOrientador + '\'' +
                '}';
    }


}
