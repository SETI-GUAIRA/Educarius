package br.gov.pr.guaira.educacao.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode

@Entity
@Table(name="kitAlimentacao")
@DynamicUpdate
public class KitAlimentacao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;	
	@NotBlank(message="Informe o nome do aluno")
	@Column(nullable = false)
	private String nome_aluno;
	@NotBlank(message="Informe o nome do responsável")	
	@Column(nullable = false)
	private String nome_responsavel;
	@NotBlank(message="Informe o CPF")	
	@CPF(message = "Número do CPF está inválido!")	
	@Column(nullable = false)
	private String cpf;	
	private String rg;
	
	private String telefone_contato;
	@Column(nullable = false)
	private LocalDate data_cadastro;	
	
	@NotNull(message = "Informe o colegio")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "colegio", referencedColumnName = "codigo")
	private Colegio colegio;	
	
	@NotNull(message = "Informe a série")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "serieColegio", referencedColumnName = "codigo")
	private SerieColegio serieColegio;
	
	@NotNull(message = "Informe o turno")
	@Enumerated(EnumType.STRING)	
	private Turno turno;
	
	@NotNull(message = "Informe a Turma")
	@Enumerated(EnumType.STRING)
	private Turma turma;
	
	
	
	@Column
	private Boolean ativo = true;
	
	
	@PrePersist
	@PreUpdate
	private void toUpperCase() {
		this.nome_aluno = this.nome_aluno.toUpperCase();
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
//		KitAlimentacao other = (KitAlimentacao) obj;
//		if (codigo == null) {
//			if (other.codigo != null)
//				return false;
//		} else if (!codigo.equals(other.codigo))
//			return false;
//		return true;
//	}
	
}
