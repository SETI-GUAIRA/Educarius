package br.gov.pr.guaira.educacao.filter;

import java.time.LocalDate;
import br.gov.pr.guaira.educacao.model.Colegio;
import br.gov.pr.guaira.educacao.model.Serie;
import lombok.Data;


@Data
public class KitAlimentacaoFilter {
    
	private LocalDate dataCadastro;
	private Colegio colegio;	
	private Serie serie;

	
	
}
