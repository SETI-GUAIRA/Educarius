package br.gov.pr.guaira.educacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pr.guaira.educacao.model.Evento;
import br.gov.pr.guaira.educacao.repository.helper.EventosQueries;

public interface Eventos extends JpaRepository<Evento, Long>, EventosQueries {

	public List<Evento> findByOrderByDataFinalDesc();

	///public Object findByOrderByNomeEventoAsc();

}
