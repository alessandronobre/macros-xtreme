package br.com.macrosxtreme.enums;

public enum AtividadeFisica {

    NIVEL1(1){
        @Override
        public double calcularGastoTotalCalorias(double taxaMetabolicaBasal) {
            double taxaMetabolicaBasalToNivel1 = taxaMetabolicaBasal * 1.2;
            return taxaMetabolicaBasalToNivel1;
        }
    },
    NIVEL2(2) {
        @Override
        public double calcularGastoTotalCalorias(double taxaMetabolicaBasal) {
            double taxaMetabolicaBasalToNivel2 = taxaMetabolicaBasal * 1.375;
            return taxaMetabolicaBasalToNivel2;
        }
    },
    NIVEL3(3) {
        @Override
        public double calcularGastoTotalCalorias(double taxaMetabolicaBasal) {
            double taxaMetabolicaBasalToNivel3 = taxaMetabolicaBasal * 1.55;
            return taxaMetabolicaBasalToNivel3;
        }
    },
    NIVEL4(4) {
        @Override
        public double calcularGastoTotalCalorias(double taxaMetabolicaBasal) {
            double taxaMetabolicaBasalToNivel4 = taxaMetabolicaBasal * 1.725;
            return taxaMetabolicaBasalToNivel4;
        }
    },
    NIVEL5(5) {
        @Override
        public double calcularGastoTotalCalorias(double taxaMetabolicaBasal) {
            double taxaMetabolicaBasalToNivel5 = taxaMetabolicaBasal * 1.9;
            return taxaMetabolicaBasalToNivel5;
        }
    };

    private final int valor;

    AtividadeFisica(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public abstract double calcularGastoTotalCalorias(double taxaMetabolicaBasal);

}
