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
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import br.gov.pr.guaira.educacao.model.validation.group.CnpjGroup;
import br.gov.pr.guaira.educacao.model.validation.group.CpfGroup;
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
	private String nomeAluno;
	
	@NotBlank(message="Informe o nome do responsável")	
	@Column(nullable = false)
	private String nome_responsavel;
	
//	@NotBlank(message="Informe o CPF")	
//	@CPF(message = "Número do CPF está inválido!")	
//	@Column(nullable = false)
//	private String cpf;	
//	private String rg;	
	private String telefone_contato;
	@Column(nullable = false)
	private LocalDate data_cadastro;
	
//	TESTE CPF BRA PY
	@Column(name = "tipo_pessoa")	
	private TipoPessoa tipoPessoa;
	@NotBlank(message = "CPF/RG/IC é obrigatório!")
	@CNPJ(groups = CnpjGroup.class)
	@CPF(groups = CpfGroup.class)
	@Column(name = "cpf")
	private String cpfOuCnpj;
	
		
	
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
		this.nomeAluno = this.nomeAluno.toUpperCase();
		this.nome_responsavel = this.nome_responsavel.toUpperCase();
	}
	
	
	
	public boolean isNova() {
		return this.codigo == null;
	}


	
}
