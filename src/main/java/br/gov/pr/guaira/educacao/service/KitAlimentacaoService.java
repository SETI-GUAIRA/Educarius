package br.gov.pr.guaira.educacao.service;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import br.gov.pr.guaira.educacao.model.KitAlimentacao;
import br.gov.pr.guaira.educacao.model.PedidoKitAlimentacao;

import br.gov.pr.guaira.educacao.repository.KitAlimentacoes;
import br.gov.pr.guaira.educacao.exception.CpfExistenteException;
import br.gov.pr.guaira.educacao.exception.ImpossivelExcluirEntidadeException;
import br.gov.pr.guaira.educacao.exception.KitAlimentacaoJaExistenteException;



@Service
public class KitAlimentacaoService {

	@Autowired
	private KitAlimentacoes kitAlimentacoes;
	@Autowired
	private PedidoKitAlimentacaoService pedidoKitAlimentacaoService;
	
	public void salvar(KitAlimentacao kitAlimentacao) {
		//Optional<KitAlimentacao> KitAlimentacaoOptional = this.kitAlimentacoes.findByCpf(kitAlimentacao.getCpf());	
//		if(!this.kitAlimentacoes.buscaKitAlimentacaoCadastrada(kitAlimentacao).isEmpty() && kitAlimentacao.isNova()){
//				throw new KitAlimentacaoJaExistenteException("Já existe uma cadastro !");
//		}
//		if(participanteOptional.isPresent() && kitAlimentacao.getCodigo() == null) {
//			throw new CpfExistenteException("O CPF já esta em uso!");
//		}
		kitAlimentacao.setData_cadastro(LocalDate.now());
	//	kitAlimentacao.setAtivo(true);
		this.kitAlimentacoes.saveAndFlush(kitAlimentacao);
		
		criar_pedido_kit_alimentacao(kitAlimentacao);
		
	}
	private void criar_pedido_kit_alimentacao(KitAlimentacao kitAlimentacao) {
		PedidoKitAlimentacao pedidoKitAlimentacao = new PedidoKitAlimentacao();
		pedidoKitAlimentacao.setDataCadastro(LocalDate.now());	
		pedidoKitAlimentacao.setRecebeu(false);	
		pedidoKitAlimentacao.setKitAlimentacao(kitAlimentacao);
		
		this.pedidoKitAlimentacaoService.salvar(pedidoKitAlimentacao);
	}
	
	@Transactional
	public void excluir(KitAlimentacao kitAlimentacao) {
		
		try {
			this.kitAlimentacoes.delete(kitAlimentacao);
			this.kitAlimentacoes.flush();
		}catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Esta aula não pode ser excluída!");
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
