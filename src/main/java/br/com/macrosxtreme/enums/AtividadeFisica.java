package br.com.macrosxtreme.enums;

public enum AtividadeFisica {

    NIVEL1(1),
    NIVEL2(2),
    NIVEL3(3),
    NIVEL4(4),
    NIVEL5(5);

    private final int valor;

    AtividadeFisica(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

}
