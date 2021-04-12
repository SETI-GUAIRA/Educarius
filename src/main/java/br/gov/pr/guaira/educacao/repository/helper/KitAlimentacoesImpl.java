package br.gov.pr.guaira.educacao.repository.helper;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import br.gov.pr.guaira.educacao.filter.KitAlimentacaoFilter;
import br.gov.pr.guaira.educacao.model.KitAlimentacao;
import br.gov.pr.guaira.educacao.model.Usuario;
import br.gov.pr.guaira.educacao.repository.paginacao.PaginacaoUtil;

public class KitAlimentacoesImpl implements KitAlimentacoesQueries{

	@PersistenceContext
	private EntityManager manager;
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<KitAlimentacao> filtrar(KitAlimentacaoFilter filtro, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<KitAlimentacao> query = builder.createQuery(KitAlimentacao.class);
		Root<KitAlimentacao> kitAlimentacaoEntity = query.from(KitAlimentacao.class);
		kitAlimentacaoEntity.fetch("colegio", JoinType.INNER);
		kitAlimentacaoEntity.fetch("serieColegio", JoinType.INNER);	
		
	
		Predicate[] filtros = adicionarFiltro(filtro, kitAlimentacaoEntity);

		query.select(kitAlimentacaoEntity).where(filtros);
		
		TypedQuery<KitAlimentacao> typedQuery =  (TypedQuery<KitAlimentacao>) paginacaoUtil.prepararOrdem(query, kitAlimentacaoEntity, pageable);
		typedQuery = (TypedQuery<KitAlimentacao>) paginacaoUtil.prepararIntervalo(typedQuery, pageable);
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, total(filtro));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<KitAlimentacao> buscaKitAlimentacaoCadastrada(KitAlimentacao kitAlimentacao) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<KitAlimentacao> query = builder.createQuery(KitAlimentacao.class);
		Root<KitAlimentacao> kitAlimentacaoEntity = query.from(KitAlimentacao.class);

		kitAlimentacaoEntity.fetch("colegio", JoinType.INNER);
		kitAlimentacaoEntity.fetch("serieColegio", JoinType.INNER);		
		
		
		KitAlimentacaoFilter kitAlimentacaoFilter = new KitAlimentacaoFilter();
		kitAlimentacaoFilter.setColegio(kitAlimentacao.getColegio());
		kitAlimentacaoFilter.setSerieColegio(kitAlimentacao.getSerieColegio());		
	
		
		Predicate[] filtros = adicionarFiltro(kitAlimentacaoFilter, kitAlimentacaoEntity);

		query.select(kitAlimentacaoEntity).where(filtros);
		
		TypedQuery<KitAlimentacao> typedQuery = manager.createQuery(query);
		return typedQuery.getResultList();
	}
	
	private Long total(KitAlimentacaoFilter filtro) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		Root<KitAlimentacao> kitAlimentacaoEntity = query.from(KitAlimentacao.class);
		
		query.select(criteriaBuilder.count(kitAlimentacaoEntity));
		query.where(adicionarFiltro(filtro, kitAlimentacaoEntity));
		
		return manager.createQuery(query).getSingleResult();
	}
	
	private Predicate[] adicionarFiltro(KitAlimentacaoFilter filtro, Root<KitAlimentacao> kitAlimentacaoEntity) {
		List<Predicate> predicateList = new ArrayList<>();
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		if (filtro != null) {
			
			if (filtro.getColegio() != null){
				predicateList.add(builder.equal(kitAlimentacaoEntity.get("colegio"), filtro.getColegio()));
			}			
			if (filtro.getSerieColegio() != null){
				predicateList.add(builder.equal(kitAlimentacaoEntity.get("serieColegio"), filtro.getSerieColegio()));
			}
			if (filtro.getTurma() != null){
				predicateList.add(builder.equal(kitAlimentacaoEntity.get("turma"), filtro.getTurma()));
			}
			if (filtro.getTurno() != null){
				predicateList.add(builder.equal(kitAlimentacaoEntity.get("turno"), filtro.getTurno()));
			}
			if (filtro.getNomeAluno() != null){
				predicateList.add(builder.like(kitAlimentacaoEntity.get("nomeAluno"), "%"+filtro.getNomeAluno().toUpperCase()+"%"));
			}
			if (filtro.getPedido() != null){
				predicateList.add(builder.equal(kitAlimentacaoEntity.get("pedido"), filtro.getPedido()));
			}
		}
		Predicate[] predArray = new Predicate[predicateList.size()];
		return predicateList.toArray(predArray);
	}

	@Override
	public List<KitAlimentacao> KitAtivo() {
		return manager.createQuery("from KitAlimentacao where ativo = true", KitAlimentacao.class).getResultList();		
	}
}