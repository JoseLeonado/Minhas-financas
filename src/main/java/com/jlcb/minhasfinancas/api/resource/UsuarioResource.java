package com.jlcb.minhasfinancas.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jlcb.minhasfinancas.api.dto.UsuarioDTO;
import com.jlcb.minhasfinancas.exception.EmailException;
import com.jlcb.minhasfinancas.model.Usuario;
import com.jlcb.minhasfinancas.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {
	
	private UsuarioService usuarioService;
	
	/*
	 * Contrutor para injeção de dependência
	 * 
	 */
	public UsuarioResource(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	/*
	 * @RequestBody = Ela diz para que o JSON que vem da requisição sejam transformado no objeto DTO com as propriedades da mesma
	 */
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody UsuarioDTO usuarioDTO) { 
		
		Usuario usuario = new Usuario(null, usuarioDTO.getNome(), usuarioDTO.getEmail(), usuarioDTO.getSenha());
		
		try {
			
			Usuario usuarioSalvo = usuarioService.salvarUsuario(usuario);
			
			return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
			
		} catch (EmailException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}