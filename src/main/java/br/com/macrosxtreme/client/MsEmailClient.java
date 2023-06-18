package br.com.macrosxtreme.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.macrosxtreme.dto.EmailDTO;

@FeignClient(name = "MsEmailClient", url = "http://localhost:8082", path = "/api/email")
public interface MsEmailClient {
    
	@PostMapping
	public ResponseEntity<String> enviar(@RequestBody EmailDTO email);
	
}
