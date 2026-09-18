/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.client;

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
    private final HttpClient httpClient;;
    private final ObjectMapper objectMapper;
    
    public ClientService() throws IOException, InterruptedException {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }
    
    public List<ClientModel> getData() throws IOException, InterruptedException{
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
                new TypeReference<List<ClientModel>>(){});
        
    }
}
