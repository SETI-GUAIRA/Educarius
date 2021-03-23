package br.gov.pr.guaira.educacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pr.guaira.educacao.model.PedidoKitAlimentacao;

import br.gov.pr.guaira.educacao.repository.helper.PedidoKitAlimentacoesQueries;


public interface PedidoKitAlimentacoes extends JpaRepository<PedidoKitAlimentacao, Long>, PedidoKitAlimentacoesQueries {
	//public List<PedidoKitAlimentacao> findByOrderByDataCadastroDesc();
}
