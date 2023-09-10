package br.com.macrosxtreme.enums;

public enum Objetivo {
    EMAGRECIMENTO(1),
    GANHO(2);

    private final int valor;

    Objetivo(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

}
