/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author JM-Tecnologias
 */
public class PortariaController implements Initializable{
    
    /*
    *
    * 
    */  
    @FXML private TextField nomeFieldMembro;
    @FXML private TextField telefoneFieldMembro;
    @FXML private TextField emailFieldMembro;
    @FXML private Label estadoLabelMembro;

    @FXML private TextField nomeFieldVisitante;
    @FXML private TextField dataFieldVisitante;
    @FXML private TextField horaFieldVisitante;
    @FXML private TextField telefoneFieldVisitante;
    @FXML private Label estadoLabelVisitante;
    
    @FXML private TextArea notasField;
    
    /*
    *
    * 
    */ 
    @FXML private TextField scanField;
    
    
    /*
    *
    * 
    */ 
    @FXML private TextField pisoBlocoField;
    @FXML private TextField numeroField;
    @FXML private TextArea descricaoField;
    
    /*
    *
    * 
    */ 
    @FXML private TableView<?> membroTabela;
    @FXML private TableColumn<?, ?> estadoColumnMembro;
    @FXML private TableColumn<?, ?> notasColumnMembro;

    /*
    *
    * 
    */ 
    @FXML private TableView<?> visitanteTabela;
    @FXML private TableColumn<?, ?> estadoColumnVisitante;
    @FXML private TableColumn<?, ?> notasColumnVisitante;
    
    /*
    *
    * 
    */ 
    @FXML private TextField notaHistoricoField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
}
