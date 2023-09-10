package br.com.macrosxtreme.enums;

public enum Genero {

    MASCULINO(1){
        @Override
        public int calcularTaxaMetabolicaBasal(int idade, int altura, int peso) {
            double taxaMetabolicaBasal = (10 * peso) + (6.25 * altura) - (5 * idade) + 5;
            int taxaMetabolicaBasalMasculina = (int) Math.round(taxaMetabolicaBasal);
            return taxaMetabolicaBasalMasculina;
        }
    },
    FEMININO(2) {
        @Override
        public int calcularTaxaMetabolicaBasal(int idade, int altura, int peso) {
            double taxaMetabolicaBasal = (10 * peso) + (6.25 * altura) - (5 * idade) - 161;
            int taxaMetabolicaBasalFeminino = (int) Math.round(taxaMetabolicaBasal);
            return taxaMetabolicaBasalFeminino;
        }
    };

    private final int valor;

    Genero(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public abstract int calcularTaxaMetabolicaBasal(int idade, int altura, int peso);

}
