package com.jlcb.minhasfinancas.api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class AutenticacaoUsuarioDTO {
	
	private String email;
	private String senha;
}
