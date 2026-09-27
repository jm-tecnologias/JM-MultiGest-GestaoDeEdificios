/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import jmmultigest.utils.APIHelpper;
import jmmultigest.utils.ApiException;

/**
 *
 * @author JM-Tecnologias
 */
public class ClientService {

    public List<ClientModel> getData() throws IOException, InterruptedException, ApiException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(APIHelpper.URL + "/building/clients/list-all"))
                .GET()
                .build();
        HttpResponse<String> response = APIHelpper.httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        String jsonResponse = response.body();
//        System.out.println("Resposta: "+jsonResponse);
        if (response.statusCode() != 200 && response.statusCode() != 201) {
            String message = "Erro desconhecido.";
            try {
                JsonNode errorJson
                        = APIHelpper.objectMapper.readTree(response.body());
                if (errorJson.has("detail")) {
                    message = errorJson.get("detail").asText();
                }
            } catch (Exception ex) {
                message = response.body();
            }
            throw new ApiException(
                    response.statusCode(),
                    message
            );
        }

        return APIHelpper.objectMapper.readValue(jsonResponse,
                new TypeReference<List<ClientModel>>() {
        });

    }

    public ClientModel create(ClientModel client) throws JsonProcessingException, IOException, InterruptedException, ApiException {
        String jsonBody = APIHelpper.objectMapper.writeValueAsString(client);
//        System.out.println("JSON Envioado: " + jsonBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(APIHelpper.URL + "/building/clients/register"))
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
        HttpResponse<String> response = APIHelpper.httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString());

        String jsonResponse = response.body();
//        System.out.println("Resposta: " + jsonResponse);

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            String message = "Erro desconhecido.";
            try {
                JsonNode errorJson
                        = APIHelpper.objectMapper.readTree(response.body());
                if (errorJson.has("detail")) {
                    message = errorJson.get("detail").asText();
                }
            } catch (Exception ex) {
                message = response.body();
            }
            throw new ApiException(
                    response.statusCode(),
                    message
            );
        }
        return APIHelpper.objectMapper.readValue(jsonResponse, ClientModel.class);
    }

    public ClientModel update(int id, ClientModel client) throws JsonProcessingException, IOException, InterruptedException, ApiException {
        String jsonBody = APIHelpper.objectMapper.writeValueAsString(client);
//        System.out.println("JSON Envioado: " + jsonBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(APIHelpper.URL + "/building/clients/update/" + id))
                .header(
                        "Content-Type",
                        "application/json"
                )
                .header(
                        "Accept",
                        "application/json"
                )
                .method("PATCH", HttpRequest.BodyPublishers
                        .ofString(jsonBody))
                .build();
        HttpResponse<String> response = APIHelpper.httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString());

        String jsonResponse = response.body();
//        System.out.println("Resposta: " + jsonResponse);

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            String message = "Erro desconhecido.";
            try {
                JsonNode errorJson
                        = APIHelpper.objectMapper.readTree(response.body());
                if (errorJson.has("detail")) {
                    message = errorJson.get("detail").asText();
                }
            } catch (Exception ex) {
                message = response.body();
            }
            throw new ApiException(
                    response.statusCode(),
                    message
            );
        }
        return APIHelpper.objectMapper.readValue(jsonResponse, ClientModel.class);
    }

//    public static void main(String[] args) throws IOException, InterruptedException, JsonProcessingException, ApiException {
//        ClientService service = new ClientService();
//        ClientModel client = new ClientModel();
//        client.setSurname("Mula, Amilcar");
//        client.setNum_members(3);
//        client.setDescription("Teste de actualizacao");
//        
//        client.setUser_id(1);
//        client.setBuilding_id(1);
//        client.setStatus(true);
//        service.update(1, client);
//    }
}
