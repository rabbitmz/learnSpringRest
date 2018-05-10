package com.example.algamoney.api.resource;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.event.ResourceCreatedEvent;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.filter.LancamentoFilter;
import com.example.algamoney.api.service.LancamentoService;

@RestController
@RequestMapping(LancamentoResource.PATH)
public class LancamentoResource {
	public static final String PATH = "/lancamento";

	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<Lancamento> getLancamentos(LancamentoFilter filter, Pageable pageable)
	{
		return (Page<Lancamento>) this.lancamentoService.getLancamentos(filter, pageable);
	}
	
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> getLancamento(@PathVariable final Long codigo)
	{
		return ResponseEntity.ok().body(this.lancamentoService.getLancamentoById(codigo));
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> save(@RequestBody final Lancamento lancamento, HttpServletResponse response)
	{
		Lancamento save = this.lancamentoService.save(lancamento);
	
		this.publisher.publishEvent(new ResourceCreatedEvent(this, response, save.getCodigo()));
		
		return ResponseEntity.ok().body(save);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable final Long codigo)
	{
		this.lancamentoService.remove(codigo);
	}
}
