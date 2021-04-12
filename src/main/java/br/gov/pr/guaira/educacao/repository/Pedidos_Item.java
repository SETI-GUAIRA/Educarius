package br.gov.pr.guaira.educacao.repository;




import java.time.LocalDate;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pr.guaira.educacao.filter.PedidoFilter;
import br.gov.pr.guaira.educacao.model.Pedido;
import br.gov.pr.guaira.educacao.model.PedidoItemId;
import br.gov.pr.guaira.educacao.model.Pedido_Item;
import br.gov.pr.guaira.educacao.model.Serie;
import br.gov.pr.guaira.educacao.repository.helper.PedidosQueries;
import br.gov.pr.guaira.educacao.repository.helper.Pedidos_ItemQueries;


public interface Pedidos_Item extends JpaRepository<Pedido_Item, Long>, Pedidos_ItemQueries{

	public Optional<Pedido_Item> findById(PedidoItemId id);
//	Optional<Pedido> findBydataPedido(LocalDate data_pedido);

	

	
	

	
	

	//public Object count(PedidoFilter pedidoFilter);

	
	
	
	
}
