/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 *
 * @author JM-Tecnologias
 */
public class ClientService {

    private static final String API_URL = "http://127.0.0.1:8000";
    private final HttpClient httpClient;
    ;
    private final ObjectMapper objectMapper;

    public ClientService() throws IOException, InterruptedException {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public List<ClientModel> getData() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(API_URL + "/building/clients/list-all"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        String jsonResponse = response.body();
//        System.out.println("Resposta: "+jsonResponse);

        return objectMapper.readValue(jsonResponse,
                new TypeReference<List<ClientModel>>() {
        });

    }

    public ClientModel create(ClientModel client) throws JsonProcessingException, IOException, InterruptedException {
        String jsonBody = objectMapper.writeValueAsString(client);
        System.out.println("JSON Envioado: " + jsonBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/building/clients/register"))
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
        return objectMapper.readValue(jsonBody, ClientModel.class);
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        ClientService service = new ClientService();
        ClientModel client = new ClientModel();
        client.setUser_id(1);
        client.setSurname("Cossa, Manuel");
        client.setNum_members(5);
        client.setDescription("Membro Principal");
        client.setBuilding_id(1);
        service.create(client);
    }
}
