package br.com.cotiinformatica;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OpenAIGenerateImage {

	public static void main(String[] args) {
		
		//registrando o ENDPOINT e a chave de autenticação
		String apiUrl = "https://api.openai.com/v1/images/generations";
		String apiKey = "your key here";
		
		try (var httpClient  = HttpClients.createDefault()) {
			
			var scanner = new Scanner(System.in);
			
			System.out.print("Descreva a imagem desejada: ");
			var descricao = scanner.nextLine();
			
			//criando a requisição para a open do OpenAI
			Map<String, Object> requestBody = new HashMap<>();
			requestBody.put("prompt", descricao); //texto que descreve a imagem
			requestBody.put("n", 1); //define a quantidade de imagens que serão geradas
			requestBody.put("size", "512x512"); //tamanho da imagem (largura e altura)
			
			//configurando a chamada para a API
			var request = new HttpPost(apiUrl);
			request.setHeader("Authorization", "Bearer " + apiKey);
			request.setHeader("Content-Type", "application/json");
			
			//serializando / convertendo os dados em JSON
			var mapper = new ObjectMapper();
			var jsonRequest = mapper.writeValueAsString(requestBody);
			request.setEntity(new StringEntity(jsonRequest));
			
			//enviando a requisição
			var response = EntityUtils.toString(httpClient.execute(request).getEntity());
			
			//imprimindo a resposta da API
			System.out.println(response);
			
			scanner.close();
		}
		catch(Exception e) {
			System.out.println("Erro: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
