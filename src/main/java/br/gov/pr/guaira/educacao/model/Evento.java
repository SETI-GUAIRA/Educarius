package br.gov.pr.guaira.educacao.model;

import java.io.Serializable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import br.gov.pr.guaira.educacao.repository.listener.EventoEntityListener;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@EntityListeners(EventoEntityListener.class)
@Entity
@Table(name = "evento")
@DynamicUpdate
public class Evento implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	@NotBlank(message = "Nome é obrigatório!")
	private String nome;	
	
	private String foto;
	@Column(name = "content_type")
	private String contentType;	
	@Transient
	private boolean novaFoto;
	@Transient
	private String urlFoto;
	@Transient
	private String urlThumbnailFoto;
	
	@Column
	private Boolean educacaoEspecial = true;
	
	@NotNull(message = "Informe a data de início")
	@Column(name="data_inicial")
	private LocalDate dataInicial;
	@NotNull(message = "Informe a data final")
	@Column(name="data_final")
	private LocalDate dataFinal;

	
	@PrePersist
	@PreUpdate
	private void toUpperCase() {
		this.nome = this.nome.toUpperCase();		
	}
	public boolean isNova() {
		return this.codigo == null;
	}	
	public boolean isNovaFoto() {
		return novaFoto;
	}
	public String getFotoOuMock() {
		return !StringUtils.isEmpty(foto) ? foto : "cerveja-mock.png";
	}
	public String semanaFormadata() {
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY"); 
	      
		 return "De "+formatter.format(dataInicial) + " a " + formatter.format(dataFinal);
	}
	
}
