package br.com.macrosxtreme.mapper;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DataMapper {

	public String formatador() {
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		LocalDateTime dataHoraAtual = LocalDateTime.now();
		String dataHoraFormatada = dataHoraAtual.format(formatador);
		return dataHoraFormatada;
	}
}