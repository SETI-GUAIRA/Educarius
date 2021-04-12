package br.gov.pr.guaira.educacao.controller;


import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import br.gov.pr.guaira.educacao.filter.KitAlimentacaoFilter;
import br.gov.pr.guaira.educacao.model.Colegio;
import br.gov.pr.guaira.educacao.model.KitAlimentacao;
import br.gov.pr.guaira.educacao.model.Pedido;
import br.gov.pr.guaira.educacao.model.Turma;
import br.gov.pr.guaira.educacao.model.Turno;

import br.gov.pr.guaira.educacao.repository.Colegios;
import br.gov.pr.guaira.educacao.repository.KitAlimentacoes;

import br.gov.pr.guaira.educacao.repository.SeriesColegios;
import br.gov.pr.guaira.educacao.security.UsuarioSistema;
import br.gov.pr.guaira.educacao.service.KitAlimentacaoService;
import br.gov.pr.guaira.educacao.service.StatusKitAlimentacao;
import br.gov.pr.guaira.educacao.service.StatusUsuario;
import br.gov.pr.guaira.educacao.model.TipoPessoa;
import br.gov.pr.guaira.educacao.controller.page.PageWrapper;
import br.gov.pr.guaira.educacao.exception.CpfExistenteException;
import br.gov.pr.guaira.educacao.exception.ImpossivelExcluirEntidadeException;
import br.gov.pr.guaira.educacao.exception.KitAlimentacaoJaExistenteException;
import br.gov.pr.guaira.educacao.exception.PedidoJaCadastradoException;



@Controller
@RequestMapping("/kitAlimentacao")
public class KitAlimentacaoController {

	@Autowired
	private SeriesColegios seriesColegios;		
	
	@Autowired
	private KitAlimentacoes kitAlimentacoes;
	
	@Autowired
	private KitAlimentacaoService kitAlimentacaoService;
	
	@Autowired
	private Colegios colegios;
	
	@GetMapping("/nova")
	public ModelAndView nova(KitAlimentacao kitAlimentacao) {
		ModelAndView mv = new ModelAndView("kitAlimentacao/CadastroKitAlimentacao");
		mv.addObject("seriesColegios", this.seriesColegios.findAll());
		mv.addObject("tiposPessoa", TipoPessoa.values());
		mv.addObject("colegios", this.colegios.findAll());		
		return mv;
	}
	//REFERENTE A TELA PRINCIPAL DE CADASTRO PUBLICA
	@PostMapping("/nova")
	public ModelAndView salvar(@Valid KitAlimentacao kitAlimentacao, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return nova(kitAlimentacao);
		}
		try {
			this.kitAlimentacaoService.salvar(kitAlimentacao);
		}catch (KitAlimentacaoJaExistenteException e) {
				result.rejectValue("nomeAluno", e.getMessage(), e.getMessage());					
				return nova(kitAlimentacao);	
		}catch (ConstraintViolationException e) {
			String msg = "O CPF informado é inválido";
			result.rejectValue("cpf", msg, msg);
			return nova(kitAlimentacao);
		}
		attributes.addFlashAttribute("mensagem", "Cadastro efetuado com sucesso!");
		return new ModelAndView("redirect:/kitAlimentacao/nova");
	}
	
	@GetMapping("/editar")
	public ModelAndView edita(KitAlimentacao kitAlimentacao) {
		ModelAndView mv = new ModelAndView("kitAlimentacao/EditaKitAlimentacao");
		mv.addObject("seriesColegios", this.seriesColegios.findAll());
		mv.addObject("colegios", this.colegios.findAll());		
		mv.addObject("tiposPessoa", TipoPessoa.values());
        UsuarioSistema usuario = (UsuarioSistema)SecurityContextHolder.getContext().getAuthentication().getPrincipal();		
        kitAlimentacao.setColegio(usuario.getUsuario().getColegio());
		
		return mv;
	}
	//REFERENTE A TELA DE CADASTRO ADM
	@PostMapping(value = {"/gravar", "{\\d+}"})
	public ModelAndView gravar(@Valid KitAlimentacao kitAlimentacao, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return edita(kitAlimentacao);
		}
		try {
			this.kitAlimentacaoService.salvar(kitAlimentacao);
		}catch (KitAlimentacaoJaExistenteException e) {
			result.rejectValue("nomeAluno", e.getMessage(), e.getMessage());					
			return edita(kitAlimentacao);
		}	
//		}catch (ConstraintViolationException e) {
//			String msg = "O CPF informado é inválido";
//			result.rejectValue("cpf", msg, msg);
//			return edita(kitAlimentacao);
//		}
		attributes.addFlashAttribute("mensagem", "Cadastro Editado com sucesso!");
		return new ModelAndView("redirect:/kitAlimentacao/editar");
	}
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long codigo) {
		KitAlimentacao kitAlimentacao = this.kitAlimentacoes.findById(codigo).get();
		ModelAndView mv = edita(kitAlimentacao);
		mv.addObject(kitAlimentacao);
		return mv;
	}
	
  //PESQUISA PARA COLEGIOS 
	@GetMapping	
	public ModelAndView pesquisar(KitAlimentacaoFilter kitAlimentacaoFilter, BindingResult result, @PageableDefault(size=10) org.springframework.data.domain.Pageable pageable, 
			 HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("kitAlimentacao/PesquisaKitAlimentacao");		
		mv.addObject("seriesColegios", this.seriesColegios.findAll());
		mv.addObject("colegios", this.colegios.findAll());	

		
		UsuarioSistema usuario = (UsuarioSistema)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		kitAlimentacaoFilter.setColegio(usuario.getUsuario().getColegio());
		
		
		PageWrapper<KitAlimentacao> paginaWrapper = new PageWrapper<>(this.kitAlimentacoes.filtrar(kitAlimentacaoFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	 //PESQUISA PARA ADM consegue pesquisar alunos de todos colegios
		@GetMapping("/pesquisa")	
		public ModelAndView pesquisarAdm(KitAlimentacaoFilter kitAlimentacaoFilter, BindingResult result, @PageableDefault(size=10) org.springframework.data.domain.Pageable pageable, 
				 HttpServletRequest httpServletRequest) {
			ModelAndView mv = new ModelAndView("kitAlimentacao/PesquisaKitAlimentacaoAdm");		
			mv.addObject("seriesColegios", this.seriesColegios.findAll());
			mv.addObject("colegios", this.colegios.findAll());	

			PageWrapper<KitAlimentacao> paginaWrapper = new PageWrapper<>(this.kitAlimentacoes.filtrar(kitAlimentacaoFilter, pageable), httpServletRequest);
			mv.addObject("pagina", paginaWrapper);
			
			return mv;
		}
	
	@DeleteMapping("/{codigo}")
	public ResponseEntity<String>  excluir(@PathVariable("codigo") KitAlimentacao kitAlimentacao){
		try {
			this.kitAlimentacaoService.excluir(kitAlimentacao);
		}catch (PedidoJaCadastradoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}	
	
	
//	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") KitAlimentacao kitAlimentacao){
//		try {
//			this.kitAlimentacaoService.excluir(kitAlimentacao);
//		}catch (ImpossivelExcluirEntidadeException e) {
//			ResponseEntity.badRequest().body(e.getMessage());			
//			nova(kitAlimentacao);
//		}
//		return ResponseEntity.ok().build();
//	}
	
	
	
	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestParam("codigos[]") Long[] codigos, @RequestParam("status") StatusKitAlimentacao statusKitAlimentacao) {
		kitAlimentacaoService.alterarStatus(codigos, statusKitAlimentacao);
	}
	@ModelAttribute("turno")
	  public Turno[] getNomeTurnos() {
	    return Turno.values();
	  }
	@ModelAttribute("turma")
	  public Turma[] getNomeTurmas() {
	    return Turma.values();
	  }
}


