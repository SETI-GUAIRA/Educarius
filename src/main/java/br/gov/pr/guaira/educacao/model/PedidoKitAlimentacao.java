package br.gov.pr.guaira.educacao.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Data
@Entity
@Table(name = "pedidoKitAlimentacao")
@DynamicUpdate
public class PedidoKitAlimentacao implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;	
	@Column
	private boolean recebeu;
	@Column(name = "dataCadastro")
	private LocalDate dataCadastro;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kitAlimentacao", referencedColumnName = "codigo")
	private KitAlimentacao kitAlimentacao;
	
}
