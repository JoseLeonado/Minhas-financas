package com.jlcb.minhasfinancas.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jlcb.minhasfinancas.exception.AutenticacaoException;
import com.jlcb.minhasfinancas.exception.EmailException;
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
		
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
		
		if (!usuario.isPresent()) {
			throw new AutenticacaoException("Usuário não encontrado para o e-mail informado.");
		}
		
		if (!usuario.get().getSenha().equals(senha)) {
			throw new AutenticacaoException("Senha inválida.");
		}
		
		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		
		validarEmail(usuario.getEmail());
		
		return usuarioRepository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {

		boolean emailExiste = usuarioRepository.existsByEmail(email);
		
		if (emailExiste) {
			throw new EmailException("Já existe um usário com este e-mail.");
		}
	}
}
