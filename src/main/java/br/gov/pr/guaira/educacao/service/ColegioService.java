package br.gov.pr.guaira.educacao.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.pr.guaira.educacao.exception.ImpossivelExcluirEntidadeException;
import br.gov.pr.guaira.educacao.exception.SerieJaCadastradaException;
import br.gov.pr.guaira.educacao.model.Colegio;

import br.gov.pr.guaira.educacao.repository.Colegios;
;


@Service
public class ColegioService {

	@Autowired
	private Colegios colegios;
	
	
	public void salvar(Colegio colegio) {
		
		Optional<Colegio> colegioOptional = this.colegios.findByNomeIgnoreCase(colegio.getNome());
		
		if(colegioOptional.isPresent() && colegio.isNova()) {
			throw new SerieJaCadastradaException("colegio já está cadastrada!");
		}
		this.colegios.saveAndFlush(colegio);
	}
	
	@Transactional
	public void excluir(Colegio colegio) {
		
		try {
			this.colegios.delete(colegio);
			this.colegios.flush();
		}catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Não foi possível excluir esta série!");
		}
		
	}

	public List<Colegio> buscarTodos() {
	    return colegios.findAllByOrderByNomeAsc();
	  }
	
}
