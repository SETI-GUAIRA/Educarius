package br.gov.pr.guaira.educacao.repository.helper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.pr.guaira.educacao.filter.KitAlimentacaoFilter;
import br.gov.pr.guaira.educacao.model.Colegio;
import br.gov.pr.guaira.educacao.model.KitAlimentacao;

public interface KitAlimentacoesQueries {
	public Page<KitAlimentacao> filtrar(KitAlimentacaoFilter kitAlimentacaoFilter, Pageable pageable);	
	public List<KitAlimentacao> buscaKitAlimentacaoCadastrada(KitAlimentacao kitAlimentacao);
	
}
