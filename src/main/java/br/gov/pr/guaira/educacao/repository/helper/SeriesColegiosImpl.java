package br.gov.pr.guaira.educacao.repository.helper;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.gov.pr.guaira.educacao.filter.SerieColegioFilter;
import br.gov.pr.guaira.educacao.model.SerieColegio;
import br.gov.pr.guaira.educacao.repository.paginacao.PaginacaoUtil;

public class SeriesColegiosImpl implements SeriesColegiosQueries{

	@PersistenceContext
	private EntityManager manager;
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<SerieColegio> filtrar(SerieColegioFilter filtro, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<SerieColegio> query = builder.createQuery(SerieColegio.class);
		Root<SerieColegio> serieEntity = query.from(SerieColegio.class);
		Predicate[] filtros = adicionarFiltro(filtro, serieEntity);

		query.select(serieEntity).where(filtros);
		
		TypedQuery<SerieColegio> typedQuery =  (TypedQuery<SerieColegio>) paginacaoUtil.prepararOrdem(query, serieEntity, pageable);
		typedQuery = (TypedQuery<SerieColegio>) paginacaoUtil.prepararIntervalo(typedQuery, pageable);
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, total(filtro));
	}
	
	private Long total(SerieColegioFilter filtro) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		Root<SerieColegio> serieEntity = query.from(SerieColegio.class);
		
		query.select(criteriaBuilder.count(serieEntity));
		query.where(adicionarFiltro(filtro, serieEntity));
		
		return manager.createQuery(query).getSingleResult();
	}
	
	private Predicate[] adicionarFiltro(SerieColegioFilter filtro, Root<SerieColegio> serieEntity) {
		List<Predicate> predicateList = new ArrayList<>();
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		if (filtro != null) {
			
			if (!StringUtils.isEmpty(filtro.getNome())) {
				predicateList.add(builder.like(serieEntity.get("nome"), "%"+filtro.getNome().toUpperCase()+"%"));
			}
		}
		Predicate[] predArray = new Predicate[predicateList.size()];
		return predicateList.toArray(predArray);
	}

}
