package br.gov.pr.guaira.educacao.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode
@Entity
@Table(name="pedido_item")
public class Pedido_Item {

	@EmbeddedId
	private PedidoItemId id;
	
	private Boolean recebeu;
	
	
	
	
	
}
