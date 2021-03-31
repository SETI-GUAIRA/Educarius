package br.gov.pr.guaira.educacao.model;

public enum Turno {

	TARDE("Tarde"), MANHA("Manhã"), NOITE("Noite"), INTEGRAL("Integral");
	
	private Turno(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
