package br.gov.pr.guaira.educacao.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.gov.pr.guaira.educacao.model.Serie;
import br.gov.pr.guaira.educacao.model.SerieColegio;

@Component
public class SerieColegioConverter implements Converter<String, SerieColegio>{

	@Override
	public SerieColegio convert(String codigo) {
		if (!StringUtils.isEmpty(codigo)) {
			SerieColegio serieColegio = new SerieColegio();
			serieColegio.setCodigo(Long.valueOf(codigo));
			return serieColegio;
		}
		return null;
	}

}
