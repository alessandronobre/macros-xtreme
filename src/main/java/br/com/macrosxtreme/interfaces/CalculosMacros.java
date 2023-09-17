package br.com.macrosxtreme.interfaces;

import br.com.macrosxtreme.enums.NivelAtividadeFisica;
import br.com.macrosxtreme.enums.Genero;
import br.com.macrosxtreme.enums.Objetivo;

public interface CalculosMacros {

    String calcularImc(int altura, int peso);
    Integer calcularTaxaMetabolicaBasal(Genero genero, int idade, int altura, int peso);
    Integer calcularGastoTotalCalorias(Genero genero, int idade, int altura, int peso, NivelAtividadeFisica nivelAtividade);
    Integer calcularProteina(int peso);
    Integer calcularFibra(Integer caloriasTreino);
    Integer calcularCaloriasTreino(Genero genero, int idade, int altura, int peso, Objetivo objetivo, NivelAtividadeFisica nivelAtividade);
    Integer calcularCarboidratoTreino(Integer caloriasTreino, int proteina, int gordura);
    Integer calcularGorduraTreino(int peso);
    Integer calcularCaloriasDescanso(Genero genero, int idade, int altura, int peso, Objetivo objetivo, NivelAtividadeFisica nivelAtividade);
    Integer calcularCarboidratoDescanso(Integer carboidratoTreino);
    Integer calcularGorduraDescanso(Integer gorduraTreino);

}
