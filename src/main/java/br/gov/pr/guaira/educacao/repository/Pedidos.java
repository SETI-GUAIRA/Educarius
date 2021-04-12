 package br.gov.pr.guaira.educacao.repository;




import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import br.gov.pr.guaira.educacao.model.Pedido;
import br.gov.pr.guaira.educacao.repository.helper.PedidosQueries;



public interface Pedidos extends JpaRepository<Pedido, Long>, PedidosQueries{

		
	Optional<Pedido> findBydataPedido(LocalDate data_pedido);

	Optional<Pedido> VerificaJaexistePedido(int monthValue);
	
	public List<Pedido> findAllByOrderByCodigoDesc();

	
	
}
