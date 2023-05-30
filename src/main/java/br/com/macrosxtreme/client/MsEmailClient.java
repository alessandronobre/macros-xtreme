package br.com.macrosxtreme.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.macrosxtreme.dto.HistoricoEmailDTO;

@FeignClient(name = "MsEmailClient", url = "http://localhost:8082", path = "/api/email")
public interface MsEmailClient {

    @GetMapping(params = "nome")
    public ResponseEntity<List<HistoricoEmailDTO>> findHistoricoPorUsuario(@RequestParam("nome") String nome);
	
}
