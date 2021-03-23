package br.gov.pr.guaira.educacao.model;

public enum Turma {

	A("A"), B("B"), C("C"), D("D"), E("E"), F("F"), H("H");
	
	private Turma(String descricao) {
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
