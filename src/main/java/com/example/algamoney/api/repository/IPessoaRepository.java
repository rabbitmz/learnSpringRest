package com.example.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamoney.api.model.Pessoa;

public interface IPessoaRepository extends JpaRepository<Pessoa,Long>{

}
