package com.jlcb.minhasfinancas.api.resource;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jlcb.minhasfinancas.api.dto.UsuarioDTO;
import com.jlcb.minhasfinancas.model.Usuario;
import com.jlcb.minhasfinancas.service.exception.AutenticacaoException;
import com.jlcb.minhasfinancas.service.exception.RegraDeNegocioException;
import com.jlcb.minhasfinancas.service.impl.UsuarioServiceImpl;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {
	
	private UsuarioServiceImpl usuarioService;
	
	/*
	 * Contrutor para injeção de dependência
	 * 
	 */
	public UsuarioResource(UsuarioServiceImpl usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	/*
	 * @RequestBody = Ela diz para que o JSON que vem da requisição sejam transformado no objeto DTO com as propriedades da mesma
	 */
	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody UsuarioDTO usuarioDTO) { 
		
		Usuario usuario = new Usuario(null, usuarioDTO.getNome(), usuarioDTO.getEmail(), usuarioDTO.getSenha());
		
		try {
			
			Usuario usuarioSalvo = usuarioService.salvarUsuario(usuario);
			
			return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED); /* quando precisar retornar algo no corpo usar new ResponseEntity<>(corpo da resposta, status retornado); */
		} catch (RegraDeNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/autenticar")
	public ResponseEntity<?> autenticar(@RequestBody UsuarioDTO usuarioDTO) {
		
		try {
			
			Usuario usuarioAutenticado = usuarioService.autenticar(usuarioDTO.getEmail(), usuarioDTO.getSenha());
			
			return ResponseEntity.ok(usuarioAutenticado); /* Retorna o status 200 */
		} catch (AutenticacaoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
