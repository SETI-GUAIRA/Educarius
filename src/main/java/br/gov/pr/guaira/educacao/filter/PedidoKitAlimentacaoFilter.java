package br.gov.pr.guaira.educacao.filter;

import java.time.LocalDate;

public class PedidoKitAlimentacaoFilter {

	private LocalDate data_cadastro;	
	
	public LocalDate getData_cadastro() {
		return data_cadastro;
	}
	public void setData_cadastro(LocalDate data_cadastro) {
		this.data_cadastro = data_cadastro;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;		
		result = prime * result + ((data_cadastro == null) ? 0 : data_cadastro.hashCode());
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
		PedidoKitAlimentacaoFilter other = (PedidoKitAlimentacaoFilter) obj;		
		if (data_cadastro == null) {
			if (other.data_cadastro != null)
				return false;
		} else if (!data_cadastro.equals(other.data_cadastro))
			return false;
		return true;
	}
	
	
}
