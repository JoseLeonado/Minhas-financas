package com.jlcb.minhasfinancas.service.interfaces;

import com.jlcb.minhasfinancas.model.Usuario;

public interface UsuarioServiceInterface {
	
	Usuario autenticar(String email, String senha);
	
	Usuario salvarUsuario(Usuario usuario);
	
	void validarEmail(String email);
}
