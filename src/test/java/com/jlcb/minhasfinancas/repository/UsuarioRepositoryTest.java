package com.jlcb.minhasfinancas.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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
	
	@Autowired
	TestEntityManager entityManager;

	@Test
	public void deverRetornarVerdadeiroAoVerificarSeUmEmailExiste() {
		
		/* Cenário */
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		
		/* Ação */
		boolean resultado = usuarioRepository.existsByEmail("usuario@email.com");
		
		/* Verificação */
		Assertions.assertThat(resultado).isTrue();
	}
	
	@Test
	public void deverRetornarFalsoAoVerificarSeUmEmailExiste() {
		
		/* Cenário */
		
		/* Ação */
		boolean resultado = usuarioRepository.existsByEmail("usuario@email.com");
		
		/* Verificação */
		Assertions.assertThat(resultado).isFalse();
	}
	
	@Test
	public void deveSalvarUmUsuarioNoBancoDeDados() {
		
		/* Cenário */
		Usuario usuario = criarUsuario();
		
		/* Açaõ */
		Usuario usarioSalvo = usuarioRepository.save(usuario);
		
		/* Verificação */
		Assertions.assertThat(usarioSalvo.getId()).isNotNull(); /* Verifique se o usuário salvo possui id, ou seja, se o usuário foi salvo no banco ele terá um id */
	}
	
	@Test
	public void deveRetornarVerdadeiroAoBuscarUmUsuarioPorUmEmail() {
		
		/* Cenário */
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		
		/* Açaõ */
		Optional<Usuario> resultado = usuarioRepository.findByEmail("usuario@email.com");
		
		/* Verificação */
		Assertions.assertThat(resultado.isPresent()).isTrue(); /* Verifica se o resultado está presente  */
	}
	
	@Test
	public void deveRetornarFalsoAoBuscarUmUsuarioPorUmEmail() {
		
		/* Cenário */

		
		/* Açaõ */
		Optional<Usuario> resultado = usuarioRepository.findByEmail("usuario@email.com");
		
		/* Verificação */
		Assertions.assertThat(resultado.isPresent()).isFalse(); /* Verifica se o resultado está presente  */
	}
	
	public static Usuario criarUsuario() {
		return 	Usuario.builder()
				.nome("Usuário")
				.email("usuario@email.com")
				.senha("senha")
				.build();
	}
}
