package br.gov.pr.guaira.educacao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import br.gov.pr.guaira.educacao.model.SerieColegio;
import br.gov.pr.guaira.educacao.repository.helper.SeriesColegiosQueries;

public interface SeriesColegios extends JpaRepository<SerieColegio, Long>, SeriesColegiosQueries {

	public Optional<SerieColegio> findByNomeIgnoreCase(String nome);
	public List<SerieColegio> findByOrderByNomeAsc();
}
