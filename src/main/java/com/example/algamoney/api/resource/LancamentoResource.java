package com.example.algamoney.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.service.LancamentoService;

@RestController
@RequestMapping(LancamentoResource.PATH)
public class LancamentoResource {
	public static final String PATH = "/lancamento";

	@Autowired
	private LancamentoService lancamentoService; 
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Lancamento> getLancamentos()
	{
		return this.lancamentoService.getLancamentos();
	}
	
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> getLancamento(@PathVariable final Long codigo)
	{
		return ResponseEntity.ok().body(this.lancamentoService.getLancamentoById(codigo));
	}
}
