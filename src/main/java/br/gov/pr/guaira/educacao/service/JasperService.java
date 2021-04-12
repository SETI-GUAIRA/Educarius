package br.gov.pr.guaira.educacao.service;




import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;




@Service
public class JasperService {
	@Autowired
	private Connection connection;
	
	private Map<String, Object> params = new HashMap<>();
	
	
	public void addParams(String Key, Object value) {
		this.params.put(Key, value);
	}
	public JasperService() {
		this.params.put("IMAGEM_DIRETORIO", "classpath:jasper/");
		
	}

	
//	public byte[] recibo_Kit_Alimentacao_PDF() throws FileNotFoundException{
//		byte[] bytes = null;
//		try {	
//	
//		InputStream jasperStream = this.getClass().getResourceAsStream("/jasper/colegio.jasper");
//		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);	
//		JasperPrint print = JasperFillManager.fillReport(jasperReport, params, connection);	
//			
//		    bytes = JasperExportManager.exportReportToPdf(print);
//		} catch (JRException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return bytes;
//	
//	}
	
	public byte[] recibo_Kit_Alimentacao_PDF() throws Exception {		
		InputStream inputStream = this.getClass().getResourceAsStream("/jasper/ReciboKitAlimentacao.jasper");
		return geraRelatorio(params, inputStream, connection);
	}
	public byte[] todosAtivos_Kit_Alimentacao_PDF() throws Exception{
		InputStream inputStream = this.getClass().getResourceAsStream("/jasper/cadastroAtivos.jasper");
		return geraRelatorio(params, inputStream, connection);
	}
	public byte[] pedido_Todos_Ativos_PDF() throws Exception {
		InputStream inputStream = this.getClass().getResourceAsStream("/jasper/TodosAtivosColegios.jasper");
		return geraRelatorio(params, inputStream, connection);
	}
	
	public byte[] pedido_Kit_Alimentacao_PDF() throws JRException, SQLException {
		InputStream inputStream = this.getClass().getResourceAsStream("/jasper/Pedido.jasper");
		return geraRelatorio(params, inputStream, connection);
	}
	
	private byte[] geraRelatorio(Map<String, Object> parametros, InputStream inputStream, Connection con)
			throws JRException, SQLException {
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, con);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		}finally {			
		//	con.close();
		}
	}
	


	
	
}
