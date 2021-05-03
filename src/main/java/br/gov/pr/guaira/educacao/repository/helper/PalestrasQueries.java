package br.gov.pr.guaira.educacao.repository.helper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.pr.guaira.educacao.filter.PalestraFilter;
import br.gov.pr.guaira.educacao.model.Palestra;

public interface PalestrasQueries {

	public Page<Palestra> filtrar(PalestraFilter palestraFilter, Pageable pageable);
	public List<Palestra> buscaPalestraCadastrada(Palestra palestra);
}
