package br.com.macrosxtreme.services;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

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

		switch (genero) {
		case "Homem": {
			switch (nivelAtividadeFisica) {
			case "nivel1": {
				i = tmb * 1.2;
			}
				break;
			case "nivel2": {
				i = tmb * 1.375;
			}
				break;
			case "nivel3": {
				i = tmb * 1.55;
			}
				break;
			case "nivel4": {
				i = tmb * 1.725;
			}
				break;
			case "nivel5": {
				i = tmb * 1.9;
			}
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: Valor não existe");
			}
		}
			break;
		case "Mulher": {
			switch (nivelAtividadeFisica) {
			case "nivel1": {
				i = tmb * 1.2;
			}
				break;
			case "nivel2": {
				i = tmb * 1.375;
			}
				break;
			case "nivel3": {
				i = tmb * 1.55;
			}
				break;
			case "nivel4": {
				i = tmb * 1.725;
			}
				break;
			case "nivel5": {
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

		int gastoTotal = (int) Math.round(i);
		return gastoTotal;
	}

	public int calculatorObjTraining(String genero, int idade, int altura, int peso, String objetivo,
			String nivelAtividadeFisica) {
		double i, gastoTotal;
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
		int obj = (int) Math.round(i);
		return obj;

	}

	public int calculatorObjOff(String genero, int idade, int altura, int peso, String objetivo,
			String nivelAtividadeFisica) {

		int i = calculatorObjTraining(genero, idade, altura, peso, objetivo, nivelAtividadeFisica);
		double y = i - (10 * i / 100);
		int ObjOff = (int) Math.round(y);
		return ObjOff;

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
		List<Integer> macros = macrosTraining(genero, idade, altura, peso, objetivo, nivelAtividadeFisica);
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

	public String imc(int altura, int peso) {
		DecimalFormat converter = new DecimalFormat("#,##");
		DecimalFormat convertImc = new DecimalFormat("#,##");

		String formatAltura = converter.format(altura);
		double converterAltura = Double.parseDouble(formatAltura);

		double imc = peso / (converterAltura * converterAltura);

		String formatImc = convertImc.format(imc);
		double imcFormatted = Double.parseDouble(formatImc);

		String situacao = null;

		if (imcFormatted < 17) {
			situacao = "Muito abaixo do peso";
		} else if (imcFormatted >= 17 && imcFormatted <= 18.5) {
			situacao = "Abaixo do peso";
		} else if (imcFormatted >= 18.6 && imcFormatted <= 24.9) {
			situacao = "Peso normal";
		} else if (imcFormatted >= 25 && imcFormatted <= 29.9) {
			situacao = "Acima do peso";
		} else if (imcFormatted >= 30 && imcFormatted <= 34.9) {
			situacao = "Obesidade 1";
		} else if (imcFormatted >= 35 && imcFormatted <= 39.9) {
			situacao = "Obesidade 2 (Severa)";
		} else if (imcFormatted >= 40) {
			situacao = "Obesidade 3 (Mórbida)";
		}

		return imcFormatted + "%" + " (" + situacao+ ")";
	}

}
