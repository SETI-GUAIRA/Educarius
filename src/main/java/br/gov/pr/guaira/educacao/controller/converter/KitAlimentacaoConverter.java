package br.gov.pr.guaira.educacao.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.gov.pr.guaira.educacao.model.Colegio;
import br.gov.pr.guaira.educacao.model.KitAlimentacao;
import br.gov.pr.guaira.educacao.model.Serie;

@Component
public class KitAlimentacaoConverter implements Converter<String, KitAlimentacao>{

	@Override
	public KitAlimentacao convert(String codigo) {
		if (!StringUtils.isEmpty(codigo)) {
			KitAlimentacao kitAlimentacao = new KitAlimentacao();
			kitAlimentacao.setCodigo(Long.valueOf(codigo));
			return kitAlimentacao;
		}
		return null;
	}

}
