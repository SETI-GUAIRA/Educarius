package br.gov.pr.guaira.educacao.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.gov.pr.guaira.educacao.filter.ColegioFilter;
import br.gov.pr.guaira.educacao.filter.KitAlimentacaoFilter;
import br.gov.pr.guaira.educacao.model.Colegio;
import br.gov.pr.guaira.educacao.model.KitAlimentacao;
import br.gov.pr.guaira.educacao.repository.Colegios;
import br.gov.pr.guaira.educacao.repository.KitAlimentacoes;
import br.gov.pr.guaira.educacao.service.ColegioService;
import br.gov.pr.guaira.educacao.service.KitAlimentacaoService;


@Controller
@RequestMapping("/pedidos")
public class PedidoController implements Serializable{

	private static final long serialVersionUID = 1L;
	@Autowired
	private KitAlimentacaoService kitAlimentacaoService;
	@Autowired
	private KitAlimentacoes kitAlimentacoes;

	@RequestMapping("/novo")
	public ModelAndView nova(KitAlimentacao kitAlimentacao) {
		ModelAndView mv = new ModelAndView("pedido/PedidoKitAlimentacao");		
		return mv;
	}
	
	@PostMapping(value = {"/nova", "{\\d+}"})
	public ModelAndView salvar(@Valid KitAlimentacao kitAlimentacao, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return nova(kitAlimentacao);
		}
		try {
			this.kitAlimentacaoService.salvar(kitAlimentacao);
		}catch (ColegioJaCadastradaException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return nova(kitAlimentacao);
		}
		attributes.addFlashAttribute("mensagem", "Colegio cadastrada com sucesso!");
		return new ModelAndView("redirect:/colegios/nova");
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long codigo) {
		KitAlimentacao kitAlimentacao = this.kitAlimentacoes.findById(codigo).get();
		ModelAndView mv = nova(kitAlimentacao);
		mv.addObject(kitAlimentacao);
		return mv;
	}
	
	@GetMapping
	public ModelAndView pesquisar(KitAlimentacaoFilter kitAlimentacaoFilter, BindingResult result, @PageableDefault(size=10) Pageable pageable, 
			 HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("colegio/PesquisaColegio");
		PageWrapper<KitAlimentacao> paginaWrapper = new PageWrapper<>(kitAlimentacoes.filtrar(kitAlimentacaoFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") KitAlimentacao kitAlimentacao){
		
		try {
			this.kitAlimentacaoService.excluir(kitAlimentacao);
		}catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
}
