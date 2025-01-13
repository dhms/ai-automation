package br.com.cotiinformatica.dtos;

public class TarefaRequestDto {

	private String nome;
	private String urgencia;
	private Integer tempoEmHoras;
	private String complexidade;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUrgencia() {
		return urgencia;
	}

	public void setUrgencia(String urgencia) {
		this.urgencia = urgencia;
	}

	public Integer getTempoEmHoras() {
		return tempoEmHoras;
	}

	public void setTempoEmHoras(Integer tempoEmHoras) {
		this.tempoEmHoras = tempoEmHoras;
	}

	public String getComplexidade() {
		return complexidade;
	}

	public void setComplexidade(String complexidade) {
		this.complexidade = complexidade;
	}

}
