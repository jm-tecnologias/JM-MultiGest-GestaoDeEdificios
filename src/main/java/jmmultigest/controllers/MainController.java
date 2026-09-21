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
import jmmultigest.auth.UserEmployeeModel;
import jmmultigest.building.BuildModel;
import jmmultigest.building.BuildingService;
import jmmultigest.utils.ApiException;
import jmmultigest.utils.DataTransfer;
import jmmultigest.utils.ErroUtil;

/**
 *
 * @author JM-Tecnologias
 */
public class MainController implements Initializable {

    private BuildingService buildService;
    private UserEmployeeModel user;
    private BuildModel build;

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

    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user = DataTransfer.getUserData();
        try {
            buildService = new BuildingService();
        } catch (IOException ex) {
            ErroUtil.showError("Erro de comunicação",
                    "Não foi possível comunicar com a API.");
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            ErroUtil.showError("Operação interrompida",
                    "A comunicação com o servidor foi interrompida.");
        }
    }
    
    
    @FXML
    public void switchForm(ActionEvent event) throws IOException {
        loadBuildData();
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

    void loadBuildData() {
        try {
            build = buildService.getBuildingData(user.getEmployee().getBuilding_id());
            DataTransfer.setBuildingData(build);
        } catch (ApiException ex) {
            ErroUtil.showError("Erro ao carregar dados do edificio", ex.getMessage());
        } catch (IOException ex) {
            ErroUtil.showError("Erro de comunicação",
                    "Não foi possível comunicar com a API.");
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            ErroUtil.showError("Operação interrompida",
                    "A comunicação com o servidor foi interrompida.");
        }
    }

}
