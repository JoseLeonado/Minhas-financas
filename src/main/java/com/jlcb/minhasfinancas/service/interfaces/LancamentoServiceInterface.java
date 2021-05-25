package com.jlcb.minhasfinancas.service.interfaces;

import java.util.List;

import com.jlcb.minhasfinancas.model.Lancamento;
import com.jlcb.minhasfinancas.model.enums.StatusLancamento;

public interface LancamentoServiceInterface {

	Lancamento salvar(Lancamento lancamento);

	Lancamento atualizar(Lancamento lancamento);

	void deletar(Lancamento lancamento);

	List<Lancamento> buscar(Lancamento lancamentoFiltros);
	
	void atulizarStatus(Lancamento lancamento, StatusLancamento status);	
}
