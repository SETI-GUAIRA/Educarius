package br.gov.pr.guaira.educacao.repository.helper;



import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.gov.pr.guaira.educacao.filter.PedidoFilter;
import br.gov.pr.guaira.educacao.filter.SerieFilter;
import br.gov.pr.guaira.educacao.model.KitAlimentacao;
import br.gov.pr.guaira.educacao.model.Pedido;
import br.gov.pr.guaira.educacao.model.Usuario;
import br.gov.pr.guaira.educacao.repository.paginacao.PaginacaoUtil;





public class PedidosImpl implements PedidosQueries {
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Pedido> filtrar(PedidoFilter filtro, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pedido> query = builder.createQuery(Pedido.class);
		Root<Pedido> pedidoEntity = query.from(Pedido.class);
		Predicate[] filtros = adicionarFiltro(filtro, pedidoEntity);

		query.select(pedidoEntity).where(filtros);
		
		TypedQuery<Pedido> typedQuery =  (TypedQuery<Pedido>) paginacaoUtil.prepararOrdem(query, pedidoEntity, pageable);
		typedQuery = (TypedQuery<Pedido>) paginacaoUtil.prepararIntervalo(typedQuery, pageable);
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, total(filtro));
	}
	
	private Long total(PedidoFilter filtro) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		Root<Pedido> pedidoEntity = query.from(Pedido.class);
		
		query.select(criteriaBuilder.count(pedidoEntity));
		query.where(adicionarFiltro(filtro, pedidoEntity));
		
		return manager.createQuery(query).getSingleResult();
	}
	
	private Predicate[] adicionarFiltro(PedidoFilter filtro, Root<Pedido> pedidoEntity) {
		List<Predicate> predicateList = new ArrayList<>();
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		if (filtro != null) {
			
//			if (!StringUtils.isEmpty(filtro.getNome())) {
//				predicateList.add(builder.like(serieEntity.get("nome"), "%"+filtro.getNome().toUpperCase()+"%"));
//			}
		}
		Predicate[] predArray = new Predicate[predicateList.size()];
		return predicateList.toArray(predArray);
	}

	@Override
	public Optional<Pedido> VerificaJaexistePedido(int monthValue) {		
		return manager
				.createQuery("from Pedido where MONTH(dataPedido) = :monthValue", Pedido.class)
					.setParameter("monthValue", monthValue).getResultList().stream().findFirst();
	}

}
