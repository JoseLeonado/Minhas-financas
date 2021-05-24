package com.jlcb.minhasfinancas.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jlcb.minhasfinancas.exception.RegraNegocioException;
import com.jlcb.minhasfinancas.model.Usuario;
import com.jlcb.minhasfinancas.model.repository.UsuarioRepository;
import com.jlcb.minhasfinancas.service.interfaces.UsuarioServiceInterface;

@Service
public class UsuarioService implements UsuarioServiceInterface {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		return null;
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		
		validarEmail(usuario.getEmail());
		
		return usuarioRepository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {

		boolean existe = usuarioRepository.existsByEmail(email);
		
		if (existe) {
			throw new RegraNegocioException("Já existe um usário com este e-mail!");
		}
		
	}

}
