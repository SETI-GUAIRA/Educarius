package br.gov.pr.guaira.educacao.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;



import br.gov.pr.guaira.educacao.exception.PalestraJaExistenteException;
import br.gov.pr.guaira.educacao.exception.ImpossivelExcluirEntidadeException;
import br.gov.pr.guaira.educacao.exception.KitAlimentacaoJaExistenteException;
import br.gov.pr.guaira.educacao.model.KitAlimentacao;
import br.gov.pr.guaira.educacao.model.Palestra;
import br.gov.pr.guaira.educacao.repository.Palestras;

@Service
public class PalestraService {

	@Autowired
	private Palestras palestras;
	
//	public void salvar(Palestra palestra) {		
//		palestra.setDataCadastro(LocalDateTime.now());
//		configuraUrlVideos(palestra);
//		this.palestras.saveAndFlush(palestra);
//	}
	
	
	
	public void salvar(MultipartFile file, Palestra palestra) {			
		String fotoNome = file.getOriginalFilename();
        System.out.println("teste----------"+file.getOriginalFilename());
        palestra.setDataCadastro(LocalDateTime.now());
		configuraUrlVideos(palestra);
        if(file.getOriginalFilename().isEmpty() && palestra.isNova()==false) {
        	System.out.println("foto em branco");
        	try { 
        		  Palestra palestraNew = pegaPalestraId(palestra.getCodigo()).get();
    			  palestraNew.setDataCadastro(palestra.getDataCadastro());
    			  palestraNew.setNomePalestrante(palestra.getNomePalestrante());
    			  palestraNew.setNomePalestra(palestra.getNomePalestra());
    			  palestraNew.setDataInicial(palestra.getDataInicial());
    			  palestraNew.setDescricao(palestra.getDescricao());
    			  palestraNew.setDescricaoHtml(palestra.getDescricaoHtml());
    			  palestraNew.setUrlVideoAula1(palestra.getUrlVideoAula1());
    			  palestraNew.setUrlVideoAula2(palestra.getUrlVideoAula2());
    			  palestraNew.setEvento(palestra.getEvento());
    			  palestraNew.setCodigo(palestra.getCodigo());
    			  
    			  this.palestras.saveAndFlush(palestraNew);
    		  }
    		  catch(Exception e) {
    			  e.printStackTrace();
    		  }
        }else {
        	try { 
  			  Palestra palestraNew = new Palestra(fotoNome,file.getContentType(),file.getBytes());
  			  palestraNew.setDataCadastro(palestra.getDataCadastro());
  			  palestraNew.setNomePalestrante(palestra.getNomePalestrante());
  			  palestraNew.setNomePalestra(palestra.getNomePalestra());
  			  palestraNew.setDataInicial(palestra.getDataInicial());
  			  palestraNew.setDescricao(palestra.getDescricao());
  			  palestraNew.setDescricaoHtml(palestra.getDescricaoHtml());
  			  palestraNew.setUrlVideoAula1(palestra.getUrlVideoAula1());
  			 palestraNew.setUrlVideoAula2(palestra.getUrlVideoAula2());
  			  palestraNew.setEvento(palestra.getEvento());
  			  palestraNew.setCodigo(palestra.getCodigo());
  			  
  			  this.palestras.saveAndFlush(palestraNew);
  		  }
  		  catch(Exception e) {
  			  e.printStackTrace();
  		  }
        }
		
		
		 
		
	}
	
	
	@Transactional
	public void excluir(Palestra palestra) {
		
		try {
			this.palestras.delete(palestra);
			this.palestras.flush();
		}catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Esta aula não pode ser excluída!");
		}
	}
	
	private void configuraUrlVideos(Palestra palestra) {
		String url1 = palestra.getUrlVideoAula1();
		String url2 = palestra.getUrlVideoAula2();		
		
			if(!StringUtils.isEmpty(url1) && url1.indexOf("?v=")!= -1 ) {
				palestra.setUrlVideoAula1(url1.substring(url1.indexOf("?v=")+3));
			}		
			if(!StringUtils.isEmpty(url2) && url2.indexOf("?v=")!= -1 ) {
				palestra.setUrlVideoAula2(url2.substring(url2.indexOf("?v=")+3));
			}
			
	}
	public Optional<Palestra> pegaPalestraId(long id) {
		  return palestras.findById(id);
	  }
}
