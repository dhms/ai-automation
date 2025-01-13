package br.com.cotiinformatica.dtos;

public class ChatGPTChoiceDTO {

	private ChatGPTMessageDTO message;

	public ChatGPTChoiceDTO() {
		// TODO Auto-generated constructor stub
	}

	public ChatGPTChoiceDTO(ChatGPTMessageDTO message) {
		super();
		this.message = message;
	}

	public ChatGPTMessageDTO getMessage() {
		return message;
	}

	public void setMessage(ChatGPTMessageDTO message) {
		this.message = message;
	}

}
