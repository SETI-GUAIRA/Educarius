package br.gov.pr.guaira.educacao.model;
import br.gov.pr.guaira.educacao.model.validation.group.CnpjGroup;
import br.gov.pr.guaira.educacao.model.validation.group.CpfGroup;

public enum TipoPessoa {

	CPF("CPF", "CPF *", "000.000.000-00", CpfGroup.class) {
		@Override
		public String formatar(String cpfOuCnpj) {
			return cpfOuCnpj.replaceAll("(\\d{3})(\\d{3})(\\d{3})", "$1.$2.$3-");
		}
	},
	RG("RG", "RG *", "0000000000", CpfGroup.class) {
		@Override
		public String formatar(String cpfOuCnpj) {
			return cpfOuCnpj.replaceAll("(\\d{10})", "$1");
		}		
	},
	IE("IC", "Identidad Civil *", "0000000", CnpjGroup.class) {
		@Override
		public String formatar(String cpfOuCnpj) {
			return cpfOuCnpj.replaceAll("(\\d{7})", "$1");
		}
	};	
	private String descricao;
	private String documento;
	private String mascara;
	private Class<?> grupo;
	
	private TipoPessoa(String descricao, String documento, String mascara, Class<?> grupo) {
		this.descricao = descricao;
		this.documento = documento;
		this.mascara = mascara; 
		this.grupo = grupo;
	}
	
	public abstract String formatar(String cpfOuCnpj);

	public String getDescricao() {
		return descricao;
	}

	public String getDocumento() {
		return documento;
	}

	public String getMascara() {
		return mascara;
	}

	public Class<?> getGrupo() {
		return grupo;
	}
	
	public static String removerFormatacao(String cpfOuCnpj) {
		return cpfOuCnpj.replaceAll("\\.|-|/","");
	}
	
}
