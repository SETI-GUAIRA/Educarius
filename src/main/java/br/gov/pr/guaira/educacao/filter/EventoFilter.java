package br.gov.pr.guaira.educacao.filter;

import java.time.LocalDate;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class EventoFilter {

	private LocalDate dataInicial;
	private LocalDate dataFinal;
	private String    nome;		
	
}
