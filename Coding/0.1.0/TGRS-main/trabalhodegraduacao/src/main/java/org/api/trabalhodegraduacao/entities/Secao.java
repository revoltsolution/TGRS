package org.api.trabalhodegraduacao.entities;

import java.sql.Date;
import java.time.LocalDate;

public class Secao {

    private int idSecao;
    private String identificacaoProjeto;
    private String empresaParceira;
    private String problema;
    private String solucao;
    private String linkRepositorio;
    private String tecnologiasUtilizadas;
    private String contribuicoesPessoais;
    private String descricaoSoft;
    private String descricaoHard;
    private String historicoProfissional;
    private String historicoAcademico;
    private String motivacao;
    private int ano;
    private char periodo;
    private char semestre;
    private Date data;
    private int idTG;
    private String emailAluno;
    private String emailOrientador;


    public int getIdSecao() {
        return idSecao;
    }

    public void setIdSecao(int idSecao) {
        this.idSecao = idSecao;
    }

    public String getIdentificacaoProjeto() {
        return identificacaoProjeto;
    }

    public void setIdentificacaoProjeto(String identificacaoProjeto) {
        this.identificacaoProjeto = identificacaoProjeto;
    }

    public String getEmpresaParceira() {
        return empresaParceira;
    }

    public void setEmpresaParceira(String empresaParceira) {
        this.empresaParceira = empresaParceira;
    }

    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    public String getLinkRepositorio() {
        return linkRepositorio;
    }

    public void setLinkRepositorio(String linkRepositorio) {
        this.linkRepositorio = linkRepositorio;
    }

    public String getTecnologiasUtilizadas() {
        return tecnologiasUtilizadas;
    }

    public void setTecnologiasUtilizadas(String tecnologiasUtilizadas) {
        this.tecnologiasUtilizadas = tecnologiasUtilizadas;
    }

    public String getContribuicoesPessoais() {
        return contribuicoesPessoais;
    }

    public void setContribuicoesPessoais(String contribuicoesPessoais) {
        this.contribuicoesPessoais = contribuicoesPessoais;
    }

    public String getDescricaoSoft() {
        return descricaoSoft;
    }

    public void setDescricaoSoft(String descricaoSoft) {
        this.descricaoSoft = descricaoSoft;
    }

    public String getDescricaoHard() {
        return descricaoHard;
    }

    public void setDescricaoHard(String descricaoHard) {
        this.descricaoHard = descricaoHard;
    }

    public String getHistoricoProfissional() {
        return historicoProfissional;
    }

    public void setHistoricoProfissional(String historicoProfissional) {
        this.historicoProfissional = historicoProfissional;
    }

    public String getHistoricoAcademico() {
        return historicoAcademico;
    }

    public void setHistoricoAcademico(String historicoAcademico) {
        this.historicoAcademico = historicoAcademico;
    }

    public String getMotivacao() {
        return motivacao;
    }

    public void setMotivacao(String motivacao) {
        this.motivacao = motivacao;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public char getPeriodo() {
        return periodo;
    }

    public void setPeriodo(char periodo) {
        this.periodo = periodo;
    }

    public char getSemestre() {
        return semestre;
    }

    public void setSemestre(char semestre) {
        this.semestre = semestre;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getIdTG() {
        return idTG;
    }

    public void setIdTG(int idTG) {
        this.idTG = idTG;
    }

    public String getEmailAluno() {
        return emailAluno;
    }

    public void setEmailAluno(String emailAluno) {
        this.emailAluno = emailAluno;
    }

    public String getEmailOrientador() {
        return emailOrientador;
    }

    public void setEmailOrientador(String emailOrientador) {
        this.emailOrientador = emailOrientador;
    }

    public Secao(int idSecao, String identificacaoProjeto, String empresaParceira, String problema, String solucao, String linkRepositorio, String tecnologiasUtilizadas, String contribuicoesPessoais, String descricaoSoft, String descricaoHard, String historicoProfissional, String historicoAcademico, String motivacao, int ano, char periodo, char semestre, Date data, int idTG, String emailAluno, String emailOrientador) {
        this.idSecao = idSecao;
        this.identificacaoProjeto = identificacaoProjeto;
        this.empresaParceira = empresaParceira;
        this.problema = problema;
        this.solucao = solucao;
        this.linkRepositorio = linkRepositorio;
        this.tecnologiasUtilizadas = tecnologiasUtilizadas;
        this.contribuicoesPessoais = contribuicoesPessoais;
        this.descricaoSoft = descricaoSoft;
        this.descricaoHard = descricaoHard;
        this.historicoProfissional = historicoProfissional;
        this.historicoAcademico = historicoAcademico;
        this.motivacao = motivacao;
        this.ano = ano;
        this.periodo = periodo;
        this.semestre = semestre;
        this.data = data;
        this.idTG = idTG;
        this.emailAluno = emailAluno;
        this.emailOrientador = emailOrientador;

    }
}

