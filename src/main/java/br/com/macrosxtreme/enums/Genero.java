package br.com.macrosxtreme.enums;

public enum Genero {

    MASCULINO(1){
        @Override
        public Integer calcularTaxaMetabolicaBasal(int idade, int altura, int peso) {
            double taxaMetabolicaBasal = (10 * peso) + (6.25 * altura) - (5 * idade) + 5;
            return (int) Math.round(taxaMetabolicaBasal);
        }
    },
    FEMININO(2) {
        @Override
        public Integer calcularTaxaMetabolicaBasal(int idade, int altura, int peso) {
            double taxaMetabolicaBasal = (10 * peso) + (6.25 * altura) - (5 * idade) - 161;
            return (int) Math.round(taxaMetabolicaBasal);
        }
    };

    private final int valor;

    Genero(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public abstract Integer calcularTaxaMetabolicaBasal(int idade, int altura, int peso);

}
