package br.gov.pr.guaira.educacao.controller;

import java.io.IOException;
import java.sql.Connection;


import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lowagie.text.pdf.codec.Base64.InputStream;
import com.lowagie.text.pdf.codec.Base64.OutputStream;

import br.gov.pr.guaira.educacao.filter.KitAlimentacaoFilter;
import br.gov.pr.guaira.educacao.model.KitAlimentacao;
import br.gov.pr.guaira.educacao.model.Turma;
import br.gov.pr.guaira.educacao.model.Turno;
import br.gov.pr.guaira.educacao.repository.Colegios;
import br.gov.pr.guaira.educacao.repository.SeriesColegios;
import br.gov.pr.guaira.educacao.security.UsuarioSistema;
import br.gov.pr.guaira.educacao.service.JasperService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;





@Controller
@RequestMapping("/relatorios")
public class RelatorioController { 

@Autowired
private JasperService service;	
@Autowired
private Colegios colegios;	


@Autowired
private SeriesColegios seriesColegios;		
	
@Autowired
private Connection connection;

  @GetMapping
  public ModelAndView relatorios(KitAlimentacaoFilter kitAlimentacaoFilter) {
    ModelAndView mv = new ModelAndView("relatorio/Relatorio");
    mv.addObject("colegios", this.colegios.findAll());	
    mv.addObject("seriesColegios", this.seriesColegios.findAll());
    UsuarioSistema usuario = (UsuarioSistema)SecurityContextHolder.getContext().getAuthentication().getPrincipal();	
	kitAlimentacaoFilter.setColegio(usuario.getUsuario().getColegio());	
    return mv;
  }

  @GetMapping("/conn")
  public String myConn(Model model) {
  	model.addAttribute("conn", connection != null ? "Conexão ok!" : "Ops... sem conexão");
      return "relatorio/Relatorio";
  }
  
//	@GetMapping("/todos")
//	public void exportar_Todos_PDF(
//			@RequestParam("acao") String acao,
//			HttpServletResponse response) throws IOException {
//		byte[] bytes = service.exportar_Todos_PDF();
//		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
//		if (acao.equals("V")) {
//			response.setHeader("Content-disposition", "inline;filename=todas.pdf");
//		} else {
//			response.setHeader("Content-disposition", "attachment;filename=todas.pdf");			
//		}
//		response.getOutputStream().write(bytes);
//	}


//	@GetMapping("/colegio")
//	public void exibir_Colegio(
//			@RequestParam(name = "colegio", required = false) Integer colegio, 
//			@RequestParam(name = "serieColegio", required = false) Integer serie, 
//			@RequestParam(name = "turma", required = false) String turma, 
//			@RequestParam(name = "turno", required = false) String turno,	       
//	        HttpServletResponse response)
//			throws Exception {
//    
//		
//		service.addParams("ESCOLA", colegio);
//		service.addParams("SERIE", serie);
//		service.addParams("TURMA", turma.isEmpty() ? null : turma);
//		service.addParams("TURNO", turno.isEmpty() ? null : turno);
//
//		byte[] bytes = service.recibo_Kit_Alimentacao_PDF();
//		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
//		response.setHeader("Content-disposition", "inline;filename=colegio.pdf");
//		response.getOutputStream().write(bytes);	
//	}
	
	@PostMapping("/recibo")
	public void exibir_Colegio(KitAlimentacaoFilter kitAlimentacaoFilter,	       
	        HttpServletResponse response)
			throws Exception {   
		
		service.addParams("ESCOLA", kitAlimentacaoFilter.getColegio().getCodigo());
		service.addParams("SERIE", kitAlimentacaoFilter.getSerieColegio().getCodigo());
		service.addParams("TURMA", kitAlimentacaoFilter.getTurma());
		service.addParams("TURNO", kitAlimentacaoFilter.getTurno());

		byte[] bytes = service.recibo_Kit_Alimentacao_PDF();
		response.setContentType(MediaType.APPLICATION_PDF_VALUE);		
		response.setHeader("Content-disposition", "inline;filename=colegio.pdf");
		response.getOutputStream().write(bytes);	
	}
	
	

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
		
	@ModelAttribute("turno")
	  public Turno[] getNomeTurnos() {
	    return Turno.values();
	  }
	@ModelAttribute("turma")
	  public Turma[] getNomeTurmas() {
	    return Turma.values();
	  }
	
}
