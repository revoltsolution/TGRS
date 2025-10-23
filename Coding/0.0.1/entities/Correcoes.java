package org.api.trabalhodegraduacao.entities;

import java.time.LocalDate;

public class Correcoes {
    private Integer idCorrecao;
    private String comentario;
    private String status;
    private LocalDate dataCorrecao;

    public Correcoes(String comentario, String status) {
        this.comentario = comentario;
        this.status = status;
        this.dataCorrecao = LocalDate.now();
    }
    public Integer getIdCorrecao() {
        return idCorrecao;
    }
    public void setIdCorrecao(Integer idCorrecao) {
        this.idCorrecao = idCorrecao;
    }
    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDate getDataCorrecao() {
        return dataCorrecao;
    }
    public void setDataCorrecao(LocalDate dataCorrecao) {
        this.dataCorrecao = dataCorrecao;
    }
    @Override
    public String toString() {
        return"";
    }
}
