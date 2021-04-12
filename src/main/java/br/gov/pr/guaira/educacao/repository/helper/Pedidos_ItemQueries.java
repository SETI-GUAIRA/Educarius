package br.gov.pr.guaira.educacao.repository.helper;



import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.pr.guaira.educacao.filter.ColegioFilter;
import br.gov.pr.guaira.educacao.filter.PedidoFilter;
import br.gov.pr.guaira.educacao.model.Colegio;
import br.gov.pr.guaira.educacao.model.Pedido;
import br.gov.pr.guaira.educacao.model.Pedido_Item;



public interface Pedidos_ItemQueries {
	
	//public Page<Pedido> filtrar(PedidoFilter pedidoFilter, Pageable pageable);
	public Object totalPedidoMes(Long codigoPedido);
	public Optional<Pedido_Item> VerificaJaexisteAlunoItemPedido(Long codigo);

	public Object GetMes();		


}
