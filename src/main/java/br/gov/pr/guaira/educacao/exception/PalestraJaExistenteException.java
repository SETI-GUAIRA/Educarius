package br.gov.pr.guaira.educacao.exception;

public class PalestraJaExistenteException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PalestraJaExistenteException(String msg) {
		super(msg);
	}
}
