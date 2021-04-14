package br.gov.pr.guaira.educacao.repository.helper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.gov.pr.guaira.educacao.filter.EventoFilter;
import br.gov.pr.guaira.educacao.model.Evento;
import br.gov.pr.guaira.educacao.repository.paginacao.PaginacaoUtil;

public class EventosImpl implements EventosQueries {

	@PersistenceContext
	private EntityManager manager;
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Evento> filtrar(EventoFilter filtro, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Evento> query = builder.createQuery(Evento.class);
		Root<Evento> eventoEntity = query.from(Evento.class);
		Predicate[] filtros = adicionarFiltro(filtro, eventoEntity);

		query.select(eventoEntity).where(filtros);
		
		TypedQuery<Evento> typedQuery =  (TypedQuery<Evento>) paginacaoUtil.prepararOrdem(query, eventoEntity, pageable);
		typedQuery = (TypedQuery<Evento>) paginacaoUtil.prepararIntervalo(typedQuery, pageable);
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, total(filtro));
	}
	
	private Long total(EventoFilter filtro) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		Root<Evento> eventoEntity = query.from(Evento.class);
		
		query.select(criteriaBuilder.count(eventoEntity));
		query.where(adicionarFiltro(filtro, eventoEntity));
		
		return manager.createQuery(query).getSingleResult();
	}
	
	private Predicate[] adicionarFiltro(EventoFilter filtro, Root<Evento> eventoEntity) {
		List<Predicate> predicateList = new ArrayList<>();
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		if (filtro != null) {
			
			if (filtro.getDataInicial() != null) {
				predicateList.add(builder.lessThanOrEqualTo(eventoEntity.get("dataInicial"), filtro.getDataInicial()));  
			}
			
			if (filtro.getDataFinal() != null) {
				predicateList.add(builder.greaterThanOrEqualTo(eventoEntity.get("dataFinal"), filtro.getDataFinal()));  
			}
			if (!StringUtils.isEmpty(filtro.getNome())) {
				predicateList.add(builder.like(eventoEntity.get("nome"), "%"+filtro.getNome().toUpperCase()+"%"));
			}
		}
		Predicate[] predArray = new Predicate[predicateList.size()];
		return predicateList.toArray(predArray);
	}

//	@Override
//	public List<Evento> buscaSemanasSite(String usuario, Serie serie) {
//		
//		CriteriaBuilder builder = manager.getCriteriaBuilder();
//		CriteriaQuery<Semana> query = builder.createQuery(Semana.class);
//		Root<Semana> semanaEntity = query.from(Semana.class);
//		List<Predicate> predicateList = new ArrayList<>();
//		
//		LocalDate dataAtual = LocalDate.now();
//		
//		if(usuario.equals("anonymousUser")) {
//			predicateList.add(builder.lessThanOrEqualTo(semanaEntity.get("dataInicial"), dataAtual));
//		}
//		
//		Subquery<Aula> subquery = query.subquery(Aula.class);	
//		Root<Aula> aulaRoot = subquery.from(Aula.class);
//		
//		List<Predicate> predicateListSubQuery = new ArrayList<>();
//		predicateListSubQuery.add(builder.equal(aulaRoot.get("semana"), semanaEntity));
//		predicateListSubQuery.add(builder.equal(aulaRoot.get("serie"), serie));
//		subquery.select(aulaRoot).where(predicateListSubQuery.toArray(new Predicate[0]));
//		
//		predicateList.add(builder.exists(subquery));
//		query.select(semanaEntity);
//		query.where(predicateList.toArray(new Predicate[0]));
//		query.orderBy(builder.desc(semanaEntity.get("dataInicial")));
//		
//		TypedQuery<Semana> typeQuery = manager.createQuery(query);
//		return typeQuery.setMaxResults(6).getResultList();
//	}
}
