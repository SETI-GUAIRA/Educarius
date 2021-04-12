package br.gov.pr.guaira.educacao.filter;

import java.time.LocalDate;

import org.springframework.data.domain.Example;

public class PedidoFilter {

	private LocalDate dataPedido;	
	
	public LocalDate getDataPedido() {
		return dataPedido;
	}
	public void setDataPedido(LocalDate data_cadastro) {
		this.dataPedido = data_cadastro;
	}
}
