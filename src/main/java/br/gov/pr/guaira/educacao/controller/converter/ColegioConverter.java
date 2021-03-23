package br.gov.pr.guaira.educacao.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.gov.pr.guaira.educacao.model.Colegio;
import br.gov.pr.guaira.educacao.model.Serie;

@Component
public class ColegioConverter implements Converter<String, Colegio>{

	@Override
	public Colegio convert(String codigo) {
		if (!StringUtils.isEmpty(codigo)) {
			Colegio colegio = new Colegio();
			colegio.setCodigo(Long.valueOf(codigo));
			return colegio;
		}
		return null;
	}

}
