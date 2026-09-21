/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import jmmultigest.App;
import jmmultigest.auth.UserEmployeeModel;
import jmmultigest.auth.UserModel;
import jmmultigest.auth.UserService;
import jmmultigest.utils.ApiException;
import jmmultigest.utils.DataTransfer;
import jmmultigest.utils.ErroUtil;

/**
 *
 * @author JM-Tecnologias
 */
public class LoginController implements Initializable{
    
    @FXML private PasswordField passwordPasswordField;
    @FXML private TextField usernameTextField;
    
    private UserService userService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            userService = new UserService();
        } catch (IOException ex) {
            System.out.println("Erro (IOException) inicializando servico de user"+ ex);
        } catch (InterruptedException ex) {
            System.out.println("Erro (InterruptedException) inicializando servico de user"+ ex);
        }
    }
    
    @FXML
    void login(ActionEvent event){
        UserModel user = new UserModel();
        user.setUsername(usernameTextField.getText());
        user.setPassword(passwordPasswordField.getText());
        try{
            UserEmployeeModel x = userService.login(user);
            if( x != null){
                DataTransfer.setUserData(x);
                App.setRoot("main");
            } 
        } catch (ApiException ex) {
            ErroUtil.showError("Não foi possível Logar no sistema",ex.getMessage());
        } catch (IOException ex) {
            ErroUtil.showError(
                "Erro de comunicação",
                "Não foi possível comunicar com a API.");
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            ErroUtil.showError(
                "Operação interrompida",
                "A comunicação com o servidor foi interrompida.");
        }      
    }
}
