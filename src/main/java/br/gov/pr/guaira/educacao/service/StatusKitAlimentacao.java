package br.gov.pr.guaira.educacao.service;

import br.gov.pr.guaira.educacao.repository.KitAlimentacoes;


public enum StatusKitAlimentacao {

	ATIVAR {
		@Override
		public void executar(Long[] codigos, KitAlimentacoes kitAlimentacoes) {
			kitAlimentacoes.findByCodigoIn(codigos).forEach(u -> u.setAtivo(true));
		}
	}, DESATIVAR {
		@Override
		public void executar(Long[] codigos, KitAlimentacoes kitAlimentacoes) {
			kitAlimentacoes.findByCodigoIn(codigos).forEach(u -> u.setAtivo(false));			
		}
	};
	
	public abstract void executar(Long[] codigos, KitAlimentacoes kitAlimentacoes);
}
