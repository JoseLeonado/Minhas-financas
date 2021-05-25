package com.jlcb.minhasfinancas.service;

import java.util.Optional;

import com.jlcb.minhasfinancas.model.Usuario;

public interface UsuarioService {
	
	Usuario autenticar(String email, String senha);
	
	Usuario salvarUsuario(Usuario usuario);
	
	void validarEmail(String email);
	
	Optional<Usuario> obterUsuarioPorId(Long id);
}
