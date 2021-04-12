package br.gov.pr.guaira.educacao.repository.helper;



import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import javax.xml.crypto.Data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.pr.guaira.educacao.filter.ColegioFilter;
import br.gov.pr.guaira.educacao.filter.PedidoFilter;
import br.gov.pr.guaira.educacao.model.Colegio;
import br.gov.pr.guaira.educacao.model.Pedido;



public interface PedidosQueries {
	
	public Page<Pedido> filtrar(PedidoFilter pedidoFilter, Pageable pageable);

	Optional<Pedido> VerificaJaexistePedido(int monthValue);
	
}
