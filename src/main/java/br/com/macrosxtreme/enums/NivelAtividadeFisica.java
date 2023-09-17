package br.com.macrosxtreme.enums;

public enum NivelAtividadeFisica {

    NIVEL1(1){
        @Override
        public Integer calcularGastoTotalCalorias(Integer taxaMetabolicaBasal) {
            return (int) Math.round(taxaMetabolicaBasal * 1.2);
        }
    },
    NIVEL2(2) {
        @Override
        public Integer calcularGastoTotalCalorias(Integer taxaMetabolicaBasal) {
            return (int) Math.round(taxaMetabolicaBasal * 1.375);
        }
    },
    NIVEL3(3) {
        @Override
        public Integer calcularGastoTotalCalorias(Integer taxaMetabolicaBasal) {
            return (int) Math.round(taxaMetabolicaBasal * 1.55);
        }
    },
    NIVEL4(4) {
        @Override
        public Integer calcularGastoTotalCalorias(Integer taxaMetabolicaBasal) {
            return (int) Math.round(taxaMetabolicaBasal * 1.725);
        }
    },
    NIVEL5(5) {
        @Override
        public Integer calcularGastoTotalCalorias(Integer taxaMetabolicaBasal) {
            return (int) Math.round(taxaMetabolicaBasal * 1.9);
        }
    };

    private final int valor;

    NivelAtividadeFisica(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public abstract Integer calcularGastoTotalCalorias(Integer taxaMetabolicaBasal);

}
