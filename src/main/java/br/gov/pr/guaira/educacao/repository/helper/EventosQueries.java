package br.gov.pr.guaira.educacao.repository.helper;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.pr.guaira.educacao.filter.EventoFilter;
import br.gov.pr.guaira.educacao.model.Evento;


public interface EventosQueries {

	public Page<Evento> filtrar(EventoFilter eventoFilter, Pageable pageable);
	//public List<Evento> buscaSemanasSite(String usuario, Serie serie);
}
