package br.com.macrosxtreme.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.macrosxtreme.dto.HistoricoMacrosDTO;
import br.com.macrosxtreme.model.HistoricoMacros;
import br.com.macrosxtreme.repository.MacrosRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MacrosService {

	private final MacrosRepository macrosRepository;
	
	public void salvarHistorico(HistoricoMacrosDTO historicoMacros) {
		
		HistoricoMacros historico = new HistoricoMacros();
		historico.setUsuario(historicoMacros.getUsuario());
		historico.setImc(historicoMacros.getImc());
		historico.setTmb(historicoMacros.getTmb());
		historico.setGastoCaloricoTotal(historicoMacros.getGastoCaloricoTotal());
		historico.setCaloriasTreino(historicoMacros.getCaloriasTreino());
		historico.setProteinaTreino(historicoMacros.getProteinaTreino());
		historico.setCarboidratoTreino(historicoMacros.getCarboidratoTreino());
		historico.setGorduraTreino(historicoMacros.getGorduraTreino());
		historico.setFibraTreino(historicoMacros.getFibraTreino());
		historico.setCaloriasDescanso(historicoMacros.getCaloriasDescanso());
		historico.setProteinaDescanso(historicoMacros.getProteinaDescanso());
		historico.setCarboidratoDescanso(historicoMacros.getCarboidratoDescanso());
		historico.setGorduraDescanso(historicoMacros.getGorduraDescanso());
		historico.setFibraDescanso(historicoMacros.getFibraDescanso());
		macrosRepository.save(historico);
	}
	
	public HistoricoMacros findByMacros(String usuario) {
		return macrosRepository.findByMacros(usuario);
		
	}
	
	public List<HistoricoMacros> findByHistoricoMacros(String usuario) {
		return macrosRepository.findByHistoricoMacros(usuario);
		
	}

	public Integer calcularTBM(String genero, int idade, int altura, int peso) {
		int tmb = 0;
		if (genero.equals("Masculino")) {
			double i = (10 * peso) + (6.25 * altura) - (5 * idade) + 5;
			tmb = (int) Math.round(i);
			return tmb;
		} else if (genero.equals("Feminino")) {
			double i = (10 * peso) + (6.25 * altura) - (5 * idade) - 161;
			tmb = (int) Math.round(i);
			return tmb;

		}
		return tmb;
	}

	public Integer calcularGT(String genero, int idade, int altura, int peso, String nivelAtividadeFisica) {
		double tmb = calcularTBM(genero, idade, altura, peso);
		double i = 0;

		if (genero.equals("Masculino")) {
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
				throw new IllegalArgumentException("Valor não existe");
			}

		} else if (genero.equals("Feminino")) {

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
				throw new IllegalArgumentException("Valor não existe ");
			}
		}

		int gastoTotal = (int) Math.round(i);
		return gastoTotal;
	}

	public Integer objetivoTreino(String genero, int idade, int altura, int peso, String objetivo,
			String nivelAtividadeFisica) {
		double i = 0, gastoTotal;
		gastoTotal = calcularGT(genero, idade, altura, peso, nivelAtividadeFisica);

		if (objetivo.equals("Emagrecimento")) {
			i = gastoTotal - (25 * gastoTotal / 100);

		} else if (objetivo.equals("Ganho")) {
			i = gastoTotal + 200;

		}
		int objetivoTreino = (int) Math.round(i);
		return objetivoTreino;

	}

	public Integer objetivoDescanso(String genero, int idade, int altura, int peso, String objetivo,
			String nivelAtividadeFisica) {
		int objetivoTreino = objetivoTreino(genero, idade, altura, peso, objetivo, nivelAtividadeFisica);
		
		double objetivoOff = objetivoTreino - (10 * objetivoTreino / 100);
		int objetivoDescanso = (int) Math.round(objetivoOff);
		
		return objetivoDescanso;

	}

	public List<Integer> macrosTreino(String genero, int idade, int altura, int peso, String objetivo,
			String nivelAtividadeFisica) {
		int objtivo = objetivoTreino(genero, idade, altura, peso, objetivo, nivelAtividadeFisica);
		
		int proteina, gordura, carboidatro, fibras = 0;
		double p = peso * 2.240;
		double g = peso * 0.760;
		double c = (objtivo - (p * 4) - (g * 9)) / 4;
		
		if (objtivo <= 1200) {
			fibras = 10;
			
		} else if (objtivo > 1200 && objtivo <= 2200) {
			fibras = 20;
			
		} else if (objtivo > 2200 && objtivo <= 3200) {
			fibras = 30;
			
		} else if (objtivo > 3200 && objtivo <= 4200) {
			fibras = 40;
		}

		proteina = (int) Math.round(p);
		gordura = (int) Math.round(g);
		carboidatro = (int) Math.round(c);
		
		List<Integer> macros = new ArrayList<>();
		macros.add(proteina);
		macros.add(carboidatro);
		macros.add(gordura);
		macros.add(fibras);

		return macros;
	}

	public List<Integer> macrosDescanso(String genero, int idade, int altura, int peso, String objetivo,
			String nivelAtividadeFisica) {
		List<Integer> macrosTreino = macrosTreino(genero, idade, altura, peso, objetivo, nivelAtividadeFisica);
		
		int proteina, gordura, carboidatro, fibras = 0;
		
		proteina = macrosTreino.get(0);
		carboidatro = macrosTreino.get(1) - (20 * macrosTreino.get(1) / 100);
		gordura = macrosTreino.get(2) - (9 * macrosTreino.get(2) / 100);
		fibras = macrosTreino.get(3);

		List<Integer> macros = new ArrayList<>();
		macros.add(proteina);
		macros.add(carboidatro);
		macros.add(gordura);
		macros.add(fibras);

		return macros;
	}

	public String imc(int altura, int peso) {
		DecimalFormat conversor = new DecimalFormat("#,##");

		String formatAltura = conversor.format(altura);
		double converterAltura = Double.parseDouble(formatAltura);

		String formatImc = conversor.format(peso / (converterAltura * converterAltura));
		double imcFormatado = Double.parseDouble(formatImc);

		String situacao = null;

		if (imcFormatado < 17) {
			situacao = "Muito abaixo do peso";
		} else if (imcFormatado >= 17 && imcFormatado <= 18.5) {
			situacao = "Abaixo do peso";
		} else if (imcFormatado >= 18.6 && imcFormatado <= 24.9) {
			situacao = "Peso normal";
		} else if (imcFormatado >= 25 && imcFormatado <= 29.9) {
			situacao = "Acima do peso";
		} else if (imcFormatado >= 30 && imcFormatado <= 34.9) {
			situacao = "Obesidade 1";
		} else if (imcFormatado >= 35 && imcFormatado <= 39.9) {
			situacao = "Obesidade 2 (Severa)";
		} else if (imcFormatado >= 40) {
			situacao = "Obesidade 3 (Mórbida)";
		}

		return imcFormatado + "%" + " (" + situacao + ")";
	}

}
