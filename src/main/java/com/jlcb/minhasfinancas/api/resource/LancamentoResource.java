package com.jlcb.minhasfinancas.api.resource;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jlcb.minhasfinancas.api.dto.AtualizaStatusLancamentoDTO;
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
	
	@GetMapping
	public ResponseEntity<?> buscar(
			@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam(value = "mes", required = false) Integer mes,
			@RequestParam(value =  "ano", required = false) Integer ano,
			@RequestParam("usuario") Long idUsuario
			) {
		
		Lancamento lancamentoFiltros = new Lancamento();
		lancamentoFiltros.setDescricao(descricao);
		lancamentoFiltros.setMes(mes);
		lancamentoFiltros.setAno(ano);
		
		Optional<Usuario> usuario = usuarioService.obterUsuarioPorId(idUsuario); /* Pode ou não retornar um usuário */
		
		if (!usuario.isPresent()) {
			return ResponseEntity.badRequest().body("Não foi possível realizar a consulta. Usuário não encontrado para o id informado");
		} else {
			lancamentoFiltros.setUsuario(usuario.get());
		}
		
		List<Lancamento> lancamentosEncontradoNaBuscaPelosFiltros = lancamentoService.buscar(lancamentoFiltros);
		
		return ResponseEntity.ok(lancamentosEncontradoNaBuscaPelosFiltros);
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody LancamentoDTO lancamentoDTO) {
		
		try {
			
			Lancamento lancamento = converterDtoParaLancamento(lancamentoDTO);
			lancamentoService.salvar(lancamento);
			
			return new ResponseEntity<>(lancamento, HttpStatus.CREATED);
		} catch (RegraDeNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}") /* Editar */
	public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @Valid @RequestBody LancamentoDTO lancamentoDTO) {
		
		return	lancamentoService.obterLancamentoPorId(id).map(lancamentoEncontrado -> { /* Caso encontre um lançamento pelo id, então iremos atualiza o mesmo */
			
			try {
				
				Lancamento lancamento =	converterDtoParaLancamento(lancamentoDTO);
				lancamento.setId(lancamentoEncontrado.getId());
				lancamentoService.atualizar(lancamento);
				
				return ResponseEntity.ok(lancamento);
			} catch (RegraDeNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<>("Lançamento não encontrado.", HttpStatus.BAD_REQUEST)); /* Lançar uma exceção caso não encontre o lançamento pelo id passado */
	}
	
	@PutMapping("{id}/atuliza-status")
	public ResponseEntity<?> atualizarStatus(@PathVariable("id") Long id, @RequestBody AtualizaStatusLancamentoDTO atualizaStatusLancamentoDTO) {
		
		return lancamentoService.obterLancamentoPorId(id).map(lancamentoEncontrado -> {
			
			StatusLancamento statusSelecionado = StatusLancamento.valueOf(atualizaStatusLancamentoDTO.getStatus());
			
			if (statusSelecionado == null) {
				return ResponseEntity.badRequest().body("Não foi possível atualizar o status do lançamento, envie um status válido");
			}
			
			try {
				
				lancamentoEncontrado.setStatus(statusSelecionado);
				
				lancamentoService.atualizar(lancamentoEncontrado);
				
				return ResponseEntity.ok(lancamentoEncontrado);	
			} catch (RegraDeNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}		
		}).orElseGet(() -> new ResponseEntity<>("Lançamento não encontrado.", HttpStatus.BAD_REQUEST));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deletar (@PathVariable("id") Long id) {
		
		
//		if (lancamentoEncontrado.isPresent()) {
//			lancamentoService.deletar(lancamentoEncontrado);
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		} else {
//			return new ResponseEntity<>("Lançamento não encontrado.", HttpStatus.BAD_REQUEST);
//		}

		return lancamentoService.obterLancamentoPorId(id).map(lancamentoEncontrado -> { /* Caso encontre um lançamento pelo id, então iremos deletar o mesmo */
			
			lancamentoService.deletar(lancamentoEncontrado);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity<>("Lançamento não encontrado.", HttpStatus.BAD_REQUEST));
	}
	
	private Lancamento converterDtoParaLancamento(LancamentoDTO lancamentoDTO) {
		
		Lancamento lancamento = new Lancamento();
		lancamento.setId(lancamentoDTO.getId());
		lancamento.setDescricao(lancamentoDTO.getDescricao());
		lancamento.setMes(lancamentoDTO.getMes());
		lancamento.setAno(lancamentoDTO.getAno());
		lancamento.setValor(lancamentoDTO.getValor());
		
		if (lancamentoDTO.getTipo() != null) {
			lancamento.setTipo(TipoLancamento.valueOf(lancamentoDTO.getTipo()));
		}
		
		if (lancamentoDTO.getStatus() != null) {
			lancamento.setStatus(StatusLancamento.valueOf(lancamentoDTO.getStatus()));
		}
		
		Usuario usuario = usuarioService.obterUsuarioPorId(lancamentoDTO.getUsuario()).orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado para o id informado"));
		lancamento.setUsuario(usuario);
		
		return lancamento;
	}
}
