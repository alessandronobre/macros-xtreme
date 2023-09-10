package br.com.macrosxtreme.enums;

public enum Objetivo {
    EMAGRECIMENTO(1){
        @Override
        public double calcularCaloriasTreino(double gastoTotalCalorias) {
            double objetivoCaloriasTreino = gastoTotalCalorias - (25 * gastoTotalCalorias / 100);
            return objetivoCaloriasTreino;
        }
    },
    GANHO(2) {
        @Override
        public double calcularCaloriasTreino(double gastoTotalCalorias) {
            double objetivoCaloriasTreino = gastoTotalCalorias + 200;
            return objetivoCaloriasTreino;
        }
    };

    private final int valor;

    Objetivo(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public abstract double calcularCaloriasTreino(double gastoTotalCalorias);

}
