package br.gov.pr.guaira.educacao.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import br.gov.pr.guaira.educacao.model.KitAlimentacao;
import br.gov.pr.guaira.educacao.model.PedidoKitAlimentacao;

import br.gov.pr.guaira.educacao.repository.KitAlimentacoes;
import br.gov.pr.guaira.educacao.exception.CpfExistenteException;



@Service
public class KitAlimentacaoService {

	@Autowired
	private KitAlimentacoes kitAlimentacoes;
	@Autowired
	private PedidoKitAlimentacaoService pedidoKitAlimentacaoService;
	
	public void salvar(KitAlimentacao kitAlimentacao) {
		Optional<KitAlimentacao> participanteOptional = this.kitAlimentacoes.findByCpf(kitAlimentacao.getCpf());	
//		if(!this.kitAlimentacoes.buscaKitAlimentacaoCadastrada(kitAlimentacao).isEmpty() && kitAlimentacao.isNova()){
//				throw new KitAlimentacaoJaExistenteException("Já existe uma cadastro com estes dados no mês selecionado!");
//		}
		if(participanteOptional.isPresent() && kitAlimentacao.getCodigo() == null) {
			throw new CpfExistenteException("O CPF já esta em uso!");
		}
		kitAlimentacao.setData_cadastro(LocalDate.now());	
		kitAlimentacao.setAtivo(true);
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
	
//	@Transactional
//	public void excluir(Aula aula) {
//		
//		try {
//			this.aulas.delete(aula);
//			this.aulas.flush();
//		}catch (PersistenceException e) {
//			throw new ImpossivelExcluirEntidadeException("Esta aula não pode ser excluída!");
//		}
//	}
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
