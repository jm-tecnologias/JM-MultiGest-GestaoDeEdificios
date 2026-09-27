/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import jmmultigest.utils.APIHelpper;
import jmmultigest.utils.ApiException;

/**
 *
 * @author JM-Tecnologias
 */
public class MemberService {

    public MemberModel create(MemberModel member) throws JsonProcessingException,
            IOException, InterruptedException, ApiException {
        String jsonBody = APIHelpper.objectMapper.writeValueAsString(member);
//        System.out.println("JSON Envioado: " + jsonBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(APIHelpper.URL + "/building/clients/members/register"))
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
        return APIHelpper.objectMapper.readValue(jsonResponse, MemberModel.class);
    }
    
    public MemberModel update(int id, MemberModel member) throws JsonProcessingException, IOException, InterruptedException, ApiException {
        String jsonBody = APIHelpper.objectMapper.writeValueAsString(member);
//        System.out.println("JSON Envioado: " + jsonBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(APIHelpper.URL + "/building/clients/members/update/" + id))
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
        return APIHelpper.objectMapper.readValue(jsonResponse, MemberModel.class);
    }

//    public static void main(String[] args) throws IOException, InterruptedException, ApiException {
//        MemberService service = new MemberService();
//        MemberModel client = new MemberModel();
//        client.setUser_id(1);
//        client.setClient_id(2);
//        client.setDoc_id("100110682835M");
//        client.setApartment_id(2);
//        client.setSurname("Cossa");
//        client.setName("Rosa");
//        client.setPhone("848484849");
//        client.setClient_relationship("Filho");
//        client.setEmail("rosa.mamuel@mail.com");
//        client.setDescription("Filha do membro principal, maior de idade com possivilidade de requerer uma conta de cliente");
//        service.create(client);
//    }
}
