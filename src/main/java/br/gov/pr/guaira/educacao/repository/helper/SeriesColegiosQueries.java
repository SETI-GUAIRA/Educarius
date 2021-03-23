package br.gov.pr.guaira.educacao.repository.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.pr.guaira.educacao.filter.SerieColegioFilter;


import br.gov.pr.guaira.educacao.model.SerieColegio;


public interface SeriesColegiosQueries {

	public Page<SerieColegio> filtrar(SerieColegioFilter serieColegioFilter, Pageable pageable);
	
}
