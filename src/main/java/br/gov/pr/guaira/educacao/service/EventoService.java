package br.gov.pr.guaira.educacao.service;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.pr.guaira.educacao.storage.FotoStorage;
import br.gov.pr.guaira.educacao.exception.ImpossivelExcluirEntidadeException;
import br.gov.pr.guaira.educacao.model.Evento;
import br.gov.pr.guaira.educacao.model.Semana;
import br.gov.pr.guaira.educacao.repository.Eventos;
import br.gov.pr.guaira.educacao.repository.Semanas;
import br.gov.pr.guaira.educacao.validation.DataValidacaoException;

@Service
public class EventoService {

	@Autowired
	private Eventos eventos;
	@Autowired
	private FotoStorage fotoStorage;
	
	public void salvar(Evento evento) {
		
		confereDatas(evento);
		this.eventos.saveAndFlush(evento);
	}

	@Transactional
	public void excluir(Evento evento) {		
		try {
			String foto = evento.getFoto();
			this.eventos.delete(evento);
			this.eventos.flush();
			this.fotoStorage.excluir(foto);
		}catch (RuntimeException e) {
			throw new ImpossivelExcluirEntidadeException("Este evento não pode ser excluído!");
		}
	}
	
	private void confereDatas(Evento evento) {
		if(evento.getDataInicial().isBefore(LocalDate.now())) {
			throw new DataValidacaoException("Data inicial é menor que a data atual");
		}
		
		if(evento.getDataInicial().isAfter(evento.getDataFinal())) {
			throw new DataValidacaoException("Data inicial é maior que a data final");
		}
		
		if(evento.getDataFinal().isBefore(LocalDate.now())) {
			throw new DataValidacaoException("Data final incorreta!");
		}
		
		if(evento.getDataFinal().isBefore(evento.getDataInicial())) {
			throw new DataValidacaoException("Data final incorreta");
		}
		
		if(evento.getDataFinal().isEqual(evento.getDataInicial())) {
			throw new DataValidacaoException("Datas iguais!");
		}
	}
}
