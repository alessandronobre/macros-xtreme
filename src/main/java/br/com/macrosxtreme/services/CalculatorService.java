package br.com.macrosxtreme.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

	public List<String> typeGenre() {
		List<String> genero = new ArrayList<>();
		genero.add("Homem");
		genero.add("Mulher");
		return genero;

	}

	public List<String> levelActivity() {
		List<String> nivelA = new ArrayList<>();
		nivelA.add("Sedentário (Exercicio minimo)");
		nivelA.add("Exercicio Leve (1-3 dias por semana)");
		nivelA.add("Exercicio Moderado (3-5 dias por semana)");
		nivelA.add("Exercicio Intenso (6-7 dias por semana)");
		nivelA.add("Exercicio Muito Intenso (Atleta, 2x por dia)");
		return nivelA;

	}

	public List<String> goal() {
		List<String> obj = new ArrayList<>();
		obj.add("Cutting");
		obj.add("Buking");
		return obj;

	}

	public int calculatorM(int idade, int altura, int peso) {
		double i = (10 * peso) + (6.25 * altura) - (5 * idade) + 5;
		int tmbM = (int) Math.round(i);
		return tmbM;

	}

	public int calculatorF(int idade, int altura, int peso) {
		double i = (10 * peso) + (6.25 * altura) - (5 * idade) - 161;
		int tmbF = (int) Math.round(i);
		return tmbF;

	}

	public int calculatorGT(String genero, int idade, int altura, int peso, String nivelAtividadeFisica) {
		double tmbM = calculatorM(idade, altura, peso);
		double tmbF = calculatorF(idade, altura, peso);
		double i;
		int gastoTotal;

		switch (genero) {
		case "Homem": {
			switch (nivelAtividadeFisica) {
			case "Sedentário (Exercicio minimo)": {
				i = tmbM * 1.2;
			}
				break;
			case "Exercicio Leve (1-3 dias por semana)": {
				i = tmbM * 1.375;
			}
				break;
			case "Exercicio Moderado (3-5 dias por semana)": {
				i = tmbM * 1.55;
			}
				break;
			case "Exercicio Intenso (6-7 dias por semana)": {
				i = tmbM * 1.725;
			}
				break;
			case "Exercicio Muito Intenso (Atleta, 2x por dia)": {
				i = tmbF * 1.9;
			}
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: ");
			}
		}
			break;
		case "Mulher": {
			switch (nivelAtividadeFisica) {
			case "Sedentário (Exercicio minimo)": {
				i = tmbF * 1.2;
			}
				break;
			case "Exercicio Leve (1-3 dias por semana)": {
				i = tmbF * 1.375;
			}
				break;
			case "Exercicio Moderado (3-5 dias por semana)": {
				i = tmbF * 1.55;
			}
				break;
			case "Exercicio Intenso (6-7 dias por semana)": {
				i = tmbF * 1.725;
			}
				break;
			case "Exercicio Muito Intenso (Atleta, 2x por dia)": {
				i = tmbF * 1.9;
			}
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: ");
			}
		}
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: ");
		}
		return gastoTotal = (int) Math.round(i);
	}

	public int calculatorObj(String genero, int idade, int altura, int peso, String objetivo, String nivelAtividadeFisica) {
		double i, gastoTotal;
		int obj;
		gastoTotal = calculatorGT(genero, idade, altura, peso, nivelAtividadeFisica);

		switch (objetivo) {
		case "Cutting": {
			i = gastoTotal - (25 * gastoTotal / 100);
		}
			break;
		case "Buking": {
			i = gastoTotal + 200;
		}
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: ");
		}
		return obj = (int) Math.round(i);

	}

	public List<Integer> macros(String genero, int idade, int altura, int peso, String objetivo, String nivelAtividadeFisica) {
		int obj = calculatorObj(genero, idade, altura, peso, objetivo, nivelAtividadeFisica);
		int proteina;
		int gordura;
		int carbo;
		int fibras = 0;
		double x = peso * 2.2;
		double i = peso * 0.8;
		double CaloriasP = x * 4;
		double CaloriasG = i * 9;
		double y = (obj - CaloriasP - CaloriasG) / 4;
		if (obj <= 1200) {
			fibras = 10;
		}
		if (obj > 1200 && obj <= 2200) {
			fibras = 20;
		}
		if (obj > 2200 && obj <= 3200) {
			fibras = 30;
		}
		if (obj > 3200 && obj <= 4200) {
			fibras = 40;
		}
		
		proteina = (int) Math.round(x);
		gordura = (int) Math.round(i);
		carbo = (int) Math.round(y);
		List<Integer> macro = new ArrayList<>();
		macro.add(proteina);
		macro.add(carbo);
		macro.add(gordura);
		macro.add(fibras);
		
		return macro;
	}
	
}
