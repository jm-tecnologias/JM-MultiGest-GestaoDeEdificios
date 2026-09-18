/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.building;
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
public class BuildingService {
    private static final String API_URL = "http://127.0.0.1:8000";
    private final HttpClient httpClient;;
    private final ObjectMapper objectMapper;
    
    public BuildingService() throws IOException, InterruptedException {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }
    
    public List<BuildModel> getBuildingData() throws IOException, InterruptedException{
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(API_URL + "/building/list-all"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        String jsonResponse = response.body();
        System.out.println("Resposta: "+jsonResponse);
        
        return objectMapper.readValue(jsonResponse,
                new TypeReference<List<BuildModel>>(){});
        
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        BuildingService service = new BuildingService();
        
        try {
            List<BuildModel> buildings = service.getBuildingData();
            for(BuildModel b: buildings){
                
                System.out.println("_________________________");
                System.out.println(b.getApartments().size());
            }
        } catch (Exception e) {
            System.err.println("Erro ao consumir API:");
            e.printStackTrace();
        } 
    }

    
}
