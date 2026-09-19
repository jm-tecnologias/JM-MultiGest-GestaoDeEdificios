/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *
 * @author JM-Tecnologias
 */
public class MemberService {
    private static final String API_URL = "http://127.0.0.1:8000";
    private final HttpClient httpClient;
    ;
    private final ObjectMapper objectMapper;

    public MemberService() throws IOException, InterruptedException {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }
    
    public MemberModel create(MemberModel member) throws JsonProcessingException, IOException, InterruptedException {
        String jsonBody = objectMapper.writeValueAsString(member);
        System.out.println("JSON Envioado: " + jsonBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/building/clients/members/register"))
                .header(
                        "Content-Type",
                        "application/json"
                )
                .header(
                        "Accept",
                        "application/json"
                )
                .POST(
                        HttpRequest.BodyPublishers
                                .ofString(jsonBody)
                )
                .build();
        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString());

        String jsonResponse = response.body();
//        System.out.println("Resposta: " + jsonResponse);

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new RuntimeException(
                    "HTTP ERRO: "
                    + response.statusCode()
                    + " - "
                    + response.body()
            );
        }
        return objectMapper.readValue(jsonBody, MemberModel.class);
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        MemberService service = new MemberService();
        MemberModel client = new MemberModel();
        client.setUser_id(1);
        client.setClient_id(2);
        client.setDoc_id("100110682835M");
        client.setApartment_id(2);
        client.setSurname("Cossa");
        client.setName("Rosa");
        client.setPhone("848484849");
        client.setEmail("rosa.mamuel@mail.com");
        client.setDescription("Filha do membro principal, maior de idade com possivilidade de requerer uma conta de cliente");
        service.create(client);
    }
}
