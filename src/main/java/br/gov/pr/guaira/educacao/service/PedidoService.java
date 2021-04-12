package br.gov.pr.guaira.educacao.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.pr.guaira.educacao.exception.ImpossivelExcluirEntidadeException;
import br.gov.pr.guaira.educacao.exception.PedidoJaCadastradoException;
import br.gov.pr.guaira.educacao.model.Pedido;
import br.gov.pr.guaira.educacao.repository.Pedidos;
import br.gov.pr.guaira.educacao.repository.Pedidos_Item;




@Service
public class PedidoService {

	@Autowired
	private Pedidos pedidos;
	@Autowired
	private Pedidos_Item pedidos_Item;
	
		
	public void salvar(Pedido pedido) {
		pedido.setDataPedido(LocalDate.now());		
	//	Optional<Pedido> pedidoOptional = this.pedidos.findBydataPedido(pedido.getDataPedido());
		Optional<Pedido> pedidoOptional = this.pedidos.VerificaJaexistePedido(pedido.getDataPedido().getMonthValue());
		//pedidos.VerificaJaexistePedido(pedido.getDataPedido().getMonth());	
//			if(pedidoOptional.get().getDataPedido().getMonth().equals(LocalDate.now().getMonth())) {
//				throw new PedidoJaCadastradoException("Pedido já está cadastrado!");
//			}
		
			if(pedidoOptional.isPresent() && pedido.isNova()) {
				throw new PedidoJaCadastradoException("Pedido já está cadastrado!");
			}		
		 pedidos.saveAndFlush(pedido);
	}
	
	@Transactional
	public void excluir(Pedido pedido) {		
		try {
			this.pedidos.delete(pedido);
			this.pedidos.flush();
		}catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Não foi possível excluir!");
		}
		
	}

//	public List<Colegio> buscarTodos() {
//	    return colegios.findAllByOrderByNomeAsc();
//	  }
	
}
