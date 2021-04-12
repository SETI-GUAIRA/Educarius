package br.gov.pr.guaira.educacao.controller;



import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import br.gov.pr.guaira.educacao.controller.page.PageWrapper;
import br.gov.pr.guaira.educacao.exception.ImpossivelExcluirEntidadeException;
import br.gov.pr.guaira.educacao.filter.UsuarioFilter;
import br.gov.pr.guaira.educacao.model.Usuario;
import br.gov.pr.guaira.educacao.repository.Colegios;
import br.gov.pr.guaira.educacao.repository.Grupos;
import br.gov.pr.guaira.educacao.repository.Usuarios;
import br.gov.pr.guaira.educacao.service.EmailUsuarioJaCadastrado;
import br.gov.pr.guaira.educacao.service.SenhaObrigatorioUsuarioException;
import br.gov.pr.guaira.educacao.service.StatusUsuario;
import br.gov.pr.guaira.educacao.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController{

	@Autowired
	private Grupos grupos;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private Colegios colegios;
	
	

	@RequestMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
		mv.addObject("grupos", grupos.findAll());
		mv.addObject("colegios", this.colegios.findAll());		
		return mv;
	}

	@PostMapping({"/novo", "{\\d+}"})
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			return novo(usuario);
		}
		try {
			this.usuarioService.salvar(usuario);
		} catch (EmailUsuarioJaCadastrado e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return novo(usuario);
		} catch (SenhaObrigatorioUsuarioException e) {
			result.rejectValue("senha", e.getMessage(), e.getMessage());
			return novo(usuario);
		}
		attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
		return new ModelAndView("redirect:/usuarios/novo");

	}
	
	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter usuarioFilter, @PageableDefault(size=50) Pageable pageable,
			HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("usuario/PesquisaUsuarios");
		mv.addObject("grupos", grupos.findAll());
		
		PageWrapper<Usuario> paginaWrapper = new PageWrapper<>(this.usuarios.filtrar(usuarioFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestParam("codigos[]") Long[] codigos, @RequestParam("status") StatusUsuario status) {
		usuarioService.alterarStatus(codigos, status);
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Usuario usuario){
		
		try {
			this.usuarioService.excluir(usuario);
		}catch (ImpossivelExcluirEntidadeException e) {
			
			return ResponseEntity.badRequest().body(e.getMessage());
		}
				
		return ResponseEntity.ok().build();
		
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Usuario usuario = this.usuarios.buscarComGrupos(codigo);
		ModelAndView mv = novo(usuario);
		mv.addObject(usuario);
		
		return mv;
	}
}
