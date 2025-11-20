package org.api.trabalhodegraduacao.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Correcao {

    private int idCorrecao;
    private LocalDate dataCorrecoes;
    private String status;
    private String conteudo;

    private LocalDateTime dataSecao;
    private String emailAluno;
    private String emailOrientador;

    private String tituloSecao;

    public Correcao() {
    }

    public int getIdCorrecao() { return idCorrecao; }
    public void setIdCorrecao(int idCorrecao) { this.idCorrecao = idCorrecao; }

    public LocalDate getDataCorrecoes() { return dataCorrecoes; }
    public void setDataCorrecoes(LocalDate dataCorrecoes) { this.dataCorrecoes = dataCorrecoes; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }

    public LocalDateTime getDataSecao() { return dataSecao; }
    public void setDataSecao(LocalDateTime dataSecao) { this.dataSecao = dataSecao; }

    public String getEmailAluno() { return emailAluno; }
    public void setEmailAluno(String emailAluno) { this.emailAluno = emailAluno; }

    public String getEmailOrientador() { return emailOrientador; }
    public void setEmailOrientador(String emailOrientador) { this.emailOrientador = emailOrientador; }

    public String getTituloSecao() { return tituloSecao; }
    public void setTituloSecao(String tituloSecao) { this.tituloSecao = tituloSecao; }
}