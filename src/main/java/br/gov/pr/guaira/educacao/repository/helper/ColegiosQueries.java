package br.gov.pr.guaira.educacao.repository.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.pr.guaira.educacao.filter.ColegioFilter;
import br.gov.pr.guaira.educacao.model.Colegio;


public interface ColegiosQueries {
	public Page<Colegio> filtrar(ColegioFilter colegioFilter, Pageable pageable);
}
