package br.gov.pr.guaira.educacao.repository.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.gov.pr.guaira.educacao.filter.UsuarioFilter;
import br.gov.pr.guaira.educacao.model.Grupo;
import br.gov.pr.guaira.educacao.model.Usuario;
import br.gov.pr.guaira.educacao.repository.paginacao.PaginacaoUtil;

public class UsuariosImpl implements UsuariosQueries{

	@PersistenceContext
	private EntityManager manager;
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@Override
	public Optional<Usuario> porEmailEAtivo(String email) {
		
		return manager
				.createQuery("from Usuario where lower(email) = lower(:email) and ativo = true", Usuario.class)
					.setParameter("email", email).getResultList().stream().findFirst();
	}

	@Override
	public List<String> permissoes(Usuario usuario) {
		return manager
				.createQuery("Select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u = :usuario",
				String.class).setParameter("usuario", usuario).getResultList();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Usuario buscarComGrupos(Long codigo) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
		Root<Usuario> usuarioEntity = query.from(Usuario.class);
		usuarioEntity.fetch("grupos", JoinType.LEFT);
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (codigo != null) {
			predicates.add(
					builder.equal(usuarioEntity.get("codigo"), codigo));
		}

		query.select(usuarioEntity);
		query.where(predicates.toArray(new Predicate[0]));
		query.orderBy(builder.asc(usuarioEntity.get("codigo")));

		TypedQuery<Usuario> typeQuery = manager.createQuery(query);
		return typeQuery.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> filtrar(UsuarioFilter filtro, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
		Root<Usuario> rootUsuario = query.from(Usuario.class);
		
		Predicate[] predicates = adicionafiltros(filtro, builder, query, rootUsuario);
		
		query.select(rootUsuario).where(predicates);
		
		TypedQuery<Usuario> typedQuery =  (TypedQuery<Usuario>) paginacaoUtil.prepararOrdem(query, rootUsuario, pageable);
		typedQuery = (TypedQuery<Usuario>) paginacaoUtil.prepararIntervalo(typedQuery, pageable);
		return new PageImpl<>(typedQuery.getResultList(), pageable, total(filtro));
	}
	
	private Long total(UsuarioFilter filtro) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		Root<Usuario> usuarioEntity = query.from(Usuario.class);
		
		query.select(criteriaBuilder.count(usuarioEntity));
		
		query.where(adicionafiltros(filtro,criteriaBuilder, query, usuarioEntity));
		
		return manager.createQuery(query).getSingleResult();
	}
	
	private Predicate[] adicionafiltros(UsuarioFilter filtro, CriteriaBuilder builder,
			CriteriaQuery<?> query, Root<Usuario> rootUsuario) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (filtro != null) {
			
			if (!StringUtils.isEmpty(filtro.getNome())) {
				predicates.add(builder.like(rootUsuario.get("nome"), "%"+filtro.getNome().toUpperCase()+"%"));
			}
			
			if (!StringUtils.isEmpty(filtro.getEmail())) {
				predicates.add(builder.like(rootUsuario.get("email"), "%"+filtro.getEmail()+"%"));
			}
			
			if(filtro.getGrupos() != null && !filtro.getGrupos().isEmpty()) {
				
				for(Long codigoGrupo : filtro.getGrupos().stream().mapToLong(Grupo::getCodigo).toArray()) {
				Subquery<Grupo> subquery = query.subquery(Grupo.class);	
				Root<Usuario> subqueryRoot = subquery.correlate(rootUsuario);
				Join<Usuario, Grupo> grupoRoot = subqueryRoot.join("grupos");
				subquery.select(grupoRoot).where(builder.equal(grupoRoot.get("codigo"), codigoGrupo));
				
				predicates.add(builder.exists(subquery));
				}
			}
		}
		return predicates.toArray(new Predicate[0]);
	}
}
