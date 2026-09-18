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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author JM-Tecnologias
 */
public class MainController implements Initializable {

    @FXML
    private AnchorPane mainViewer;
    @FXML
    private Button btn_dashboard;
    @FXML
    private Button btn_seguranca;
    @FXML
    private Button btn_visitante;
    @FXML
    private Button btn_cliente;
    @FXML
    private Button btn_apartamento;

    @FXML
    public void switchForm(ActionEvent event) throws IOException {
        String path = "";
        if (event.getSource() == btn_dashboard) {

            path = "/jmmultigest/dashboard.fxml";
        } else if (event.getSource() == btn_seguranca) {
            path = "/jmmultigest/portaria-seguranca.fxml";
        } else if (event.getSource() == btn_cliente) {
            path = "/jmmultigest/client-member.fxml";
        } else if (event.getSource() == btn_visitante) {
            path = "/jmmultigest/visitante.fxml";
        } else if (event.getSource() == btn_apartamento) {
            path = "/jmmultigest/apartamento.fxml";
        }
        AnchorPane pane = FXMLLoader.load(getClass().getResource(path));
        mainViewer.getChildren().setAll(pane);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
