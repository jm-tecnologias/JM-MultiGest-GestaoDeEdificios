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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import jmmultigest.client.ClientModel;
import jmmultigest.client.ClientService;

/**
 *
 * @author JM-Tecnologias
 */
public class ClientController implements Initializable{
    @FXML private TableView<ClientModel> tabelaCliente;
    @FXML private TableColumn<ClientModel, Integer> idColumnCliente;
    @FXML private TableColumn<ClientModel, String> nomeColumnCliente;
    @FXML private TableColumn<ClientModel, Integer> membrosColumnCliente;
    @FXML private TableColumn<ClientModel, Boolean> estadoColumnCliente;
    @FXML private TableColumn<ClientModel, HBox> accoesColumnCliente;
    private ObservableList<ClientModel> listaClientes;
    private ClientService clientService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            setTableWidth();
            clientService = new ClientService();
            showClientes();
        } catch (IOException ex) {
            System.out.println("Erro (IOException) Inicializando servico Cliente: "+ex);
        } catch (InterruptedException ex) {
            System.out.println("Erro (InterruptedException) Inicializando servico Cliente: "+ex);
        }
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
    }
    
    void showClientes() throws IOException, InterruptedException{
        listaClientes =  FXCollections.observableArrayList();
        tabelaCliente.setItems(listaClientes);
        
        idColumnCliente.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeColumnCliente.setCellValueFactory(new PropertyValueFactory<>("surname"));
        membrosColumnCliente.setCellValueFactory(new PropertyValueFactory<>("num_members"));
        estadoColumnCliente.setCellValueFactory(new PropertyValueFactory<>("status"));
        fetchData();
//        accoesColumnCliente 
    }
//            listaClientes = (ObservableList<ClientModel>) clientService.getData();
    private void fetchData() {
        Thread thread = new Thread(() -> {
            try {
                List<ClientModel> result = clientService.getData();
                Platform.runLater(() -> {
                    listaClientes.clear();
                    listaClientes.addAll(result);
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
