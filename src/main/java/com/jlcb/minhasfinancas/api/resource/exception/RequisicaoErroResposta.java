package com.jlcb.minhasfinancas.api.resource.exception;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequisicaoErroResposta {

    private final String mensagem;
    private final int codigo;
    private final String status;
    private final List<AtributoErroMensagem> erros;
}
