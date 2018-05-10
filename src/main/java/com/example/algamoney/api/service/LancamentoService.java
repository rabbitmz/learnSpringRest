package com.example.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.model.filter.LancamentoFilter;
import com.example.algamoney.api.repository.ICategoriaRepository;
import com.example.algamoney.api.repository.ILancamentoRepository;
import com.example.algamoney.api.repository.IPessoaRepository;

@Service
public class LancamentoService {

	@Autowired
	private ILancamentoRepository lancamentoRepository;
	@Autowired
	private ICategoriaRepository categoriaRepository;
	@Autowired
	private IPessoaRepository pessoaRepository;

	public Page<Lancamento> getLancamentos(LancamentoFilter filter, Pageable pageable) {
		return this.lancamentoRepository.filtrar(filter, pageable);
	}

	public Lancamento getLancamentoById(Long codigo) {
		
		return this.lancamentoRepository.getOne(codigo);
	}

	public Lancamento save(final Lancamento lancamento) {
		
		Optional<Categoria> categoria = this.categoriaRepository.findById(lancamento.getCategoria().getCodigo());
		lancamento.setCategoria(categoria.get());
		
		Optional<Pessoa> pessoa = this.pessoaRepository.findById(lancamento.getPessoa().getCodigo());
		if(!pessoa.isPresent() || !pessoa.get().isActivo())
		{
			throw new IllegalArgumentException();
		}
		lancamento.setPessoa(pessoa.get());
		Lancamento saved = this.lancamentoRepository.save(lancamento);
		return saved;
	}

	public void remove(final Long codigo) {
		this.lancamentoRepository.deleteById(codigo);
	} 
	
}
