package br.com.macrosxtreme.enums;

public enum Objetivo {
    EMAGRECIMENTO(1){
        @Override
        public double calcularCaloriasTreino(double gastoTotalCalorias) {
            double caloriasTreino = gastoTotalCalorias - (25 * gastoTotalCalorias / 100);
            return caloriasTreino;
        }

        @Override
        public double calcularCaloriasDescanso(int caloriasTreino) {
            double caloriasDescanso = caloriasTreino - (10 * caloriasTreino / 100);
            return caloriasDescanso;
        }
    },
    GANHO(2) {
        @Override
        public double calcularCaloriasTreino(double gastoTotalCalorias) {
            double caloriasTreino = gastoTotalCalorias + 200;
            return caloriasTreino;
        }

        @Override
        public double calcularCaloriasDescanso(int caloriasTreino) {
            double caloriasDescanso = caloriasTreino - (10 * caloriasTreino / 100);
            return caloriasDescanso;
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
    public abstract double calcularCaloriasDescanso(int caloriasTreino);

}
