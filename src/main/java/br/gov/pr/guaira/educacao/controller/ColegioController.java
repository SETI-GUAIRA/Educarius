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

import br.gov.pr.guaira.educacao.model.Colegio;
import br.gov.pr.guaira.educacao.repository.Colegios;
import br.gov.pr.guaira.educacao.service.ColegioService;


@Controller
@RequestMapping("/colegios")
public class ColegioController implements Serializable{

	private static final long serialVersionUID = 1L;
	@Autowired
	private ColegioService colegioService;
	@Autowired
	private Colegios colegios;

	@RequestMapping("/nova")
	public ModelAndView nova(Colegio colegio) {
		ModelAndView mv = new ModelAndView("colegio/CadastroColegio");
		
		return mv;
	}
	
	@PostMapping(value = {"/nova", "{\\d+}"})
	public ModelAndView salvar(@Valid Colegio colegio, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return nova(colegio);
		}
		try {
			this.colegioService.salvar(colegio);
		}catch (ColegioJaCadastradaException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return nova(colegio);
		}
		attributes.addFlashAttribute("mensagem", "Colegio cadastrada com sucesso!");
		return new ModelAndView("redirect:/colegios/nova");
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long codigo) {
		Colegio colegio = this.colegios.findById(codigo).get();
		ModelAndView mv = nova(colegio);
		mv.addObject(colegio);
		return mv;
	}
	
	@GetMapping
	public ModelAndView pesquisar(ColegioFilter colegioFilter, BindingResult result, @PageableDefault(size=10) Pageable pageable, 
			 HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("colegio/PesquisaColegio");
		PageWrapper<Colegio> paginaWrapper = new PageWrapper<>(colegios.filtrar(colegioFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Colegio colegio){
		
		try {
			this.colegioService.excluir(colegio);
		}catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
}
