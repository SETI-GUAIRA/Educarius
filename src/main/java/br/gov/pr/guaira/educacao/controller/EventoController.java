package br.gov.pr.guaira.educacao.controller;

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
import br.gov.pr.guaira.educacao.exception.ImpossivelExcluirEntidadeException;
import br.gov.pr.guaira.educacao.filter.EventoFilter;
import br.gov.pr.guaira.educacao.filter.SemanaFilter;
import br.gov.pr.guaira.educacao.model.Evento;
import br.gov.pr.guaira.educacao.model.Semana;
import br.gov.pr.guaira.educacao.repository.Eventos;
import br.gov.pr.guaira.educacao.repository.Semanas;
import br.gov.pr.guaira.educacao.service.EventoService;
import br.gov.pr.guaira.educacao.service.SemanaService;
import br.gov.pr.guaira.educacao.validation.DataValidacaoException;

@Controller
@RequestMapping("/eventos")
public class EventoController {
	
	@Autowired
	private EventoService eventoService;
	@Autowired
	private Eventos eventos;

	@GetMapping("/nova")
	public ModelAndView nova(Evento evento) {
		ModelAndView mv = new ModelAndView("evento/CadastroEvento");
		
		return mv;
	}
	
	@PostMapping(value = {"/nova", "{\\d+}"})
	public ModelAndView salvar(@Valid Evento evento, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return nova(evento);
		}
		try {
			this.eventoService.salvar(evento);
		}catch (DataValidacaoException e) {
			result.rejectValue("codigo", e.getMessage(), e.getMessage());
			return nova(evento);
		}
		
		attributes.addFlashAttribute("mensagem", "Evento criado com sucesso!");
		return new ModelAndView("redirect:/eventos/nova");
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long codigo) {
		Evento evento = this.eventos.findById(codigo).get();
		ModelAndView mv = nova(evento);
		mv.addObject(evento);
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Evento evento){		
		try {
			this.eventoService.excluir(evento);
		}catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ModelAndView pesquisar(EventoFilter semanaFilter, BindingResult binding,@PageableDefault(size = 10) Pageable pageable,
			HttpServletRequest httpServletRequest) {
		
		ModelAndView mv = new ModelAndView("evento/PesquisaEvento");
		PageWrapper<Evento> paginaWrapper = new PageWrapper<>(this.eventos.filtrar(semanaFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
}
