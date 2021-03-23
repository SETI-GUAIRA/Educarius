package br.gov.pr.guaira.educacao.service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.pr.guaira.educacao.model.PedidoKitAlimentacao;
import br.gov.pr.guaira.educacao.model.Semana;
import br.gov.pr.guaira.educacao.repository.PedidoKitAlimentacoes;
import br.gov.pr.guaira.educacao.repository.Semanas;


@Service
public class PedidoKitAlimentacaoService {

	@Autowired
	private PedidoKitAlimentacoes pedidoKitAlimentacoes;
	
	public void salvar(PedidoKitAlimentacao pedidoKitAlimentacao) {	
		this.pedidoKitAlimentacoes.saveAndFlush(pedidoKitAlimentacao);
	}

//	@Transactional
//	public void excluir(Semana semana) {
//		
//		try {
//			this.semanas.delete(semana);
//			this.semanas.flush();
//		}catch (RuntimeException e) {
//			throw new ImpossivelExcluirEntidadeException("Esta semana não pode ser excluída!");
//		}
//	}
//	
//	private void confereDatas(Semana semana) {
//		if(semana.getDataInicial().isBefore(LocalDate.now())) {
//			throw new DataValidacaoException("Data inicial incorreta!");
//		}
//		
//		if(semana.getDataInicial().isAfter(semana.getDataFinal())) {
//			throw new DataValidacaoException("Data inicial incorreta!");
//		}
//		
//		if(semana.getDataFinal().isBefore(LocalDate.now())) {
//			throw new DataValidacaoException("Data final incorreta!");
//		}
//		
//		if(semana.getDataFinal().isBefore(semana.getDataInicial())) {
//			throw new DataValidacaoException("Data final incorreta");
//		}
//		
//		if(semana.getDataFinal().isEqual(semana.getDataInicial())) {
//			throw new DataValidacaoException("Datas iguais!");
//		}
//	}
}
