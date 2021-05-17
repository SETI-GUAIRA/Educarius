package br.gov.pr.guaira.educacao.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lowagie.text.pdf.codec.Base64.InputStream;
import com.lowagie.text.pdf.codec.Base64.OutputStream;

import br.gov.pr.guaira.educacao.filter.KitAlimentacaoFilter;
import br.gov.pr.guaira.educacao.model.Colegio;
import br.gov.pr.guaira.educacao.model.KitAlimentacao;
import br.gov.pr.guaira.educacao.model.Pedido;
import br.gov.pr.guaira.educacao.model.SerieColegio;
import br.gov.pr.guaira.educacao.model.Turma;
import br.gov.pr.guaira.educacao.model.Turno;
import br.gov.pr.guaira.educacao.repository.Colegios;
import br.gov.pr.guaira.educacao.repository.Pedidos;
import br.gov.pr.guaira.educacao.repository.SeriesColegios;
import br.gov.pr.guaira.educacao.security.UsuarioSistema;
import br.gov.pr.guaira.educacao.service.JasperService;
import br.gov.pr.guaira.educacao.service.PedidoService;

import net.sf.jasperreports.engine.JRException;
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
private Pedidos pedidos;	



@Autowired
private SeriesColegios seriesColegios;		
	
//@Autowired
//private Connection connection;

  @GetMapping("/recibo")
  public ModelAndView recibo(KitAlimentacaoFilter kitAlimentacaoFilter) {
    ModelAndView mv = new ModelAndView("relatorio/Recibo");  
    UsuarioSistema usuario = (UsuarioSistema)SecurityContextHolder.getContext().getAuthentication().getPrincipal();	
	kitAlimentacaoFilter.setColegio(usuario.getUsuario().getColegio());	
    return mv;
  }
  @GetMapping("/todosAtivo")
  public ModelAndView todosAtivo(KitAlimentacaoFilter kitAlimentacaoFilter) {
    ModelAndView mv = new ModelAndView("relatorio/TodosAtivo"); 
    UsuarioSistema usuario = (UsuarioSistema)SecurityContextHolder.getContext().getAuthentication().getPrincipal();	
	kitAlimentacaoFilter.setColegio(usuario.getUsuario().getColegio());	
    return mv;
  }

//  @GetMapping("/conn")
//  public String myConn(Model model) {
//  	model.addAttribute("conn", connection != null ? "Conexão ok!" : "Ops... sem conexão");
//      return "relatorio/Relatorio";
//  }  


	
	@PostMapping("/recibo")
	public void exibir_Recibo(KitAlimentacaoFilter kitAlimentacaoFilter,	       
	        HttpServletResponse response)
			throws Exception {   
		service.addParams("PEDIDO", kitAlimentacaoFilter.getPedido().getCodigo());
		service.addParams("ESCOLA", kitAlimentacaoFilter.getColegio().getCodigo());
		service.addParams("SERIE", kitAlimentacaoFilter.getSerieColegio().getCodigo());
		service.addParams("TURMA", kitAlimentacaoFilter.getTurma());
		service.addParams("TURNO", kitAlimentacaoFilter.getTurno());

		byte[] bytes = service.recibo_Kit_Alimentacao_PDF();
		response.setContentType(MediaType.APPLICATION_PDF_VALUE);		
		response.setHeader("Content-disposition", "inline;filename=colegio.pdf");
		response.getOutputStream().write(bytes);	
	}
	@PostMapping("/todosAtivos")
	public void todosAtivos(KitAlimentacaoFilter kitAlimentacaoFilter,	       
	        HttpServletResponse response)
			throws Exception {   
		
		service.addParams("ESCOLA", kitAlimentacaoFilter.getColegio().getCodigo());
		service.addParams("SERIE", kitAlimentacaoFilter.getSerieColegio().getCodigo());
		service.addParams("TURMA", kitAlimentacaoFilter.getTurma());
		service.addParams("TURNO", kitAlimentacaoFilter.getTurno());

		byte[] bytes = service.todosAtivos_Kit_Alimentacao_PDF();
		response.setContentType(MediaType.APPLICATION_PDF_VALUE);		
		response.setHeader("Content-disposition", "inline;filename=colegio.pdf");
		response.getOutputStream().write(bytes);	
	}
	
	@GetMapping("/todosAtivoPedido")
	public void todosAtivoPedido(     
	        HttpServletResponse response)
			throws Exception {   
		byte[] bytes = service.pedido_Todos_Ativos_PDF();
		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
		response.setHeader("Content-disposition", "inline;filename=Pedido.pdf");
		response.getOutputStream().write(bytes);
	}
	
	
	@GetMapping("/pedido/{data}")
	public void exibirCarteirinhaIndividual(
			@PathVariable("data") Date data,
			HttpServletResponse response) throws IOException, JRException, SQLException {
		service.addParams("Data_Pedido", data);

		byte[] bytes = service.pedido_Kit_Alimentacao_PDF();
		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
		response.setHeader("Content-disposition", "inline;filename=Pedido.pdf");
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
	@ModelAttribute("pedidos")
	  public List<Pedido> listaPedidos() {
	    return pedidos.findAllByOrderByCodigoDesc();
	 }
	@ModelAttribute("colegios")
	  public List<Colegio> listaColegios() {
	    return colegios.findAll();
	 }
	
	@ModelAttribute("seriesColegios")
	  public List<SerieColegio> listaSeriesColegios() {
	    return seriesColegios.findAll();
	 }
}
