package com.example.algamoney.api.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Lancamento_;
import com.example.algamoney.api.model.filter.LancamentoFilter;

public class LancamentoRepositoryQueryImpl implements LancamentoRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	
	@Override
	public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();	
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class); 
		
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = this.getPredicates(filter, builder, root);
		criteria.where(predicates);
		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		
        restricoesPaginacao(query, pageable);
		
		
		return   new PageImpl(query.getResultList(),pageable, total(filter))  ;
	}


	private long total(LancamentoFilter filter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		
		Predicate[] predicates = this.getPredicates(filter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
		
	}


	private void restricoesPaginacao(TypedQuery<Lancamento> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina; 
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}


	private Predicate[] getPredicates(LancamentoFilter filter, CriteriaBuilder builder, Root<Lancamento> root) 
	{
		List<Predicate> predicates = new ArrayList<>();
        if(!StringUtils.isEmpty(filter.getDescricao()))
        {
        	predicates.add(builder.like(builder.lower(root.get(Lancamento_.descricao)), "%" + 
        			filter.getDescricao().toLowerCase() + "%"));
        }
        
        if(filter.getDataVencimentoDe()!=null)
        {
        	predicates.add(builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), filter.getDataVencimentoDe()));
        }
        
        if(filter.getDataVencimentoAte()!=null) {
        	predicates.add(builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), filter.getDataVencimentoAte()));
        }
        
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
