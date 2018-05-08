package com.example.algamoney.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.ICategoriaRepository;

@RestController
@RequestMapping(CategoriaResource.PATH)
public class CategoriaResource {

	public static final String PATH = "/categorias";
	
	@Autowired	
	private ICategoriaRepository categoriaRepository;
	
	@GetMapping
	public List<Categoria> listar()
	{
		return categoriaRepository.findAll();
	}
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response)
	{
		Categoria saved = categoriaRepository.save(categoria);

		
		
		publisher.publishEvent(new ResourceCreatedEvent(this, response, categoria.getCodigo()));
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo)
	{
		Optional<Categoria> optional = categoriaRepository.findById(codigo);
		
		return optional.isPresent() ? ResponseEntity.ok(optional.get()) : ResponseEntity.notFound().build();	
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover (@PathVariable Long codigo)
	{
		this.categoriaRepository.deleteById(codigo);
	}
	
}
