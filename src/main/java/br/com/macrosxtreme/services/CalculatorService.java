package br.com.macrosxtreme.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

	public List<String> levelActivity() {
		List<String> nivelA = new ArrayList<>();
		nivelA.add("Sedentário (Exercicio minimo)");
		nivelA.add("Exercicio Leve (1-3 dias por semana)");
		nivelA.add("Exercicio Moderado (3-5 dias por semana)");
		nivelA.add("Exercicio Intenso (6-7 dias por semana)");
		nivelA.add("Exercicio Muito Intenso (Atleta, 2x por dia)");
		return nivelA;

	}

	public int calculatorTBM(String genero, int idade, int altura, int peso) {
		int tmb = 0;
		if (genero.equals("Homem")) {
			double i = (10 * peso) + (6.25 * altura) - (5 * idade) + 5;
			tmb = (int) Math.round(i);
			return tmb;
		} else if (genero.equals("Mulher")) {
			double i = (10 * peso) + (6.25 * altura) - (5 * idade) - 161;
			tmb = (int) Math.round(i);
			return tmb;

		}
		return tmb;
	}

	public int calculatorGT(String genero, int idade, int altura, int peso, String nivelAtividadeFisica) {
		double tmb = calculatorTBM(genero, idade, altura, peso);
		double i;
		int gastoTotal;

		switch (genero) {
		case "Homem": {
			switch (nivelAtividadeFisica) {
			case "Sedentário (Exercicio minimo)": {
				i = tmb * 1.2;
			}
				break;
			case "Exercicio Leve (1-3 dias por semana)": {
				i = tmb * 1.375;
			}
				break;
			case "Exercicio Moderado (3-5 dias por semana)": {
				i = tmb * 1.55;
			}
				break;
			case "Exercicio Intenso (6-7 dias por semana)": {
				i = tmb * 1.725;
			}
				break;
			case "Exercicio Muito Intenso (Atleta, 2x por dia)": {
				i = tmb * 1.9;
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
				i = tmb * 1.2;
			}
				break;
			case "Exercicio Leve (1-3 dias por semana)": {
				i = tmb * 1.375;
			}
				break;
			case "Exercicio Moderado (3-5 dias por semana)": {
				i = tmb * 1.55;
			}
				break;
			case "Exercicio Intenso (6-7 dias por semana)": {
				i = tmb * 1.725;
			}
				break;
			case "Exercicio Muito Intenso (Atleta, 2x por dia)": {
				i = tmb * 1.9;
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

	public int calculatorObjTraining(String genero, int idade, int altura, int peso, String objetivo,
			String nivelAtividadeFisica) {
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
	
	public int calculatorObjOff(String genero, int idade, int altura, int peso, String objetivo,
			String nivelAtividadeFisica) {
		int ObjOff;
		double x = 0;
		int i = calculatorObjTraining(genero, idade, altura, peso, objetivo, nivelAtividadeFisica);
		double y = i - (10 * i / 100);
		return ObjOff = (int) Math.round(y);

	}

	public List<Integer> macrosTraining(String genero, int idade, int altura, int peso, String objetivo,
			String nivelAtividadeFisica) {
		int obj = calculatorObjTraining(genero, idade, altura, peso, objetivo, nivelAtividadeFisica);
		int proteina;
		int gordura;
		int carbo;
		int fibras = 0;
		double x = peso * 2.240;
		double i = peso * 0.760;
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
	
	public List<Integer> macrosOff(String genero, int idade, int altura, int peso, String objetivo,
			String nivelAtividadeFisica) {
		List <Integer> macros = macrosTraining(genero, idade, altura, peso, objetivo, nivelAtividadeFisica);
		int i = macros.get(2);
		int y = macros.get(1);
		int proteina = macros.get(0);
		int gordura = i - (9 * i / 100);
		int carbo = y - (20 * y / 100);
		int fibras = macros.get(3);

		List<Integer> macro = new ArrayList<>();
		macro.add(proteina);
		macro.add(carbo);
		macro.add(gordura);
		macro.add(fibras);

		return macro;
	}

}
