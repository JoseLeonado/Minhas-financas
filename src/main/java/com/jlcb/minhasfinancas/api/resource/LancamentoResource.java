package com.jlcb.minhasfinancas.api.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jlcb.minhasfinancas.api.dto.LancamentoDTO;
import com.jlcb.minhasfinancas.model.Lancamento;
import com.jlcb.minhasfinancas.model.Usuario;
import com.jlcb.minhasfinancas.model.enums.StatusLancamento;
import com.jlcb.minhasfinancas.model.enums.TipoLancamento;
import com.jlcb.minhasfinancas.service.LancamentoService;
import com.jlcb.minhasfinancas.service.UsuarioService;
import com.jlcb.minhasfinancas.service.exception.RegraDeNegocioException;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody LancamentoDTO lancamentoDTO) {
		
		
		
	}
	
	private Lancamento converterDtoParaLancamento(LancamentoDTO lancamentoDTO) {
		
		Lancamento lancamento = new Lancamento();
		lancamento.setId(lancamentoDTO.getId());
		lancamento.setDescricao(lancamentoDTO.getDescricao());
		lancamento.setMes(lancamentoDTO.getMes());
		lancamento.setAno(lancamentoDTO.getAno());
		lancamento.setValor(lancamentoDTO.getValor());
		lancamento.setTipo(TipoLancamento.valueOf(lancamentoDTO.getTipo()));
		lancamento.setStatus(StatusLancamento.valueOf(lancamentoDTO.getStatus()));
		
		Usuario usuario = usuarioService.obterUsuarioPorId(lancamentoDTO.getId()).orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado para o id informado"));
		lancamento.setUsuario(usuario);
		
		return lancamento;
	}
}
