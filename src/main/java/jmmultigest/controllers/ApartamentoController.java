/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import jmmultigest.apartments.ApartmentModel;
import jmmultigest.auth.LoginTokenModel;
import jmmultigest.auth.UserEmployeeModel;
import jmmultigest.building.BuildModel;
import jmmultigest.utils.DataTransfer;
import jmmultigest.utils.LoadAndMoveUtilities;

/**
 *
 * @author JM-Tecnologias
 */

public class ApartamentoController implements Initializable{
    private LoginTokenModel user;
    private BuildModel build;
    
    @FXML private TableView<ApartmentModel> apartamentoTabela; 
    @FXML private TableColumn<ApartmentModel, HBox> acoesColumn;
    @FXML private TableColumn<ApartmentModel, String> andarColumn;
    @FXML private TableColumn<ApartmentModel, String> descricaoColumn;
    @FXML private TableColumn<ApartmentModel, String> estadoColumn;
    @FXML private TableColumn<ApartmentModel, Integer> idColumn;
    @FXML private TableColumn<ApartmentModel, String> numeroColumn;
    @FXML private TableColumn<ApartmentModel, String> tipoColumn;
    @FXML private TextField filterField;
    
    private ObservableList<ApartmentModel> listaApartamentos;

    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user = DataTransfer.getUserData();
        build = DataTransfer.getBuildingData();
        
         showApartments();
         fetchData();
    }
    
    @FXML
    public void getView(ActionEvent e) {
        String path = "/jmmultigest/novo-apartamento.fxml";
        LoadAndMoveUtilities.showAsPopUP(e, null);
        LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, path);
    }
    
    void setTableWidth(){
        idColumn.prefWidthProperty()
                .bind(apartamentoTabela.widthProperty().multiply(0.05));
    }
    
    void showApartments() {
        listaApartamentos =  FXCollections.observableArrayList();
        apartamentoTabela.setItems(listaApartamentos);
        
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        andarColumn.setCellValueFactory(new PropertyValueFactory<>("floor"));
        numeroColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
//        estadoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isStatus() == true ? "Activo" : "Desactivo"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("occupation_state"));
        descricaoColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        acoesColumn.setCellFactory(column -> new TableCell<ApartmentModel, HBox>(){
            private final Button btnEditar = new Button("[ ✎ ]");
            private final Button btnEliminar = new Button("[ 🗑 ]");
            private final HBox container = new HBox(5);
            
            {
                container.getChildren().addAll(
                        btnEditar,
                        btnEliminar
                );
                
                btnEditar.setOnAction(e -> {
                    ApartmentModel x = getTableView()
                            .getItems().get(getIndex());
                    editarApartamento(e, x);
                });
                
                btnEliminar.setOnAction(e -> {
                    ApartmentModel x = getTableView()
                            .getItems().get(getIndex());
                    // Funcao para desactivar 
                });
            }
            @Override
            protected void updateItem(HBox item, boolean empty){
                super.updateItem(item, empty);
                if(empty){
                    setGraphic(null);
                }else{
                    setGraphic(container);
                }
            }
        });
    }
    
    void editarApartamento(ActionEvent e, ApartmentModel model){
        if(model == null){
            return;
        }
        DataTransfer.setOtherData(model);
        LoadAndMoveUtilities.showAsPopUP(e, null);
        LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmmultigest/novo-apartamento.fxml");
    }
    
    private void fetchData() {
        Thread thread = new Thread(() -> {
            try {
                List<ApartmentModel> apartmentResult = build.getApartments();
                Platform.runLater(() -> {
                    listaApartamentos.clear();
                    listaApartamentos.addAll(apartmentResult);
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
