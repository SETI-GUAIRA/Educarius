package br.gov.pr.guaira.educacao.filter;

import java.time.LocalDate;
import br.gov.pr.guaira.educacao.model.Colegio;
import br.gov.pr.guaira.educacao.model.Pedido;
import br.gov.pr.guaira.educacao.model.SerieColegio;
import br.gov.pr.guaira.educacao.model.Turma;
import br.gov.pr.guaira.educacao.model.Turno;
import lombok.Data;


@Data
public class KitAlimentacaoFilter {
    
	private LocalDate dataCadastro;
	private Colegio colegio;	
	private SerieColegio serieColegio;
	private Pedido pedido;
	private Turma turma;
	private Turno turno;
	private String nomeAluno;
}
