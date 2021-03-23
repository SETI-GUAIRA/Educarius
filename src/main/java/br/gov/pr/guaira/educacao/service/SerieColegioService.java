package br.gov.pr.guaira.educacao.service;

import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.pr.guaira.educacao.exception.ImpossivelExcluirEntidadeException;
import br.gov.pr.guaira.educacao.exception.SerieColegioJaCadastradaException;

import br.gov.pr.guaira.educacao.model.SerieColegio;

import br.gov.pr.guaira.educacao.repository.SeriesColegios;

@Service
public class SerieColegioService {

	@Autowired
	private SeriesColegios seriesColegios;
	
	
	public void salvar(SerieColegio serieColegio) {
		
		Optional<SerieColegio> serieOptional = this.seriesColegios.findByNomeIgnoreCase(serieColegio.getNome());
		
		if(serieOptional.isPresent() && serieColegio.isNova()) {
			throw new SerieColegioJaCadastradaException("Esta série já está cadastrada!");
		}
		this.seriesColegios.saveAndFlush(serieColegio);
	}
	
	@Transactional
	public void excluir(SerieColegio serieColegio) {
		
		try {
			this.seriesColegios.delete(serieColegio);
			this.seriesColegios.flush();
		}catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Não foi possível excluir esta série!");
		}
		
	}
}
