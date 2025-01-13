package br.com.cotiinformatica.dtos;

public class ChatGPTMessageDTO {

	private String content;

	public ChatGPTMessageDTO() {
		// TODO Auto-generated constructor stub
	}

	public ChatGPTMessageDTO(String content) {
		super();
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
