/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import jmmultigest.utils.ApiException;

/**
 *
 * @author JM-Tecnologias
 */
public class UserService {

    private static final String API_URL = "http://127.0.0.1:8000";
    private final HttpClient httpClient;
    ;
    private final ObjectMapper objectMapper;

    public UserService() throws IOException, InterruptedException {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public UserEmployeeModel login(UserModel user) throws IOException, InterruptedException, ApiException {
        String jsonBody = objectMapper.writeValueAsString(user);
//        System.out.println("JSON Enviado:");
//        System.out.println(jsonBody);

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(API_URL + "/building/auth/employee/login"))
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
                HttpResponse.BodyHandlers.ofString()
        );
        String jsonResponse = response.body();
//        System.out.println("Resposta: " + jsonResponse);
        if (response.statusCode() != 200) {
            String message = "Erro desconhecido.";
            try {
                JsonNode errorJson
                        = objectMapper.readTree(response.body());
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
        return objectMapper.readValue(jsonResponse, UserEmployeeModel.class);
    }

//    public static void main(String[] args) throws IOException, InterruptedException {
//        UserService service = new UserService();
//        
//        UserModel user = new UserModel();
//        user.setPassword("Informatico@12");
//        user.setUsername("Admin");
//        
//        try {
//            UserEmployeeModel x = service.login(user);
//            System.out.println(x.getEmployee().getBuilding_id());
//           
//        } catch (Exception e) {
//            System.err.println("Erro ao consumir API:");
//            e.printStackTrace();
//        } 
//    }
}
