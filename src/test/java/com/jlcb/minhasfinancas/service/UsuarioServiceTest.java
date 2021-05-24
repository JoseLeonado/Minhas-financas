package com.jlcb.minhasfinancas.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jlcb.minhasfinancas.exception.EmailException;
import com.jlcb.minhasfinancas.model.Usuario;
import com.jlcb.minhasfinancas.model.repository.UsuarioRepository;
import com.jlcb.minhasfinancas.service.interfaces.UsuarioServiceInterface;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioServiceTest {

	@Autowired
	UsuarioServiceInterface usuarioServiceInterface;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Test
	public void deveValidarEmail() {

		Assertions.assertDoesNotThrow(() -> {
			/* Cenário */
			usuarioRepository.deleteAll();

			/* Acão */
			usuarioServiceInterface.validarEmail("email@email.com");
		});
	}

	@Test
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {

		Assertions.assertThrows(EmailException.class, () -> {
			/* Cenário */
			Usuario usuario = Usuario.builder().nome("Usuário").email("email@email.com").build();
			usuarioRepository.save(usuario);

			/* Acão */
			usuarioServiceInterface.validarEmail("email@email.com");
		});
	}
}
