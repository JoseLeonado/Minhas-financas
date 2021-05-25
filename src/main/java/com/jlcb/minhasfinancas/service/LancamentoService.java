package com.jlcb.minhasfinancas.service;

import java.util.List;

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
	public Lancamento atualizar(Lancamento lancamento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletar(Lancamento lancamento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Lancamento> buscar(Lancamento lancamentoFiltros) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void atulizarStatus(Lancamento lancamento, StatusLancamento status) {
		// TODO Auto-generated method stub
		
	}
}
