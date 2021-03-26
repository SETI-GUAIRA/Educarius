package br.gov.pr.guaira.educacao.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.gov.pr.guaira.educacao.model.KitAlimentacao;


@Controller
@RequestMapping("/relatorios")
public class relatoriosController {
	
//	@Autowired
//	private AssociacaoService associacaoService;
//	@Autowired
//	private JasperService service;
	
	
	 @GetMapping
	  public ModelAndView carteirinha(KitAlimentacao kitAlimentacao) {
	    ModelAndView mv = new ModelAndView("relatorio/Relatorio");
	    return mv;
	  }	 

//	@GetMapping("/todas")
//	public void exibirCarteirinha_Todas(
//			@RequestParam("acao") String acao,
//			HttpServletResponse response) throws IOException {
//		byte[] bytes = service.exportar_Carteirinha_Todas_PDF();
//		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
//		if (acao.equals("V")) {
//			response.setHeader("Content-disposition", "inline;filename=carteirinha_todas.pdf");
//		} else {
//			response.setHeader("Content-disposition", "attachment;filename=carteirinha_todas.pdf");			
//		}
//		response.getOutputStream().write(bytes);
//	}
//
//	@GetMapping("/associacao")
//	public void exibirCarteirinha_Associacao(
//			@RequestParam(name = "associacao", required = false) Integer associacao, HttpServletResponse response)
//			throws IOException {
//		service.addParams("Cod_Associacao", associacao == 0 ? null : associacao);
//
//		byte[] bytes = service.exportar_Carteirinha_Associacao_PDF();
//		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
//		response.setHeader("Content-disposition", "inline;filename=carteirinha_associacao.pdf");
//		response.getOutputStream().write(bytes);
//	}
//
//	@GetMapping("/individual/{pescador}")
//	public void exibirCarteirinhaIndividual(
//			@PathVariable("pescador") Integer pescador,
//			HttpServletResponse response) throws IOException {
//		service.addParams("Cod_Pescador", pescador == 0 ? null : pescador);
//
//		byte[] bytes = service.exportar_Carteirinha_Individual_PDF();
//		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
//		response.setHeader("Content-disposition", "inline;filename=carteirinha_individual.pdf");
//		response.getOutputStream().write(bytes);
//	}
//	
//	@ModelAttribute("associacoes")
//	  public List<Associacao> listaDeAssociacoes() {
//	    return associacaoService.buscarTodos();
//	  }

}
