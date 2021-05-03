package br.gov.pr.guaira.educacao.model;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.util.StringUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode
@Entity
@Table(name="palestra")
@DynamicUpdate
public class Palestra implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;	
	
	
	@NotBlank(message = "Nome da palestra é obrigatório!")
	private String nomePalestra;	
	
	@NotBlank(message = "Nome do palestrate é obrigatório!")
	private String nomePalestrante;	
	
	@Lob
	@NotBlank(message = "Informe descrição do Palestrate")
	@Column(name="descricao")
	private String descricao;	
	@Lob
	@Column(name="descricao_html")
	private String descricaoHtml;	
	
	@NotBlank(message="Informe a URL da vídeo aula")
	@Column(name="url_video_aula1")
	private String urlVideoAula1;
	
	@Column(name="url_video_aula2")
	private String urlVideoAula2;	
	
	@NotNull(message = "Informe a data de início")
	@Column(name="data_inicial")
	@DateTimeFormat(iso = ISO.DATE_TIME, pattern = "")
	private LocalDateTime dataInicial;		
	
	@Column(name = "data_cadastro")
	private LocalDateTime dataCadastro;	
	
		
	private String fotoNome;
	private String fotoType;
	
//	@Column(name = "foto", columnDefinition = "VARCHAR(10000)")
//	private String foto;	
	
	@Lob
	private byte[] fotoPatestrante;
	public Palestra() {}
	public Palestra(String fotoNome, String fotoType, byte[] fotoPatestrante) {
		super();
		this.fotoNome = fotoNome;
		this.fotoType = fotoType;
		this.fotoPatestrante = fotoPatestrante;
	}
	
	
	
	@NotNull(message = "Informe o nome do evento")
	@ManyToOne
	@JoinColumn(name="evento", referencedColumnName = "codigo")
	private Evento evento;
	
	@PrePersist
	@PreUpdate
	private void toUpperCase() {
		this.nomePalestra = this.nomePalestra.toUpperCase();		
	}
	
	public boolean isNova() {
		return this.codigo == null;
	}
	
	public boolean temVideo2() {
		return !StringUtils.isEmpty(this.urlVideoAula2);
	}
	public boolean liberouVideo() {
		if(LocalDateTime.now().isAfter(dataInicial)) {
			return true;
		}
		return false;
		
	}
	public String dataFormadata() {
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY"); 
	      
		 return formatter.format(dataInicial);
	}
	public String horaFormadata() {
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss"); 	      
		 return formatter.format(dataInicial);
	}
//    public ByteArrayResource montaFoto(byte[] fotoPatestrante) {
//    	ByteArrayResource foto = new ByteArrayResource(this.fotoPatestrante);
//		return foto;
//    	
//    }
}
