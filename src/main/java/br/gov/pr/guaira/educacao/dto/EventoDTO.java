package br.gov.pr.guaira.educacao.dto;

import java.time.LocalDate;

import org.springframework.util.StringUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode
public class EventoDTO {
	private Long codigo;	
	private String nome;	
	private String foto;
	private String urlThumbnailFoto;
	private LocalDate dataInicial;
	private LocalDate dataFinal;
	
	public EventoDTO(Long codigo, String nome, String foto, LocalDate dataInicial,LocalDate dataFinal ) {
		this.codigo = codigo;		
		this.nome = nome;
		this.foto = StringUtils.isEmpty(foto) ? "cerveja-mock.png" : foto;
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;		
	}	
}
