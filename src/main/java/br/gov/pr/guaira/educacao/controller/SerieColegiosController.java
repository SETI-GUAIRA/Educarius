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
import br.gov.pr.guaira.educacao.exception.ImpossivelExcluirEntidadeException;
import br.gov.pr.guaira.educacao.exception.SerieColegioJaCadastradaException;
import br.gov.pr.guaira.educacao.filter.SerieColegioFilter;
import br.gov.pr.guaira.educacao.model.SerieColegio;
import br.gov.pr.guaira.educacao.repository.SeriesColegios;
import br.gov.pr.guaira.educacao.service.SerieColegioService;


@Controller
@RequestMapping("/serieColegio")
public class SerieColegiosController implements Serializable{

	private static final long serialVersionUID = 1L;
	@Autowired
	private SerieColegioService serieColegioService;
	@Autowired
	private SeriesColegios seriesColegios;

	@RequestMapping("/nova")
	public ModelAndView nova(SerieColegio serie) {
		ModelAndView mv = new ModelAndView("serieColegio/CadastroSerie");
		
		return mv;
	}
	
	@PostMapping(value = {"/nova", "{\\d+}"})
	public ModelAndView salvar(@Valid SerieColegio serieColegio, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return nova(serieColegio);
		}
		try {
			this.serieColegioService.salvar(serieColegio);
		}catch (SerieColegioJaCadastradaException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return nova(serieColegio);
		}
		attributes.addFlashAttribute("mensagem", "Série cadastrada com sucesso!");
		return new ModelAndView("redirect:/serieColegio/nova");
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long codigo) {
		SerieColegio serie = this.seriesColegios.findById(codigo).get();
		ModelAndView mv = nova(serie);
		mv.addObject(serie);
		return mv;
	}
	
	@GetMapping
	public ModelAndView pesquisar(SerieColegioFilter serieColegioFilter, BindingResult result, @PageableDefault(size=10) Pageable pageable, 
			 HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("serieColegio/PesquisaSeries");
		PageWrapper<SerieColegio> paginaWrapper = new PageWrapper<>(seriesColegios.filtrar(serieColegioFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") SerieColegio serieColegio){
		
		try {
			this.serieColegioService.excluir(serieColegio);
		}catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
}
