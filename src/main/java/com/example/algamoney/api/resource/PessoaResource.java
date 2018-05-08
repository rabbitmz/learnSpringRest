package com.example.algamoney.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.event.ResourceCreatedEvent;
import com.example.algamoney.api.model.Endereco;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.IPessoaRepository;
import com.example.algamoney.api.service.PessoaService;

@RestController
@RequestMapping(PessoaResource.PATH)
public class PessoaResource {

	public static final String PATH = "/pessoas";

	@Autowired
	private IPessoaRepository pessoaRepository;

	@Autowired
	private ApplicationEventPublisher publisher; 

	@Autowired
	private PessoaService pessoaService;
	
	@PostMapping
	public ResponseEntity<Pessoa> create(@Valid @RequestBody final Pessoa pessoa, final HttpServletResponse response) {
		Pessoa save = this.pessoaRepository.save(pessoa);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, save.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(save);

	}

	@GetMapping
	public List<Pessoa> findAll()
	{
		return this.pessoaRepository.findAll();
	}
	
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> findByCode(final Long code)
	{
		Optional<Pessoa> found = this.pessoaRepository.findById(code);
		
		return found.isPresent() ?  ResponseEntity.ok(found.get()) : ResponseEntity.notFound().build();  
	}
	
	@GetMapping("/location/{codigo}")
	public ResponseEntity<Endereco> findByPersonCode(final Long personCode)
	{
		Optional<Pessoa> found = this.pessoaRepository.findById(personCode);
		
		return found.isPresent() ? ResponseEntity.ok(found.get().getEndereco()) : ResponseEntity.notFound().build();
	}
	
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Optional<Pessoa>> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa)
	{
		Optional<Pessoa> pessoaSalva = pessoaRepository.findById(codigo);
		if(pessoaSalva.get()==null)
		{
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(pessoa, pessoaSalva.get(),"codigo");
		pessoaRepository.save(pessoaSalva.get());
		return ResponseEntity.ok().body(pessoaSalva);
	}
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Optional<Pessoa>> atualizarActivo(@PathVariable Long codigo,@RequestBody boolean ativo )
	{
		return ResponseEntity.ok().body(this.pessoaService.atualizarActivo(codigo,ativo));
		
	}
	
	
	
}
