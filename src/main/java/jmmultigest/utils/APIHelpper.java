/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpClient;

/**
 *
 * @author JM-Tecnologias
 */
public class APIHelpper {
    public final static String IP = "127.0.0.1";
    public final static String PORT = "8000";
    public final static String URL = "http://"+IP+":"+PORT;
    public final static HttpClient httpClient = HttpClient.newHttpClient();
    public final static ObjectMapper objectMapper = new ObjectMapper();
    
}
