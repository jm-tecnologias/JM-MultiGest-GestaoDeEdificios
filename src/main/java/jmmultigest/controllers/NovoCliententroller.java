/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import jmmultigest.auth.UserEmployeeModel;
import jmmultigest.building.BuildModel;
import jmmultigest.utils.DataTransfer;

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
    @FXML private ComboBox<?> apartamentoCombo;
    @FXML private ComboBox<?> estadoCombo;
    @FXML private DatePicker entradaDatePicker;
    @FXML private DatePicker saidaDatePicker;
    @FXML private Button cancelarClienteButton;
    @FXML private Button guardarClienteButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user = DataTransfer.getUserData();
        build = DataTransfer.getBuildingData();
        
        
    }
    
}
