/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jmmultigest.apartments.ApartmentModel;
import jmmultigest.auth.UserEmployeeModel;
import jmmultigest.building.BuildModel;
import jmmultigest.utils.DataTransfer;
import jmmultigest.utils.LoadAndMoveUtilities;

/**
 *
 * @author JM-Tecnologias
 */
public class NovoCliententroller implements Initializable{
    private UserEmployeeModel user;
    private BuildModel build;
    
    @FXML private TextField nomeField;
    @FXML private TextField agregadoField;
    @FXML private TextField apelidoField;
    @FXML private TextField docIDField;
    @FXML private TextField emailField;
    @FXML private TextField telefoneField;
    @FXML private TextArea clientObservacoesArea;
    @FXML private TextArea historicoObservacoesArea;
    @FXML private ComboBox<String> apartamentoCombo;
    @FXML private ComboBox<String> estadoCombo;
    @FXML private DatePicker entradaDatePicker;
    @FXML private DatePicker saidaDatePicker;
    @FXML private Button cancelarClienteButton;
    @FXML private Button guardarClienteButton;
    private Map<String, ApartmentModel> apartamentMap;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user = DataTransfer.getUserData();
        build = DataTransfer.getBuildingData();
        populateComboApartamento();
        
    }
    @FXML
    public void close(ActionEvent event) {
        if (LoadAndMoveUtilities.returnToStage()) {
            LoadAndMoveUtilities.setEstadoStage(false);
            LoadAndMoveUtilities.showStage(null, null);
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } else if (LoadAndMoveUtilities.returnToBaseAnchor()) {
            LoadAndMoveUtilities.setEstadoPopUP(false);
            LoadAndMoveUtilities.showAsPopUP(null, null);
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        }
    }
    
    void populateComboApartamento(){
        List<String> lista = new ArrayList<>();
        apartamentMap = new HashMap<>();
        for(ApartmentModel ap : build.getApartments()){
            lista.add(ap.getNumber());
            apartamentMap.put(ap.getNumber(), ap);
        }
        apartamentoCombo.setItems(FXCollections.observableArrayList(lista));
        estadoCombo.setItems(FXCollections.observableArrayList("Livre","Ocupado"));
    }
    
}
