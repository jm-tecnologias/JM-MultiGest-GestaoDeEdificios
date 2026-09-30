/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.visitor;

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
public class VisitorService {
    
    public VisitorMemberApartmentModel getVisitorData(int visitor_id) throws IOException, InterruptedException, ApiException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(APIHelpper.URL + "/building/visitors/get/" + visitor_id))
                .GET()
                .build();
        HttpResponse<String> response = APIHelpper.httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        String jsonResponse = response.body();
//        System.out.println("Resposta: "+jsonResponse);
        if (response.statusCode() != 200) {
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

        return APIHelpper.objectMapper.readValue(jsonResponse, VisitorMemberApartmentModel.class);
    }
}
