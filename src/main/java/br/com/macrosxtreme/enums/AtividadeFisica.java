package br.com.macrosxtreme.enums;

public enum AtividadeFisica {

    NIVEL1(1, "Sedentário (Exercicio minimo)"),
    NIVEL2(2, "Exercicio Leve (1-3 dias por semana)"),
    NIVEL3(3, "Exercicio Moderado (3-5 dias por semana)"),
    NIVEL4(4, "Exercicio Intenso (6-7 dias por semana)"),
    NIVEL5(5, "Exercicio Muito Intenso (Atleta, 2x por dia)");

    private final int valor;
    private final String significado;

    AtividadeFisica(int valor, String significado) {
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
