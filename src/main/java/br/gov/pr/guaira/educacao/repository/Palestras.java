package br.gov.pr.guaira.educacao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pr.guaira.educacao.model.Palestra;
import br.gov.pr.guaira.educacao.repository.helper.PalestrasQueries;

public interface Palestras extends JpaRepository<Palestra, Long>, PalestrasQueries{

	Object findAllByEvento(Long codigoEvento);

	//Optional<Palestra> findByNomePalestra(String nomePalestra);
}
