package br.com.cotiinformatica.dtos;

import java.util.List;

public class ChatGPTResponseDTO {

	private List<ChatGPTChoiceDTO> choices;

	public ChatGPTResponseDTO() {
		// TODO Auto-generated constructor stub
	}

	public ChatGPTResponseDTO(List<ChatGPTChoiceDTO> choices) {
		super();
		this.choices = choices;
	}

	public List<ChatGPTChoiceDTO> getChoices() {
		return choices;
	}

	public void setChoices(List<ChatGPTChoiceDTO> choices) {
		this.choices = choices;
	}

}
