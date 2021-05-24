package com.jlcb.minhasfinancas.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jlcb.minhasfinancas.model.Usuario;
import com.jlcb.minhasfinancas.model.repository.UsuarioRepository;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository usuarioRepository;

	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		
		/* Cenário */
		Usuario usuario = Usuario.builder().nome("Usuário").email("usuario@email.com").build();
		usuarioRepository.save(usuario);
		
		/* Ação */
		boolean resultado = usuarioRepository.existsByEmail("usuario@email.com");
		
		/* Verificação */
		Assertions.assertThat(resultado).isTrue();
	}
	
	@Test
	public void deveRetornarFasloQuandoNaoHouverUsuarioComOEmail() {
		
		/* Cenário */
		usuarioRepository.deleteAll();
		
		/* Ação */
		boolean resultado = usuarioRepository.existsByEmail("usuario@email.com");
		
		/* Verificação */
		Assertions.assertThat(resultado).isFalse();
	}
}
