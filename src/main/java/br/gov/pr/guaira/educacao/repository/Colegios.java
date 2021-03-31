package br.gov.pr.guaira.educacao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pr.guaira.educacao.model.Colegio;

import br.gov.pr.guaira.educacao.repository.helper.ColegiosQueries;


public interface Colegios extends JpaRepository<Colegio, Long>, ColegiosQueries {

	public Optional<Colegio> findByNomeIgnoreCase(String nome);
	public List<Colegio> findByOrderByNomeAsc();
	public List<Colegio> findAllByOrderByNomeAsc();
	
	
}
