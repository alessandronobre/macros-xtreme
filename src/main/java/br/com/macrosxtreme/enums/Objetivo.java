package br.com.macrosxtreme.enums;

public enum Objetivo {
    EMAGRECIMENTO(1, "Emagrecimento"),
    GANHO(2, "Ganho de Massa");

    private final int valor;
    private final String significado;

    Objetivo(int valor, String significado) {
        this.valor = valor;
        this.significado = significado;
    }

    public int getValor() {
        return valor;
    }

    public String getSignificado() {
        return significado;
    }

}
