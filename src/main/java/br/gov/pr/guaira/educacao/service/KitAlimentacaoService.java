package br.gov.pr.guaira.educacao.service;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;



import br.gov.pr.guaira.educacao.model.KitAlimentacao;
import br.gov.pr.guaira.educacao.model.Pedido;
import br.gov.pr.guaira.educacao.model.Pedido_Item;
import br.gov.pr.guaira.educacao.repository.KitAlimentacoes;
import br.gov.pr.guaira.educacao.repository.Pedidos_Item;
import br.gov.pr.guaira.educacao.exception.ImpossivelExcluirEntidadeException;
import br.gov.pr.guaira.educacao.exception.KitAlimentacaoJaExistenteException;
import br.gov.pr.guaira.educacao.exception.PedidoJaCadastradoException;




@Service
public class KitAlimentacaoService {

	@Autowired
	private KitAlimentacoes kitAlimentacoes;
	@Autowired
	private Pedidos_Item pedidos_Item;
	@Autowired
	private PedidoService pedidoService;
	
	public void salvar(KitAlimentacao kitAlimentacao) {
		//Optional<KitAlimentacao> KitAlimentacaoOptional = this.kitAlimentacoes.findByCpf(kitAlimentacao.getCpf());	
//		if(!this.kitAlimentacoes.buscaKitAlimentacaoCadastrada(kitAlimentacao).isEmpty() && kitAlimentacao.isNova()){
//				throw new KitAlimentacaoJaExistenteException("Já existe uma cadastro !");
//		}
//		if(participanteOptional.isPresent() && kitAlimentacao.getCodigo() == null) {
//			throw new CpfExistenteException("O CPF já esta em uso!");
//		}
		Optional<KitAlimentacao> KitAlimentacaoOptional = this.kitAlimentacoes.findByNomeAluno(kitAlimentacao.getNomeAluno().toUpperCase());
		if(KitAlimentacaoOptional.isPresent() && kitAlimentacao.getCodigo() == null) {
				throw new KitAlimentacaoJaExistenteException("Esse nome já esta em uso!");
	    }
		kitAlimentacao.setData_cadastro(LocalDate.now());
	//	kitAlimentacao.setAtivo(true);
		this.kitAlimentacoes.saveAndFlush(kitAlimentacao);
		
		//criar_pedido_kit_alimentacao(kitAlimentacao);
		
	}
//	private void criar_pedido_kit_alimentacao(KitAlimentacao kitAlimentacao) {
//		PedidoKitAlimentacao pedidoKitAlimentacao = new PedidoKitAlimentacao();
//		pedidoKitAlimentacao.setDataCadastro(LocalDate.now());	
//		pedidoKitAlimentacao.setRecebeu(false);	
//		pedidoKitAlimentacao.setKitAlimentacao(kitAlimentacao);
//		
//		this.pedidoKitAlimentacaoService.salvar(pedidoKitAlimentacao);
//	}
	
	@Transactional
	public void excluir(KitAlimentacao kitAlimentacao){
		Optional<Pedido_Item> pedidoOptional = this.pedidos_Item.VerificaJaexisteAlunoItemPedido(kitAlimentacao.getCodigo());
		if(pedidoOptional.isPresent()) {
			throw new PedidoJaCadastradoException("Aluno vinculado ao pedido de kit, não pode ser excluído!");
		}				
				try {
					this.kitAlimentacoes.delete(kitAlimentacao);
					this.kitAlimentacoes.flush();
				}catch (ConstraintViolationException  e) {	
					 e.printStackTrace();
					throw new ImpossivelExcluirEntidadeException("Aluno vinculado ao pedido de kit, não pode ser excluído!");
				}

	}
	@Transactional
	public void alterarStatus(Long[] codigos, StatusKitAlimentacao status) {
		status.executar(codigos, kitAlimentacoes);		
	}
//	
//	private void configuraUrlVideos(Aula aula) {
//		String url1 = aula.getUrlVideoAula1();
//		String url2 = aula.getUrlVideoAula2();
//		
//		if(!StringUtils.isEmpty(url1)) {
//			aula.setUrlVideoAula1(url1.substring(url1.indexOf("?v=")+3));
//		}
//		
//		if(!StringUtils.isEmpty(url2)) {
//			aula.setUrlVideoAula2(url2.substring(url2.indexOf("?v=")+3));
//		}
//	}
}
