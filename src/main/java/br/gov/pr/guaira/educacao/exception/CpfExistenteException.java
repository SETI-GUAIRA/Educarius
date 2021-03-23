package br.gov.pr.guaira.educacao.exception;

public class CpfExistenteException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CpfExistenteException(String mensagem) {
		super(mensagem);
	}
}
