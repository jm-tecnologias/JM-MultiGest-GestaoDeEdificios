/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import jmmultigest.building.BuildModel;
import jmmultigest.client.ClientModel;
import jmmultigest.utils.DataTransfer;
import jmmultigest.visitor.VisitorMemberModel;
import jmmultigest.visitor.VisitorModel;
import jmmultigest.visitor.VisitorService;

/**
 *
 * @author JM-Tecnologias
 */
public class VisitanteController implements Initializable{
        private BuildModel build;
        private VisitorService visitorService;
    private ObservableList<VisitorMemberModel> listaVisitantes;

    
    @FXML
    private TableView<VisitorMemberModel> visitanteTabela;
    @FXML
    private TableColumn<VisitorMemberModel, String> anfitriaoColumn;

    @FXML
    private TableColumn<VisitorMemberModel, String> dataColumn;

    @FXML
    private TableColumn<VisitorMemberModel, String> descricaoColumn;

    @FXML
    private TableColumn<VisitorMemberModel, String> estadoColumn;

    @FXML
    private TableColumn<VisitorMemberModel, String> horaColumn;

    @FXML
    private TableColumn<VisitorMemberModel, String> idColumn;

    @FXML
    private TableColumn<VisitorMemberModel, String> visitanteColumn;

    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        build = DataTransfer.getBuildingData();
        visitorService = new VisitorService();
        fetchData();
        showVisitantes();
    }
    void showVisitantes(){
        listaVisitantes = FXCollections.observableArrayList();
        visitanteTabela.setItems(listaVisitantes);
        idColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        String.valueOf(cellData.getValue().getVisitor().getId())));
        visitanteColumn.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getVisitor().getName()));
        anfitriaoColumn.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getMember().getSurname()
                        +", "+cellData.getValue().getMember().getName()));
        dataColumn.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getVisitor().getVisitor_date()));
        horaColumn.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getVisitor().getVisitor_time()));
        estadoColumn.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getVisitor().getStatus()));
        descricaoColumn.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getVisitor().getDescription()));
    }
    private void fetchData() {
        Thread thread = new Thread(() -> {
            try {
                List<VisitorMemberModel> visitorsResult = visitorService.listVisitors();

                Platform.runLater(() -> {
                    listaVisitantes.clear();
                    listaVisitantes.addAll(visitorsResult);
                });
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    System.out.println("Erro: " + e.getMessage());
                });
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
    
}
