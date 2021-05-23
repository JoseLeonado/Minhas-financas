package com.jlcb.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jlcb.minhasfinancas.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	
	/* Optional<Usuario> findByEmail(String email); Optional, pois, esse método pode ou não retornar um usuário */
	
	boolean existsByEmail(String email); /* Retorna verdadeiro caso exista um usuário com o e-mail passado no parâmetro. Caso não exista, retornará falso */
}
