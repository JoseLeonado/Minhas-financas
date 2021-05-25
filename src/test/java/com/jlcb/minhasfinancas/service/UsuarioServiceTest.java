package com.jlcb.minhasfinancas.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jlcb.minhasfinancas.exception.AutenticacaoException;
import com.jlcb.minhasfinancas.exception.EmailException;
import com.jlcb.minhasfinancas.model.Usuario;
import com.jlcb.minhasfinancas.model.repository.UsuarioRepository;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@SpyBean
	UsuarioService usuarioService;

	@MockBean
	UsuarioRepository usuarioRepository;

	@Test
	public void deveSalvarUmUsuario() {
		
		org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> { /* Garantindo que não vamos lançar uma exceção */
		
			/* Cenário */
			Mockito.doNothing().when(usuarioService).validarEmail(Mockito.anyString()); /* Não faça nada ao chamar o método de validarEmail() */
			Usuario usuario = Usuario.builder().id(1L).nome("Nome").email("email@email.com").senha("senha").build();
					
			Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
			
			/* Ação */
			Usuario usuarioSalvo = usuarioService.salvarUsuario(usuario);
			
			/* Verificação */
			Assertions.assertThat(usuarioSalvo).isNotNull();
			Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1L);
			Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("Nome");
			Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
			Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
		});
	}

	@Test
	public void deveAutenticarUmUsuarioComSucesso() {

		org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> { /* Garantindo que não vamos lançar uma exceção */

			/* Cenário */
			String email = "email@email.com";
			String senha = "senha";

			Usuario usuario = Usuario.builder().id(1L).email(email).senha(senha).build();
			Mockito.when(usuarioRepository.findByEmail(email))
					.thenReturn(Optional.of(usuario)); /* Retorna um optional com o usuário do parâmetro */

			/* Ação */
			Usuario resultado = usuarioService.autenticar(email, senha);

			/* Verificação */
			Assertions.assertThat(resultado).isNotNull();
		});
	}
	
	@Test
	public void deveLancarUmErroQuandoNaoEncontrarUsuarioCadastradoComEmailInformado() {

		/* Cenário */
		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty()); /* Estamos passando qualquer e-mail para a busca de usuário por um e-mail */

		/* Ação */
		Throwable exception = Assertions.catchThrowable(() -> usuarioService.autenticar("email@email.com", "senha"));
		
		/* Verificação */
		Assertions.assertThat(exception)
				.isInstanceOf(AutenticacaoException.class)
				.hasMessage("Usuário não encontrado para o e-mail informado."); /* Verifique se a exceção foi lançada corretamente com a mensagem certa */
	}
	
	@Test
	public void naoDeverSalvarUsuarioComUmEmailJaCadastrado() {
		
		org.junit.jupiter.api.Assertions.assertThrows(EmailException.class, () -> {
			/* Cenário */
			String email = "email@email.com";
			Usuario usuario = Usuario.builder().email(email).build();
			
			Mockito.doThrow(EmailException.class).when(usuarioService).validarEmail(email); /* Deveremos retornar a exceção */
			
			/* Açaõ */
			usuarioService.salvarUsuario(usuario);
			
			/* Verificação */
			Mockito.verify(usuarioRepository, Mockito.never()).save(usuario); /* Verifica que nunca foi salvado o usuário */	
		});
	}

	@Test
	public void deveLancarUmErroQuandoSenhaDoUsuarioEstiverErrada() {

		/* Cenário */
		String senha = "senha";
		Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();
		
		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

		/* Ação */
		Throwable exception = Assertions.catchThrowable(() -> 
				usuarioService.autenticar("email@email.com", "123")); /* Simulando que não foi passada a senha correta */

		/* Verificação */
		Assertions.assertThat(exception)
				.isInstanceOf(AutenticacaoException.class)
				.hasMessage("Senha inválida."); /* Verifique se a exceção foi lançada corretamente com a mensagem certa */
	}

	@Test
	public void deveValidarSeExisteEmail() {

		org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> {

			/* Cenário */
			Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);

			/* Acão */
			usuarioService.validarEmail("email@email.com");
		});
	}

	@Test
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {

		org.junit.jupiter.api.Assertions.assertThrows(EmailException.class, () -> {

			/* Cenário */
			Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

			/* Acão */
			usuarioService.validarEmail("email@email.com");
		});
	}
}
