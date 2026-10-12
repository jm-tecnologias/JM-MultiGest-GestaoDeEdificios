/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import jmmultigest.auth.LoginTokenModel;
import jmmultigest.auth.UserEmployeeModel;
import jmmultigest.utils.DataTransfer;

/**
 *
 * @author JM-Tecnologias
 */
public class DashbordController implements Initializable{
    private LoginTokenModel user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user = DataTransfer.getUserData();
//        try {
//        } catch (IOException ex) {
//            System.out.println("Erro (IOException) ao inicializar servico Building no dashboar "+ex);
//        } catch (InterruptedException ex) {
//            System.out.println("Erro (InterruptedException) ao inicializar servico Building no dashboar "+ex);
//        }
    }
    
}
