package com.example.algamoney.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.ILancamentoRepository;

@Service
public class LancamentoService {

	@Autowired
	private ILancamentoRepository lancamentoRepository;

	public List<Lancamento> getLancamentos() {
		return this.lancamentoRepository.findAll();
	}

	public Lancamento getLancamentoById(Long codigo) {
		
		return this.lancamentoRepository.getOne(codigo);
	} 
	
}
