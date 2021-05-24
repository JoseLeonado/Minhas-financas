package com.jlcb.minhasfinancas.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jlcb.minhasfinancas.model.Usuario;
import com.jlcb.minhasfinancas.model.repository.UsuarioRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository usuarioRepository;

	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		
		/* Cenário */
		Usuario usuario = Usuario.builder().nome("Usuário").email("usuario@email.com").build();
		usuarioRepository.save(usuario);
		
		/* Ação/Execução */
		boolean resultado = usuarioRepository.existsByEmail("usuario@email.com");
		
		/* Verificação */
		Assertions.assertThat(resultado).isTrue();
		
	}
}
