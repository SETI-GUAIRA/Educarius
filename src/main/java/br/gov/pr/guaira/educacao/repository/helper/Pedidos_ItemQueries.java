package br.gov.pr.guaira.educacao.repository.helper;




import java.util.List;
import java.util.Optional;




import br.gov.pr.guaira.educacao.model.Pedido_Item;



public interface Pedidos_ItemQueries {
	
	//public Page<Pedido> filtrar(PedidoFilter pedidoFilter, Pageable pageable);
	public Object totalPedidoMes(Long codigoPedido);
	public Optional<Pedido_Item> VerificaJaexisteAlunoItemPedido(Long codigo);

	public Object GetMes();	
	public Pedido_Item retornaPedidoItem(Long codigo);	


}
