package br.gov.pr.guaira.educacao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.DynamicUpdate;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "colegio")
@DynamicUpdate
public class Colegio implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotBlank(message = "Informe o nome do colegio!")
	@Column
	private String nome;
	
	@PrePersist
	@PreUpdate
	private void toUpperCase() {
		this.nome = this.nome.toUpperCase();
	}
	
	public boolean isNova() {
		return this.codigo == null;
	}
	
	
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
//		return result;
//	}
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Colegio other = (Colegio) obj;
//		if (codigo == null) {
//			if (other.codigo != null)
//				return false;
//		} else if (!codigo.equals(other.codigo))
//			return false;
//		return true;
//	}
	
}
