package br.gov.pr.guaira.educacao.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class PedidoItemId implements Serializable{

	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name="codigo_kit_alimentacao")
	private KitAlimentacao kitAlimentacao;
	@ManyToOne
	@JoinColumn(name="codigo_pedido")
	private Pedido pedido;
	
	public KitAlimentacao getKitAlimentacao() {
		return kitAlimentacao;
	}
	public void setKitAlimentacao(KitAlimentacao kitAlimentacao) {
		this.kitAlimentacao = kitAlimentacao;
	}
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pedido == null) ? 0 : pedido.hashCode());
		result = prime * result + ((kitAlimentacao == null) ? 0 : kitAlimentacao.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidoItemId other = (PedidoItemId) obj;
		if (pedido == null) {
			if (other.pedido != null)
				return false;
		} else if (!pedido.equals(other.pedido))
			return false;
		if (kitAlimentacao == null) {
			if (other.kitAlimentacao != null)
				return false;
		} else if (!kitAlimentacao.equals(other.kitAlimentacao))
			return false;
		return true;
	}
	
	
}
