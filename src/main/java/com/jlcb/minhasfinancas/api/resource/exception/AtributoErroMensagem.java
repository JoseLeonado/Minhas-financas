package com.jlcb.minhasfinancas.api.resource.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AtributoErroMensagem {

	private final String campo;
	private final Object valor;
    private final String erro;
}
