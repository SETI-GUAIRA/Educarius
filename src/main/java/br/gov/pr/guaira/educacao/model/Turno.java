package br.gov.pr.guaira.educacao.model;

public enum Turno {

	 MANHA("MANHÃ"),TARDE("TARDE"), NOITE("NOITE"), INTEGRAL("INTEGRAL");
	
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
