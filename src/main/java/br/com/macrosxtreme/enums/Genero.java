package br.com.macrosxtreme.enums;

public enum Genero {

    MASCULINO(1, "Masculino"),
    FEMININO(2, "Feminino");

    private final int valor;
    private final String significado;

    Genero(int valor, String significado) {
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
