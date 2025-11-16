package org.api.trabalhodegraduacao.entities;

import java.sql.Date;
import java.time.LocalDate;

public class Usuario {

    private String nomeCompleto;
    private String fotoPerfil;
    private LocalDate dataNascimento;
    private String emailCadastrado;
    private String curso;
    private String linkedin;
    private String gitHub;
    private String funcao;
    private String senha;
    private String emailOrientador;
    private String nomeOrientador;

    // --- CAMPO ADICIONADO ---
    // Este campo não está no banco, será calculado pelo DAO
    private double progresso;
    // ------------------------

    public Usuario(String tipo, String senha, String emailCadastrado, String nome) {
        this.funcao = tipo;
        this.senha = senha;
        this.emailCadastrado = emailCadastrado;
        this.nomeCompleto = nome;
    }

    public Usuario() {
    }

    // --- CONSTRUTOR CORRIGIDO ---
    public Usuario(String nomeDB, String emailDB, String cursoDB, Date dataNascDB, String linkedinDB, String gitHubDB, String emailOrientador, String senhaDB) {
        this.nomeCompleto = nomeDB;
        this.emailCadastrado = emailDB;
        this.curso = cursoDB;
        if (dataNascDB != null) {
            this.dataNascimento = dataNascDB.toLocalDate(); // Corrigido
        }
        this.linkedin = linkedinDB;
        this.gitHub = gitHubDB;
        this.emailOrientador = emailOrientador;
        this.senha = senhaDB; // Corrigido
    }
    // --------------------------

    public void nomeProfessor(String nome) {
        this.nomeCompleto = nome;
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

    public String getTipo() {return funcao;}

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

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getNomeOrientador() {
        return nomeOrientador;
    }

    public void setNomeOrientador(String nomeOrientador) {
        this.nomeOrientador = nomeOrientador;
    }

    // --- GETTER/SETTER ADICIONADOS ---
    public double getProgresso() {
        return progresso;
    }

    public void setProgresso(double progresso) {
        this.progresso = progresso;
    }
    // -------------------------------


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