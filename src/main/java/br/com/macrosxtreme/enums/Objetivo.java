package br.com.macrosxtreme.enums;

public enum Objetivo {
    EMAGRECIMENTO(1){
        @Override
        public Integer calcularCaloriasTreino(Integer gastoTotalCalorias) {
            return Math.round(gastoTotalCalorias - (25 * gastoTotalCalorias / 100));
        }

        @Override
        public Integer calcularCaloriasDescanso(Integer caloriasTreino) {
            return Math.round(caloriasTreino - (10 * caloriasTreino / 100));
        }
    },
    GANHO(2) {
        @Override
        public Integer calcularCaloriasTreino(Integer gastoTotalCalorias) {
            return Math.round(gastoTotalCalorias + 200);
        }

        @Override
        public Integer calcularCaloriasDescanso(Integer caloriasTreino) {
            return Math.round(caloriasTreino - (10 * caloriasTreino / 100));
        }
    };

    private final int valor;

    Objetivo(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public abstract Integer calcularCaloriasTreino(Integer gastoTotalCalorias);
    public abstract Integer calcularCaloriasDescanso(Integer caloriasTreino);

}
