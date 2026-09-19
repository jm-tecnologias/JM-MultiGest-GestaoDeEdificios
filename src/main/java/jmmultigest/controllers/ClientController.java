/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import jmmultigest.auth.UserEmployeeModel;
import jmmultigest.building.BuildModel;
import jmmultigest.client.ClientModel;
import jmmultigest.client.ClientService;
import jmmultigest.historic.HistoricApartmentModel;
import jmmultigest.historic.HistoricService;
import jmmultigest.utils.DataTransfer;
import jmmultigest.utils.LoadAndMoveUtilities;

/**
 *
 * @author JM-Tecnologias
 */
public class ClientController implements Initializable{
    private UserEmployeeModel user;
    private BuildModel build;
    
    @FXML private TableView<ClientModel> tabelaCliente;
    @FXML private TableColumn<ClientModel, Integer> idColumnCliente;
    @FXML private TableColumn<ClientModel, String> nomeColumnCliente;
    @FXML private TableColumn<ClientModel, Integer> membrosColumnCliente;
    @FXML private TableColumn<ClientModel, Boolean> estadoColumnCliente;
    @FXML private TableColumn<ClientModel, HBox> accoesColumnCliente;
    private ObservableList<ClientModel> listaClientes;
    private ClientService clientService;
    
    @FXML private TableView<HistoricApartmentModel> tabelaHistorico;
    @FXML private TableColumn<HistoricApartmentModel, String> apartamentoColumnHistorico;
    @FXML private TableColumn<HistoricApartmentModel, String> entradaColumnHistorico;
    @FXML private TableColumn<HistoricApartmentModel, String> saidaColumnHistorico;
    private ObservableList<HistoricApartmentModel> listaHistorico;
    private HistoricService historicoService;
    
    @FXML private TextArea textAreaDescricao;
    @FXML private Label pisoBlocoLabel;
    @FXML private Label numeroLabel;
    @FXML private Label descricaoLabel;
    @FXML private Label tipoApartamentoLabel;
    @FXML private Label estadoLabel;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user = DataTransfer.getUserData();
        build = DataTransfer.getBuildingData();
        
        try {
            setTableWidth();
            clientService = new ClientService();
            historicoService = new HistoricService();
            showClientes();
            showHistoric();
            fetchData();
        } catch (IOException ex) {
            System.out.println("Erro (IOException) Inicializando servico Cliente: "+ex);
        } catch (InterruptedException ex) {
            System.out.println("Erro (InterruptedException) Inicializando servico Cliente: "+ex);
        }
    }
    
    @FXML
    void openNovoCliente(ActionEvent e){
        LoadAndMoveUtilities.showAsPopUP(e, null);
        LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmmultigest/novo-cliente.fxml");
    }
    
    void setTableWidth(){
        idColumnCliente.prefWidthProperty()
                .bind(tabelaCliente.widthProperty().multiply(0.10));
        nomeColumnCliente.prefWidthProperty()
                .bind(tabelaCliente.widthProperty().multiply(0.30));
        membrosColumnCliente.prefWidthProperty()
                .bind(tabelaCliente.widthProperty().multiply(0.15));
        estadoColumnCliente.prefWidthProperty()
                .bind(tabelaCliente.widthProperty().multiply(0.15));
        accoesColumnCliente.prefWidthProperty()
                .bind(tabelaCliente.widthProperty().multiply(0.30));
        
        apartamentoColumnHistorico.prefWidthProperty()
                .bind(tabelaHistorico.widthProperty().multiply(0.50));
        entradaColumnHistorico.prefWidthProperty()
                .bind(tabelaHistorico.widthProperty().multiply(0.25));
        saidaColumnHistorico.prefWidthProperty()
                .bind(tabelaHistorico.widthProperty().multiply(0.25));
    }
    
    void showClientes() throws IOException, InterruptedException{
        listaClientes =  FXCollections.observableArrayList();
        tabelaCliente.setItems(listaClientes);
        
        idColumnCliente.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeColumnCliente.setCellValueFactory(new PropertyValueFactory<>("surname"));
        membrosColumnCliente.setCellValueFactory(new PropertyValueFactory<>("num_members"));
        estadoColumnCliente.setCellValueFactory(new PropertyValueFactory<>("status"));
//        accoesColumnCliente 
    }
    
    void showHistoric(){
        listaHistorico = FXCollections.observableArrayList();
        tabelaHistorico.setItems(listaHistorico);
        apartamentoColumnHistorico.setCellValueFactory(cellData -> new SimpleStringProperty("P/B: "+cellData.getValue().getApartment().getNumber()+" Nr: "+cellData.getValue().getApartment().getNumber()));
        entradaColumnHistorico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHistoric().getOccupational_date()));
        saidaColumnHistorico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHistoric().getFinal_date()));
    }
    
    @FXML
    void selecionaCliente(MouseEvent event){
        ClientModel x = tabelaCliente.getSelectionModel().getSelectedItem();
        if(x != null){
            textAreaDescricao.setText(x.getDescription());
        }
    }
    
    @FXML
    void selecionaHistorico(MouseEvent event){
        HistoricApartmentModel x = tabelaHistorico.getSelectionModel().getSelectedItem();
        if(x != null){
            textAreaDescricao.setText(x.getHistoric().getDescription());
            pisoBlocoLabel.setText(x.getApartment().getFloor());
            numeroLabel.setText(x.getApartment().getNumber());
            descricaoLabel.setText(x.getApartment().getDescription());
            tipoApartamentoLabel.setText(x.getApartment().getType());
            estadoLabel.setText(x.getApartment().getOccupation_state());
        }
    }
    private void fetchData() {
        Thread thread = new Thread(() -> {
            try {
                List<ClientModel> clientResult = clientService.getData();
                List<HistoricApartmentModel> historicResult = historicoService.listHistoricData();
                Platform.runLater(() -> {
                    listaClientes.clear();
                    listaClientes.addAll(clientResult);
                    
                    listaHistorico.clear();
                    listaHistorico.addAll(historicResult);
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
