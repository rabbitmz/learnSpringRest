package com.example.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.IPessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private IPessoaRepository pessoaRepository;
	
	public Optional<Pessoa> atualizarActivo(Long codigo, boolean ativo) {
		
		Optional<Pessoa> pessoaSalva = pessoaRepository.findById(codigo);
		pessoaSalva.get().setActivo(ativo);
		pessoaRepository.save(pessoaSalva.get());
		return pessoaSalva;
	}
	
		

}
