package org.api.trabalhodegraduacao.entities;

import java.time.LocalDate;

public class Notificacao {
    private String mensagem;
    private LocalDate data;

    public Notificacao(String mensagem, LocalDate data) {
        this.mensagem = mensagem;
        this.data = data;
    }

    public String getMensagem() { return mensagem; }
    public LocalDate getData() { return data; }

    @Override
    public String toString() {
        // Formato simples para ListView
        return data + " - " + mensagem;
    }
}