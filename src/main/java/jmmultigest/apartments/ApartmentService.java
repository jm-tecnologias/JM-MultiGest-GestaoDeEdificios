/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.apartments;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import jmmultigest.utils.APIHelpper;
import jmmultigest.utils.ApiException;

/**
 *
 * @author JM-Tecnologias
 */
public class ApartmentService {
    
    public ApartmentModel create(ApartmentModel model) throws JsonProcessingException,
            IOException, InterruptedException, ApiException {
        String jsonBody = APIHelpper.objectMapper.writeValueAsString(model);
//        System.out.println("JSON Envioado: " + jsonBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(APIHelpper.URL + "/building/apartment/register"))
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
        return APIHelpper.objectMapper.readValue(jsonResponse, ApartmentModel.class);
    }
    
    public ApartmentModel update(int id, ApartmentModel model) throws JsonProcessingException, IOException, InterruptedException, ApiException {
        String jsonBody = APIHelpper.objectMapper.writeValueAsString(model);
//        System.out.println("JSON Envioado: " + jsonBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(APIHelpper.URL + "/building/apartment/update/" + id))
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
        return APIHelpper.objectMapper.readValue(jsonResponse, ApartmentModel.class);
    }
}
