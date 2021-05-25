package com.jlcb.minhasfinancas.api.resource;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jlcb.minhasfinancas.api.dto.LancamentoDTO;
import com.jlcb.minhasfinancas.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	
	private LancamentoService lancamentoService;
	
	public LancamentoResource(LancamentoService lancamentoService) {
		this.lancamentoService = lancamentoService;
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody LancamentoDTO lancamentoDTO) {
		
		
		
	}

}
