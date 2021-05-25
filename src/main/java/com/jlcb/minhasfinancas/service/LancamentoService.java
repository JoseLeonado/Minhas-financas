package com.jlcb.minhasfinancas.service;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jlcb.minhasfinancas.model.Lancamento;
import com.jlcb.minhasfinancas.model.enums.StatusLancamento;
import com.jlcb.minhasfinancas.model.repository.LancamentoRepository;
import com.jlcb.minhasfinancas.service.interfaces.LancamentoServiceInterface;

@Service
public class LancamentoService implements LancamentoServiceInterface {
	
	private LancamentoRepository lancamentoRepository;
	
	/* "Injetando" a dependência */
	public LancamentoService(LancamentoRepository lancamentoRepository) {
		this.lancamentoRepository = lancamentoRepository;
	}

	@Override
	@Transactional
	public Lancamento salvar(Lancamento lancamento) {
		return lancamentoRepository.save(lancamento);
	}

	@Override
	@Transactional
	public Lancamento atualizar(Lancamento lancamento) {
		
		Objects.requireNonNull(lancamento.getId()); /* Checando se o id não é nulo, ou seja, verificar se está sendo passado um id */
		
		return salvar(lancamento);
	}

	@Override
	public void deletar(Lancamento lancamento) {

		Objects.requireNonNull(lancamento.getId());

		lancamentoRepository.delete(lancamento);
	}

	@Override
	public List<Lancamento> buscar(Lancamento lancamentoFiltros) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void atulizarStatus(Lancamento lancamento, StatusLancamento status) {
		
		lancamento.setStatus(status);
		
		atualizar(lancamento);
	}
}
