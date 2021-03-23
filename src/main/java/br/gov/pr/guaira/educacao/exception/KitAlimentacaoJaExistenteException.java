package br.gov.pr.guaira.educacao.exception;

public class KitAlimentacaoJaExistenteException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public KitAlimentacaoJaExistenteException(String msg) {
		super(msg);
	}
}
