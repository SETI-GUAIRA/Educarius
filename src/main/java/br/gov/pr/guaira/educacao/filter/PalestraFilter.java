package br.gov.pr.guaira.educacao.filter;

import java.time.LocalDate;

import br.gov.pr.guaira.educacao.model.Evento;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class PalestraFilter {

	private LocalDate dataInicial;
	private Evento evento;
	private String nomePalestrante;

	
	
}
