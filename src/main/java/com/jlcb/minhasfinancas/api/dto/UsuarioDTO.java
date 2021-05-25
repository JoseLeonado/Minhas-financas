package com.jlcb.minhasfinancas.api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class UsuarioDTO {
	
	@NotBlank(message = "Informe um nome")
	private String nome;

	@Email(message = "O email informado está no formato inváldio")
	@NotBlank(message = "Informe um email")
	private String email;

	@NotBlank(message = "Informe uma senha")
	private String senha;
}
