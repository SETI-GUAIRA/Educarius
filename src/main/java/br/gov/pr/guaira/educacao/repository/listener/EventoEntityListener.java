package br.gov.pr.guaira.educacao.repository.listener;

import javax.persistence.PostLoad;

import br.gov.pr.guaira.educacao.PlanoApplication;
import br.gov.pr.guaira.educacao.model.Evento;
import br.gov.pr.guaira.educacao.storage.FotoStorage;
//Aula 32.11 
public class EventoEntityListener {

	@SuppressWarnings("static-access")
	@PostLoad
	public void postLoad(final Evento evento) {

		FotoStorage fotoStorage = PlanoApplication.getBean(FotoStorage.class);
		evento.setUrlFoto(fotoStorage.getUrl(evento.getFotoOuMock()));
		evento.setUrlThumbnailFoto(fotoStorage.getUrl(fotoStorage.THUMBNAIL_PREFIX + evento.getFotoOuMock()));
	}
}
