/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import jmmultigest.App;

/**
 *
 * @author JM-Tecnologias
 */
public class LoginController {

    @FXML
    private void switchToDashboar() throws IOException {
        App.setRoot("dashboar");
    }
}
