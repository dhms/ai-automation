package br.com.cotiinformatica.dtos;

import java.util.List;

public class AgendaRequestDto {

	private String dataInicio;
	private String dataFim;
	private String descricao;
	private List<TarefaRequestDto> tarefas;

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getDataFim() {
		return dataFim;
	}

	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<TarefaRequestDto> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<TarefaRequestDto> tarefas) {
		this.tarefas = tarefas;
	}

}
