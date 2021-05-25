package com.jlcb.minhasfinancas.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jlcb.minhasfinancas.model.Usuario;
import com.jlcb.minhasfinancas.model.repository.UsuarioRepository;
import com.jlcb.minhasfinancas.service.UsuarioService;
import com.jlcb.minhasfinancas.service.exception.AutenticacaoException;
import com.jlcb.minhasfinancas.service.exception.RegraDeNegocioException;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

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
			throw new RegraDeNegocioException("Já existe um usário com este e-mail.");
		}
	}

	@Override
	public Optional<Usuario> obterUsuarioPorId(Long id) {
		return usuarioRepository.findById(id);
	}
}
