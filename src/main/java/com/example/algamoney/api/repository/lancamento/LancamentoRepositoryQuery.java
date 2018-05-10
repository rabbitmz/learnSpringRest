package com.example.algamoney.api.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable);
	
}
