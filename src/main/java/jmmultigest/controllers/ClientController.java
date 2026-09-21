/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import jmmultigest.client.MemberModel;
import jmmultigest.client.MemberService;
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
    @FXML private TableColumn<ClientModel, String> descricaoColumnCliente;
    @FXML private TableColumn<ClientModel, Integer> membrosColumnCliente;
    @FXML private TableColumn<ClientModel, Boolean> estadoColumnCliente;
    @FXML private TableColumn<ClientModel, HBox> accoesColumnCliente;
    private ObservableList<ClientModel> listaClientes;
    private ClientService clientService;
    
    @FXML private TableView<MemberModel> tabelaMembro;
    @FXML private TableColumn<MemberModel, Integer> idColumnMembro;
    @FXML private TableColumn<MemberModel, String> docIdColumnMembro;
    @FXML private TableColumn<MemberModel, String> nomeColumnMembro;
    @FXML private TableColumn<MemberModel, String> apelidoColumnMembro;
    private ObservableList<MemberModel> listaMembros;
    private MemberService memberService;
    
    @FXML private TableView<HistoricApartmentModel> tabelaHistorico;
    @FXML private TableColumn<HistoricApartmentModel, String> apartamentoColumnHistorico;
    @FXML private TableColumn<HistoricApartmentModel, String> entradaColumnHistorico;
    @FXML private TableColumn<HistoricApartmentModel, String> saidaColumnHistorico;
    private ObservableList<HistoricApartmentModel> listaHistorico;
    private HistoricService historicoService;
    
    @FXML private TextArea descricaoHistoricotextArea;
    @FXML private Label pisoBlocoLabel;
    @FXML private Label numeroLabel;
    @FXML private Label descricaoApartamentoLabel;
    @FXML private Label tipoApartamentoLabel;
    @FXML private Label estadoLabel;
    @FXML private Label telefoneLabel;
    @FXML private Label emailLabel;
    @FXML private Label descricaoMemberLabel;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user = DataTransfer.getUserData();
        build = DataTransfer.getBuildingData();
        
        try {
            setTableWidth();
            clientService = new ClientService();
            historicoService = new HistoricService();
            memberService = new MemberService(); 
            showClientes();
            showHistoric();
            showMember();
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
    
    @FXML
    void openNovoMembro(ActionEvent e){
        LoadAndMoveUtilities.showAsPopUP(e, null);
        LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmmultigest/novo-membro.fxml");
    }
    
    void setTableWidth(){
        idColumnCliente.prefWidthProperty()
                .bind(tabelaCliente.widthProperty().multiply(0.10));
        nomeColumnCliente.prefWidthProperty()
                .bind(tabelaCliente.widthProperty().multiply(0.15));
        membrosColumnCliente.prefWidthProperty()
                .bind(tabelaCliente.widthProperty().multiply(0.10));
        estadoColumnCliente.prefWidthProperty()
                .bind(tabelaCliente.widthProperty().multiply(0.10));
        accoesColumnCliente.prefWidthProperty()
                .bind(tabelaCliente.widthProperty().multiply(0.30));
        descricaoColumnCliente.prefWidthProperty()
                .bind(tabelaCliente.widthProperty().multiply(0.25));
        
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
        descricaoColumnCliente.setCellValueFactory(new PropertyValueFactory<>("description"));
        
//        accoesColumnCliente 
    }
    
    void showHistoric(){
        listaHistorico = FXCollections.observableArrayList();
        tabelaHistorico.setItems(listaHistorico);
        apartamentoColumnHistorico.setCellValueFactory(cellData -> new SimpleStringProperty("P/B: "+cellData.getValue().getApartment().getNumber()+" Nr: "+cellData.getValue().getApartment().getNumber()));
        entradaColumnHistorico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHistoric().getOccupational_date()));
        saidaColumnHistorico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHistoric().getFinal_date()));
    }
    
    void showMember(){
        listaMembros = FXCollections.observableArrayList();
        tabelaMembro.setItems(listaMembros);
        idColumnMembro.setCellValueFactory(new PropertyValueFactory<>("id"));
        docIdColumnMembro.setCellValueFactory(new PropertyValueFactory<>("doc_id"));
        nomeColumnMembro.setCellValueFactory(new PropertyValueFactory<>("name"));
        apelidoColumnMembro.setCellValueFactory(new PropertyValueFactory<>("surname"));
        
    }
    
    @FXML
    void selecionaCliente(MouseEvent event) throws IOException, InterruptedException{
        HistoricApartmentModel model = null;
        List<HistoricApartmentModel> historicResult = historicoService.listHistoricData();
        ClientModel x = tabelaCliente.getSelectionModel().getSelectedItem();
        if(x != null){
            /*
            Setting data for member usage propuse
            */
            DataTransfer.setOtherData(x);
            
            listaMembros.clear();
            listaMembros.addAll(x.getMembers());
            /*
            pegar os dados da lista historicResult, um por um e passar para um objecto HistoricApartmentModel
            e verificar se no Historic existe um client_id semelhante ao do ClientModel x; caso exista, adicionar o 
            HistoricApartmentModel a uma nova lista que sera adicionada ao listaHistorico
            */
            List<HistoricApartmentModel> aux_list = new ArrayList<>();
            for (HistoricApartmentModel ha : historicResult) {
                if(ha.getHistoric().getClient_id() == x.getId()){
                    aux_list.add(ha);
                    model = ha;
                }
            }
            listaHistorico.clear();
            listaHistorico.addAll(aux_list);
            
            /*
            Show Address details
            */
            if(model != null){ 
                descricaoHistoricotextArea.setText(model.getHistoric().getDescription());
                descricaoApartamentoLabel.setText(model.getApartment().getDescription());
                pisoBlocoLabel.setText(model.getApartment().getFloor());
                numeroLabel.setText(model.getApartment().getNumber());
                tipoApartamentoLabel.setText(model.getApartment().getType());
                estadoLabel.setText(model.getApartment().getOccupation_state());
            }else{
                descricaoHistoricotextArea.setText(". . .");
                descricaoApartamentoLabel.setText(". . .");
                pisoBlocoLabel.setText(". . .");
                numeroLabel.setText(". . .");
                tipoApartamentoLabel.setText(". . .");
                estadoLabel.setText(". . .");
            }
            /*
            reset Client phone data
            */
            telefoneLabel.setText("...");
            emailLabel.setText("...");
            descricaoMemberLabel.setText("...");
            
        }
    }
    
    @FXML
    void selecionaHistorico(MouseEvent event){
        HistoricApartmentModel x = tabelaHistorico.getSelectionModel().getSelectedItem();
        if(x != null){
            descricaoHistoricotextArea.setText(x.getHistoric().getDescription());
            pisoBlocoLabel.setText(x.getApartment().getFloor());
            numeroLabel.setText(x.getApartment().getNumber());
            tipoApartamentoLabel.setText(x.getApartment().getType());
            estadoLabel.setText(x.getApartment().getOccupation_state());
        }
    }
    
    @FXML
    void selecionamembro(MouseEvent e){
        MemberModel x = tabelaMembro.getSelectionModel().getSelectedItem();
        if(x != null){
            telefoneLabel.setText(x.getPhone());
            emailLabel.setText(x.getEmail());
            descricaoMemberLabel.setText(x.getDescription());
        }else{
            telefoneLabel.setText("...");
            emailLabel.setText("...");
            descricaoMemberLabel.setText("...");
        }
    }
    private void fetchData() {
        Thread thread = new Thread(() -> {
            try {
                List<ClientModel> clientResult = build.getClients();
//                List<MemberModel> memberResult = build.getClients()
//                List<HistoricApartmentModel> historicResult = historicoService.listHistoricData();
                Platform.runLater(() -> {
                    listaClientes.clear();
                    listaClientes.addAll(clientResult);
                    
                    
//                    
//                    listaHistorico.clear();
//                    listaHistorico.addAll(historicResult);
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
