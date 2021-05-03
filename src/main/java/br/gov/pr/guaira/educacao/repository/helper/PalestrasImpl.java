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
import org.springframework.util.StringUtils;

import br.gov.pr.guaira.educacao.filter.PalestraFilter;
import br.gov.pr.guaira.educacao.model.Palestra;
import br.gov.pr.guaira.educacao.repository.paginacao.PaginacaoUtil;

public class PalestrasImpl implements PalestrasQueries{

	@PersistenceContext
	private EntityManager manager;
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Palestra> filtrar(PalestraFilter filtro, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Palestra> query = builder.createQuery(Palestra.class);
		Root<Palestra> palestraEntity = query.from(Palestra.class);
		palestraEntity.fetch("evento", JoinType.INNER);
		
		
		Predicate[] filtros = adicionarFiltro(filtro, palestraEntity);

		query.select(palestraEntity).where(filtros);
		
		TypedQuery<Palestra> typedQuery =  (TypedQuery<Palestra>) paginacaoUtil.prepararOrdem(query, palestraEntity, pageable);
		typedQuery = (TypedQuery<Palestra>) paginacaoUtil.prepararIntervalo(typedQuery, pageable);
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, total(filtro));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Palestra> buscaPalestraCadastrada(Palestra palestra) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Palestra> query = builder.createQuery(Palestra.class);
		Root<Palestra> palestraEntity = query.from(Palestra.class);
		palestraEntity.fetch("evento", JoinType.INNER);
		
		
		PalestraFilter palestraFilter = new PalestraFilter();
		palestraFilter.setEvento(palestra.getEvento());		
		
		Predicate[] filtros = adicionarFiltro(palestraFilter, palestraEntity);

		query.select(palestraEntity).where(filtros);
		
		TypedQuery<Palestra> typedQuery = manager.createQuery(query);
		return typedQuery.getResultList();
	}
	
	private Long total(PalestraFilter filtro) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		Root<Palestra> palestraEntity = query.from(Palestra.class);
		
		query.select(criteriaBuilder.count(palestraEntity));
		query.where(adicionarFiltro(filtro, palestraEntity));
		
		return manager.createQuery(query).getSingleResult();
	}
	
	private Predicate[] adicionarFiltro(PalestraFilter filtro, Root<Palestra> palestraEntity) {
		List<Predicate> predicateList = new ArrayList<>();
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		if (filtro != null) {
			
			if (filtro.getEvento() != null){
				predicateList.add(builder.equal(palestraEntity.get("evento"), filtro.getEvento()));
			}	
			if (!StringUtils.isEmpty(filtro.getNomePalestrante())) {
				predicateList.add(builder.like(palestraEntity.get("nomePalestrante"), "%"+filtro.getNomePalestrante().toUpperCase()+"%"));
			}
		}
		Predicate[] predArray = new Predicate[predicateList.size()];
		return predicateList.toArray(predArray);
	}
}