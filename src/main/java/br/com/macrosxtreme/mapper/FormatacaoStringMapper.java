package br.com.macrosxtreme.mapper;

import br.com.macrosxtreme.dto.EmailDTO;
import org.springframework.stereotype.Component;

@Component
public class FormatacaoStringMapper {

    public String obterPrimeiraPalavra(String item) {
        String itemFormatado = null;
        if (item.contains(" ")) {
            int indice = item.indexOf(' ');
            itemFormatado = item.substring(0, indice);
        } else {
            itemFormatado = item;
        }
        return itemFormatado;
    }
}
