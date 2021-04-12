package br.gov.pr.guaira.educacao.controller;


import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.gov.pr.guaira.educacao.controller.page.PageWrapper;
import br.gov.pr.guaira.educacao.exception.ColegioJaCadastradaException;
import br.gov.pr.guaira.educacao.exception.ImpossivelExcluirEntidadeException;
import br.gov.pr.guaira.educacao.exception.PedidoJaCadastradoException;
import br.gov.pr.guaira.educacao.filter.KitAlimentacaoFilter;
import br.gov.pr.guaira.educacao.filter.PedidoFilter;
import br.gov.pr.guaira.educacao.model.Colegio;
import br.gov.pr.guaira.educacao.model.KitAlimentacao;
import br.gov.pr.guaira.educacao.model.Pedido;
import br.gov.pr.guaira.educacao.model.PedidoItemId;
import br.gov.pr.guaira.educacao.repository.KitAlimentacoes;
import br.gov.pr.guaira.educacao.repository.Pedidos;
import br.gov.pr.guaira.educacao.repository.Pedidos_Item;
import br.gov.pr.guaira.educacao.service.KitAlimentacaoService;
import br.gov.pr.guaira.educacao.service.PedidoService;


@Controller
@RequestMapping("/pedidos")
public class PedidoController implements Serializable{

	private static final long serialVersionUID = 1L;	
	
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private KitAlimentacoes kitAlimentacoes;
	@Autowired
	private Pedidos pedidos;
	
	private PedidoItemId pedidoItemId;
	@Autowired
	private Pedidos_Item pedidos_Intem;

	@RequestMapping("/novo")
	public ModelAndView nova(Pedido pedido) {
		ModelAndView mv = new ModelAndView("pedido/PedidoKitAlimentacao");		
		return mv;
	}		
	@GetMapping("/gravaPedido")
	public ResponseEntity<String> gravarPedido(Pedido pedido,BindingResult result) {	
		pedido.setKitsAlimentacao(kitAlimentacoes.KitAtivo());
		
		try {
			pedidoService.salvar(pedido);
		}catch (PedidoJaCadastradoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());	
		}		
		return ResponseEntity.ok().build();		
	}	
	@GetMapping
	public ModelAndView pesquisar(PedidoFilter pedidoFilter, BindingResult result, @PageableDefault(size=10) Pageable pageable, 
			 HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("pedido/PedidoKitAlimentacao");
		
				
		
		PageWrapper<Pedido> paginaWrapper = new PageWrapper<>(pedidos.filtrar(pedidoFilter, pageable), httpServletRequest);
		mv.addObject("proximoMEs",pedidos_Intem.GetMes());
		mv.addObject("totalPedidos",this.pedidos_Intem.totalPedidoMes(null));
		//mv.addObject("totalPedidos",pedidos_Intem.totalPedidoMes(pedidoFilter.getDataPedido()));
		//mv.addObject("totalPedidos",this.pedidos_Intem.findByidIgnoreCase(pedido));
		mv.addObject("pagina", paginaWrapper);		
		return mv;
	}
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Pedido pedido){
		
		try {
			this.pedidoService.excluir(pedido);
		}catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
}
