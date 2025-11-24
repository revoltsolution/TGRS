package org.api.trabalhodegraduacao.entities;

import java.time.LocalDate;

public class Notificacao {
    private String mensagem;
    private LocalDate data;

    public Notificacao() {
    }

    public Notificacao(String mensagem, LocalDate data) {
        this.mensagem = mensagem;
        this.data = data;
    }

    public String getMensagem() { return mensagem; }
    public LocalDate getData() { return data; }

    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    public void setData(LocalDate data) { this.data = data; }

    @Override
    public String toString() {
        if (data == null) {
            return mensagem;
        }
        return data + " - " + mensagem;
    }
}