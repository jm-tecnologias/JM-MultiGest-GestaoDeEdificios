/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.historic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import jmmultigest.client.MemberModel;
import jmmultigest.client.MemberService;

/**
 *
 * @author JM-Tecnologias
 */
public class HistoricService {
    private static final String API_URL = "http://127.0.0.1:8000";
    private final HttpClient httpClient;;
    private final ObjectMapper objectMapper;
    
    public HistoricService() throws IOException, InterruptedException {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }
    
    public List<HistoricApartmentModel> listHistoricData() throws IOException, InterruptedException{
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(API_URL + "/building/client/historic/list-all"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        String jsonResponse = response.body();
//        System.out.println("Resposta: "+jsonResponse);
        return objectMapper.readValue(jsonResponse,
                new TypeReference<List<HistoricApartmentModel>>(){});
        
    }
    
    public HistoricModel create(HistoricModel model) throws JsonProcessingException, IOException, InterruptedException {
        String jsonBody = objectMapper.writeValueAsString(model);
        System.out.println("JSON Envioado: " + jsonBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/building/client/historic/register"))
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
        System.out.println("Resposta: " + jsonResponse);

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new RuntimeException(
                    "HTTP ERRO: "
                    + response.statusCode()
                    + " - "
                    + response.body()
            );
        }
        return objectMapper.readValue(jsonBody, HistoricModel.class);
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        HistoricService service = new HistoricService();
        HistoricModel x = new HistoricModel();
        
        try {
            List<HistoricApartmentModel> k = service.listHistoricData();
            for(HistoricApartmentModel b: k){
                
                System.out.println("_________________________");
                System.out.println(b);
            }
        } catch (Exception e) {
            System.err.println("Erro ao consumir API:");
            e.printStackTrace();
        }
    }
}
