package br.com.macrosxtreme.mapper;

import org.springframework.stereotype.Component;

@Component
public class FormatacaoStringMapper {

    public String obterPrimeiraPalavra(String item) {
        String itemFormatado;
        if (item.contains(" ")) {
            int indice = item.indexOf(' ');
            itemFormatado = item.substring(0, indice);
        } else {
            itemFormatado = item;
        }
        return itemFormatado;
    }
}
