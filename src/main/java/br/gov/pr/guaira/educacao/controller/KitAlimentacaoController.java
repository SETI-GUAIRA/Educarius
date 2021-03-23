package br.gov.pr.guaira.educacao.controller;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.gov.pr.guaira.educacao.exception.KitAlimentacaoJaExistenteException;

import br.gov.pr.guaira.educacao.model.KitAlimentacao;
import br.gov.pr.guaira.educacao.model.Turma;
import br.gov.pr.guaira.educacao.model.Turno;
import br.gov.pr.guaira.educacao.repository.Colegios;
import br.gov.pr.guaira.educacao.repository.Series;
import br.gov.pr.guaira.educacao.repository.SeriesColegios;
import br.gov.pr.guaira.educacao.service.KitAlimentacaoService;
import br.gov.pr.guaira.educacao.exception.CpfExistenteException;



@Controller
@RequestMapping("/kitAlimentacao")
public class KitAlimentacaoController {

	@Autowired
	private SeriesColegios seriesColegios;	
	
	@Autowired
	private KitAlimentacaoService kitAlimentacaoService;
	
	@Autowired
	private Colegios colegios;
	
	@GetMapping("/nova")
	public ModelAndView nova(KitAlimentacao kitAlimentacao) {
		ModelAndView mv = new ModelAndView("kitAlimentacao/cadastroKitAlimentacao");
		mv.addObject("seriesColegios", this.seriesColegios.findByOrderByNomeAsc());
		mv.addObject("colegios", this.colegios.findByOrderByNomeAsc());		
		return mv;
	}		
	@PostMapping("/nova")
	public ModelAndView salvar(@Valid KitAlimentacao kitAlimentacao, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return nova(kitAlimentacao);
		}
		try {
			this.kitAlimentacaoService.salvar(kitAlimentacao);
		}catch (CpfExistenteException e) {
			result.rejectValue("cpf", e.getMessage(), e.getMessage());
			//return ResponseEntity.badRequest().body(e.getMessage());			
			return nova(kitAlimentacao);
		}catch (ConstraintViolationException e) {
			String msg = "O CPF informado é inválido";
			result.rejectValue("cpf", msg, msg);
			return nova(kitAlimentacao);
		}
		attributes.addFlashAttribute("mensagem", "Cadastro efetuado com sucesso!");
		return new ModelAndView("redirect:/kitAlimentacao/nova");
	}
//	
//	@GetMapping("/{codigo}")
//	public ModelAndView editar(@PathVariable("codigo") Aula aula) {
//		ModelAndView mv = nova(aula);
//		mv.addObject(aula);
//		
//		return mv;
//	}
//	@GetMapping
//	public ModelAndView pesquisar(AulaFilter aulaFilter, BindingResult result, @PageableDefault(size=10) Pageable pageable, 
//			 HttpServletRequest httpServletRequest) {
//		ModelAndView mv = new ModelAndView("aula/PesquisaAulas");
//		mv.addObject("materias", this.materias.findAll());
//		mv.addObject("series", this.series.findAll());
//		mv.addObject("semanas", this.semanas.findAll());
//		PageWrapper<Aula> paginaWrapper = new PageWrapper<>(this.aulas.filtrar(aulaFilter, pageable), httpServletRequest);
//		mv.addObject("pagina", paginaWrapper);
//		
//		return mv;
//	}
//	
//	@DeleteMapping("/{codigo}")
//	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Aula aula){
//		try {
//			this.aulaService.excluir(aula);
//		}catch (ImpossivelExcluirEntidadeException e) {
//			ResponseEntity.badRequest().body(e.getMessage());
//			nova(aula);
//		}
//		return ResponseEntity.ok().build();
//	}
	@ModelAttribute("turno")
	  public Turno[] getNomeTurnos() {
	    return Turno.values();
	  }
	@ModelAttribute("turma")
	  public Turma[] getNomeTurmas() {
	    return Turma.values();
	  }
}
