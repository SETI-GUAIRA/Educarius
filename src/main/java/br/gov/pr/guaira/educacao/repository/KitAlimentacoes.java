package br.gov.pr.guaira.educacao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import br.gov.pr.guaira.educacao.model.KitAlimentacao;


import br.gov.pr.guaira.educacao.repository.helper.KitAlimentacoesQueries;

public interface KitAlimentacoes extends JpaRepository<KitAlimentacao, Long>, KitAlimentacoesQueries{

	Optional<KitAlimentacao> findByCpf(String cpf);

}
