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
import br.gov.pr.guaira.educacao.filter.EventoFilter;
import br.gov.pr.guaira.educacao.filter.SemanaFilter;
import br.gov.pr.guaira.educacao.filter.PalestraFilter;
import br.gov.pr.guaira.educacao.model.Aula;
import br.gov.pr.guaira.educacao.model.Palestra;
import br.gov.pr.guaira.educacao.repository.Aulas;
import br.gov.pr.guaira.educacao.repository.Eventos;
import br.gov.pr.guaira.educacao.repository.Materias;
import br.gov.pr.guaira.educacao.repository.Palestras;
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
	private Eventos eventos;
	@Autowired
	private Series series;
	@Autowired
	private Palestras palestras;
	
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
//	@GetMapping("/merenda")
//	public ModelAndView nova(Aula aula) {
//		ModelAndView mv = new ModelAndView("merenda/cadastroMerenda");
//		mv.addObject("series", this.series.findByOrderByNomeAsc());
//		mv.addObject("materias", this.materias.findByOrderByNomeAsc());
//		mv.addObject("semanas", this.semanas.findByOrderByDataFinalDesc());
//		return mv;
//	}	
	
	@GetMapping("/educacaoInfantil")
	public ModelAndView educacaoInfantil() {
		ModelAndView mv = new ModelAndView("atividades/educacao_infantil");
		
		return mv;
	}
//	EDUCAÇÁO ESPECIAL------------------------------
	@GetMapping("/educacaoespecial")
	public ModelAndView educacaoEspecialEventos(EventoFilter eventoFilter, BindingResult result, @PageableDefault(size=10) Pageable pageable, 
			 HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("educacaoespecial/educacaoEspecial.html");	
		mv.addObject("eventos", this.eventos.findAll());		
		return mv;
	}
	@GetMapping("/educacaoespecial/eventos/{codigoEvento}")
	public ModelAndView educacaoEspecialEvento(@PathVariable("codigoEvento") Long codigoEvento, PalestraFilter palestraFilter , @PageableDefault(size = 15) Pageable pageable, 
			HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("educacaoespecial/EventosDisponiveis");	
		mv.addObject("eventos", this.eventos.findById(codigoEvento).get());
		palestraFilter.setEvento(this.eventos.findById(codigoEvento).get());
		PageWrapper<Palestra> paginaWrapper = new PageWrapper<>(this.palestras.filtrar(palestraFilter, pageable), httpServletRequest);		
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	@GetMapping("/educacaoespecial/palestras/{codigoPalestra}")
	public ModelAndView educacaoEspecialEventoPalestra(@PathVariable("codigoPalestra") Long codigoPalestra, PalestraFilter palestraFilter , @PageableDefault(size = 15) Pageable pageable, 
			HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("educacaoespecial/Palestra");	
	//    mv.addObject("eventos", this.eventos.findById(codigoEvento).get());
//		palestraFilter.setEvento(this.eventos.findById(codigoEvento).get());
		mv.addObject("palestras", this.palestras.findById(codigoPalestra).get());
		PageWrapper<Palestra> paginaWrapper = new PageWrapper<>(this.palestras.filtrar(palestraFilter, pageable), httpServletRequest);		
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

//	@GetMapping("/educacaoespecial/eventos01")
//	public ModelAndView educacaoEspecialEvento01() {
//		ModelAndView mv = new ModelAndView("educacaoespecial/Evento01");		
//		return mv;
//	}
//	@GetMapping("/educacaoespecial/eventos02")
//	public ModelAndView educacaoEspecialEvento02() {
//		ModelAndView mv = new ModelAndView("educacaoespecial/Evento02");
//		
//		return mv;
//	}
//	@GetMapping("/educacaoespecial/eventos03")
//	public ModelAndView educacaoEspecialEvento03() {
//		ModelAndView mv = new ModelAndView("educacaoespecial/Evento03");
//		
//		return mv;
//	}
//	@GetMapping("/educacaoespecial/eventos04")
//	public ModelAndView educacaoEspecialEvento04() {
//		ModelAndView mv = new ModelAndView("educacaoespecial/Evento04");
//		
//		return mv;
//	}
//	@GetMapping("/educacaoespecial/eventos05")
//	public ModelAndView educacaoEspecialEvento05() {
//		ModelAndView mv = new ModelAndView("educacaoespecial/Evento05");
//		
//		return mv;
//	}
//	@GetMapping("/educacaoespecial/eventos06")
//	public ModelAndView educacaoEspecialEvento06() {
//		ModelAndView mv = new ModelAndView("educacaoespecial/Evento06");
//		
//		return mv;
//	}
//	@GetMapping("/educacaoespecial/eventos07")
//	public ModelAndView educacaoEspecialEvento07() {
//		ModelAndView mv = new ModelAndView("educacaoespecial/Evento07");
//		
//		return mv;
//	}
//	@GetMapping("/educacaoespecial/eventos08")
//	public ModelAndView educacaoEspecialEvento08() {
//		ModelAndView mv = new ModelAndView("educacaoespecial/Evento08");
//		
//		return mv;
//	}
	
	
	
//	--------------------------------------------
	
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
	
	@GetMapping("/encerramento-ano-letivo")
	public ModelAndView encerramentoAnoLetivo() {
		ModelAndView mv = new ModelAndView("atividades/encerramentoAnoLetivo");
		
		return mv;
	}
	
	@GetMapping("/semanasLiberadas/{codigoSerie}")
	public ModelAndView pesquisarSite(SemanaFilter semanaFilter, BindingResult binding,@PageableDefault(size = 6) Pageable pageable,
		HttpServletRequest httpServletRequest, @PathVariable("codigoSerie") Long codigoSerie) {		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
		ModelAndView mv = new ModelAndView("plano/ListaSemanasSite");
		System.out.println(this.semanas.buscaSemanasSite(authentication.getName(), this.series.getOne(codigoSerie)));
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
