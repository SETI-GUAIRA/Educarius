package br.gov.pr.guaira.educacao.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.gov.pr.guaira.educacao.controller.page.PageWrapper;
import br.gov.pr.guaira.educacao.filter.AulaFilter;
import br.gov.pr.guaira.educacao.filter.SemanaFilter;
import br.gov.pr.guaira.educacao.model.Aula;
import br.gov.pr.guaira.educacao.repository.Aulas;
import br.gov.pr.guaira.educacao.repository.Semanas;
import br.gov.pr.guaira.educacao.repository.Series;

@Controller
@RequestMapping("/")
public class InicioController {
	
	@Autowired
	private Semanas semanas;
	@Autowired
	private Aulas aulas;
	@Autowired
	private Series series;
	
	@GetMapping
	public ModelAndView inicio() {
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("series", this.series.findByOrderByNomeAsc());
		return mv;
	}
	
	@GetMapping("/segunda")
	public ModelAndView segunda() {
		ModelAndView mv = new ModelAndView("plano/segunda");
		
		return mv;
	}
	
	@GetMapping("/terca")
	public ModelAndView terca() {
		ModelAndView mv = new ModelAndView("plano/terca");
		
		return mv;
	}
	
	@GetMapping("/quarta")
	public ModelAndView quarta() {
		ModelAndView mv = new ModelAndView("plano/quarta");
		
		return mv;
	}
	@GetMapping("/quinta")
	public ModelAndView quinta() {
		ModelAndView mv = new ModelAndView("plano/quinta");
		
		return mv;
	}
	@GetMapping("/sexta")
	public ModelAndView sexta() {
		ModelAndView mv = new ModelAndView("plano/sexta");
		
		return mv;
	}
	
	@GetMapping("/sabado")
	public ModelAndView sabado() {
		ModelAndView mv = new ModelAndView("plano/sabado");
		
		return mv;
	}
	
	@GetMapping("/domingo")
	public ModelAndView domingo() {
		ModelAndView mv = new ModelAndView("plano/domingo");
		
		return mv;
	}
	
	@GetMapping("/saudeEscola")
	public ModelAndView saudeEscola() {
		ModelAndView mv = new ModelAndView("atividades/saude_escola");
		
		return mv;
	}
	
	@GetMapping("/educacaoInfantil")
	public ModelAndView educacaoInfantil() {
		ModelAndView mv = new ModelAndView("atividades/educacao_infantil");
		
		return mv;
	}
	
	@GetMapping("/atividadesBncc")
	public ModelAndView atividadesBncc() {
		ModelAndView mv = new ModelAndView("atividades/atividades_bncc");
		
		return mv;
	}
	
	@GetMapping("/videos")
	public ModelAndView videos() {
		ModelAndView mv = new ModelAndView("atividades/videos");
		
		return mv;
	}
	
	@GetMapping("/inclusao")
	public ModelAndView inclusao() {
		ModelAndView mv = new ModelAndView("atividades/inclusao");
		
		return mv;
	}
	
	@GetMapping("/brincadeiras")
	public ModelAndView brincadeiras() {
		ModelAndView mv = new ModelAndView("atividades/brincadeiras");
		
		return mv;
	}
	
	@GetMapping("/rotinas")
	public ModelAndView rotinas() {
		ModelAndView mv = new ModelAndView("atividades/rotina");
		
		return mv;
	}
	
	@GetMapping("/musicalizacao")
	public ModelAndView musicalizacao() {
		ModelAndView mv = new ModelAndView("atividades/musicalizacao");
		
		return mv;
	}
	
	@GetMapping("/semanasLiberadas/{codigoSerie}")
	public ModelAndView pesquisarSite(SemanaFilter semanaFilter, BindingResult binding,@PageableDefault(size = 6) Pageable pageable,
			HttpServletRequest httpServletRequest, @PathVariable("codigoSerie") Long codigoSerie) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		ModelAndView mv = new ModelAndView("plano/ListaSemanasSite");
		mv.addObject("semanas", this.semanas.buscaSemanasSite(authentication.getName(), this.series.getOne(codigoSerie)));
		mv.addObject("serie", codigoSerie);
		
		return mv;
	}
	
	@GetMapping("/aulasOnline/{codigoSerie}/{codigoSemana}")
	public ModelAndView aulas(@PathVariable("codigoSerie") Long codigoSerie, @PathVariable("codigoSemana") Long codigoSemana, AulaFilter aulaFilter , @PageableDefault(size = 15) Pageable pageable, 
			HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("plano/AulasDisponiveis");
		aulaFilter.setSerie(this.series.findById(codigoSerie).get());
		aulaFilter.setSemana(this.semanas.findById(codigoSemana).get());
		
		PageWrapper<Aula> paginaWrapper = new PageWrapper<>(this.aulas.filtrar(aulaFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		mv.addObject("semana", codigoSemana);
		mv.addObject("serie", codigoSerie);
		return mv;
	}
	
	@GetMapping("/aula/{codigoSemana}/{codigoSerie}/{codigoAula}")
	public ModelAndView aulaSelecionada(@PathVariable("codigoSemana") Long codigoSemana, @PathVariable("codigoSerie") Long codigoSerie,
			@PathVariable("codigoAula") Aula aula) {
		ModelAndView mv = new ModelAndView("plano/Aula");
		Optional<Aula> aulaOptional = Optional.ofNullable(this.aulas.buscaAulaCadastrada(aula).get(0));
		mv.addObject("aula", aulaOptional.get());
		return mv;
	}
}
