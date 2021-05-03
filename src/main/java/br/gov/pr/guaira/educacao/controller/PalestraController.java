package br.gov.pr.guaira.educacao.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import br.gov.pr.guaira.educacao.controller.page.PageWrapper;
import br.gov.pr.guaira.educacao.exception.PalestraJaExistenteException;
import br.gov.pr.guaira.educacao.filter.PalestraFilter;
import br.gov.pr.guaira.educacao.model.Palestra;
import br.gov.pr.guaira.educacao.repository.Eventos;
import br.gov.pr.guaira.educacao.repository.Palestras;
import br.gov.pr.guaira.educacao.service.PalestraService;
import br.gov.pr.guaira.educacao.exception.ImpossivelExcluirEntidadeException;


@Controller
@RequestMapping("/palestras")
public class PalestraController {

	@Autowired
	private Eventos eventos;
	@Autowired
	private Palestras palestras;
	@Autowired
	private PalestraService palestraService;
	
	
	@GetMapping("/nova")
	public ModelAndView nova(Palestra palestra) {
		ModelAndView mv = new ModelAndView("palestra/CadastroPalestra");
	//	mv.addObject("eventos", this.eventos.findByOrderByNomeEventoAsc());	
		mv.addObject("eventos", this.eventos.findAll());
		return mv;
	}
	
//	@PostMapping(value = {"/nova", "{\\d+}"})
//	public ModelAndView salvar(@Valid Palestra palestra, BindingResult result, RedirectAttributes attributes) {
//		System.out.println("data ="+palestra.getFotoPatestrante());
//		if(result.hasErrors()) {
//			return nova(palestra);
//		}
//		try {
//			this.palestraService.salvar(palestra);
//		}catch (PalestraJaExistenteException e) {			
//			attributes.addFlashAttribute("mensagem", "palestra já está cadastrada com esse nome!");
//			return nova(palestra);
//		}
//		
//		attributes.addFlashAttribute("mensagem", "Palestra salva com sucesso!");
//		return new ModelAndView("redirect:/palestras/nova");
//	}
	
	@PostMapping(value = {"/nova", "{\\d+}"})
	public ModelAndView salvar(@Valid Palestra palestra, BindingResult result, RedirectAttributes attributes, @RequestParam("files") MultipartFile[] files) {
		//System.out.println(palestra.getDescricaoHtml());
		if(result.hasErrors()) {
			return nova(palestra);
		}
		try {
			for (MultipartFile file: files) {		
				System.out.println("teste file vasil"+file);
				this.palestraService.salvar(file,palestra);				
			}
			
		}catch (PalestraJaExistenteException e) {			
			attributes.addFlashAttribute("mensagem", "palestra já está cadastrada com esse nome!");
			return nova(palestra);
		}
		
		attributes.addFlashAttribute("mensagem", "Palestra salva com sucesso!");
		return new ModelAndView("redirect:/palestras/nova");
	}
	
	
	
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Palestra palestra) {
		ModelAndView mv = nova(palestra);
		mv.addObject(palestra);
		
		return mv;
	}
	@GetMapping
	public ModelAndView pesquisar(Palestra palestra, PalestraFilter palestraFilter, BindingResult result, @PageableDefault(size=10) Pageable pageable, 
			 HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("palestra/PesquisaPalestras");
		mv.addObject("eventos", this.eventos.findAll());		
		PageWrapper<Palestra> paginaWrapper = new PageWrapper<>(this.palestras.filtrar(palestraFilter, pageable), httpServletRequest);		
		mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Palestra palestra){
		try {
			this.palestraService.excluir(palestra);
		}catch (ImpossivelExcluirEntidadeException e) {
			ResponseEntity.badRequest().body(e.getMessage());
			nova(palestra);
		}
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/downloadFile/{fileId}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId){
		Palestra palestra = palestraService.pegaPalestraId(fileId).get();
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(palestra.getFotoType()))
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+palestra.getFotoNome()+"\"")
				.body(new ByteArrayResource(palestra.getFotoPatestrante()));
	}
}
