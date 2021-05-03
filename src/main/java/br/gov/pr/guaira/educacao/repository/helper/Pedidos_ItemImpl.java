package br.gov.pr.guaira.educacao.repository.helper;




import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.gov.pr.guaira.educacao.model.Pedido;
import br.gov.pr.guaira.educacao.model.Pedido_Item;
import br.gov.pr.guaira.educacao.model.Semana;






public class Pedidos_ItemImpl implements Pedidos_ItemQueries {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Object totalPedidoMes(Long codigoPedido) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		Root<Pedido_Item> pedidoItemEntity = query.from(Pedido_Item.class);
		//pedidoItemEntity.fetch(attribute)
		
		query.select(criteriaBuilder.count(pedidoItemEntity));
		//query.where(adicionarFiltro(filtro, semanaEntity));
		
		return manager.createQuery(query).getSingleResult();
		//return codigoPedido;
	}

	

	@Override
	public String GetMes() {
		// TODO Auto-generated method stub
		String mesf ="";
		int mes = MonthDay.now().getMonthValue();
		switch(mes) {
		 case 1:  mesf = "Janeiro"; break;
		 case 2:  mesf = "Fevereiro"; break;
		 case 3:  mesf = "Março"; break;
		 case 4:  mesf = "Abril"; break;
		 case 5:  mesf = "Maio"; break;
		 case 6:  mesf = "Junho"; break;
		 case 7:  mesf = "Julho"; break;
		 case 8:  mesf = "Agosto"; break;
		 case 9:  mesf = "Setembro"; break;
		 case 10: mesf = "Outubro"; break;
		 case 11: mesf = "Novembro"; break;
		 case 12: mesf = "Dezembro"; break;
		}
		
		//
		return mesf;
	}



	@Override
	public Optional<Pedido_Item> VerificaJaexisteAlunoItemPedido(Long codigo) {
		return manager
				.createQuery("from Pedido_Item where codigo_kit_alimentacao = :codigo", Pedido_Item.class)
					.setParameter("codigo", codigo).getResultList().stream().findFirst();
	}



//	@Override
//	public void deleteItemBycodigoPedido(Long codPedido) {
//		 manager
//				.createQuery("DELETE pedido_item where codigo_pedido = :codPedido", Pedido.class)
//					.setParameter("codPedido", codPedido).getResultList().stream().findFirst();
//		
//	}
//	@Override
//	public Optional<Pedido> VerificaJaexistePedido(int monthValue) {		
//		return manager
//				.createQuery("from Pedido where MONTH(dataPedido) = :monthValue", Pedido.class)
//					.setParameter("monthValue", monthValue).getResultList().stream().findFirst();
//	}

//	@SuppressWarnings("unchecked")
//	@Override
//	@Transactional(readOnly = true)
//	public Page<Pedido> filtrar(PedidoFilter filtro, Pageable pageable) {
//		CriteriaBuilder builder = manager.getCriteriaBuilder();
//		CriteriaQuery<Pedido> query = builder.createQuery(Pedido.class);
//		Root<Pedido> pedidoEntity = query.from(Pedido.class);
//		Predicate[] filtros = adicionarFiltro(filtro, pedidoEntity);
//
//		query.select(pedidoEntity).where(filtros);
//		
//		TypedQuery<Pedido> typedQuery =  (TypedQuery<Pedido>) paginacaoUtil.prepararOrdem(query, pedidoEntity, pageable);
//		typedQuery = (TypedQuery<Pedido>) paginacaoUtil.prepararIntervalo(typedQuery, pageable);
//		
//		return new PageImpl<>(typedQuery.getResultList(), pageable, total(filtro));
//	}
//	
//	private Long total(PedidoFilter filtro) {
//		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
//		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
//		Root<Pedido> pedidoEntity = query.from(Pedido.class);
//		
//		query.select(criteriaBuilder.count(pedidoEntity));
//		query.where(adicionarFiltro(filtro, pedidoEntity));
//		
//		return manager.createQuery(query).getSingleResult();
//	}
//	
//	private Predicate[] adicionarFiltro(PedidoFilter filtro, Root<Pedido> pedidoEntity) {
//		List<Predicate> predicateList = new ArrayList<>();
//		CriteriaBuilder builder = manager.getCriteriaBuilder();
//
//		if (filtro != null) {
//			
//
//		}
//		Predicate[] predArray = new Predicate[predicateList.size()];
//		return predicateList.toArray(predArray);
//	}



}
