package br.gov.pr.guaira.educacao.repository.helper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.pr.guaira.educacao.filter.AulaFilter;
import br.gov.pr.guaira.educacao.model.Aula;
import br.gov.pr.guaira.educacao.model.Palestra;

public interface AulasQueries {

	public Page<Aula> filtrar(AulaFilter aulaFilter, Pageable pageable);
	public List<Aula> buscaAulaCadastrada(Aula aula);
	
}
