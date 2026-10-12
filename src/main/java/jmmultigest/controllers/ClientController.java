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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import jmmultigest.auth.LoginTokenModel;
import jmmultigest.auth.UserEmployeeModel;
import jmmultigest.building.BuildModel;
import jmmultigest.client.ClientModel;
import jmmultigest.client.ClientService;
import jmmultigest.member.MemberModel;
import jmmultigest.member.MemberService;
import jmmultigest.historic.HistoricApartmentModel;
import jmmultigest.historic.HistoricService;
import jmmultigest.utils.DataTransfer;
import jmmultigest.utils.LoadAndMoveUtilities;

/**
 *
 * @author JM-Tecnologias
 */
public class ClientController implements Initializable{
    private LoginTokenModel user;
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
    
    private Object[] client_member;
    
    
    @FXML private TableView<HistoricApartmentModel> tabelaHistorico;
    @FXML private TableColumn<HistoricApartmentModel, String> apartamentoColumnHistorico;
    @FXML private TableColumn<HistoricApartmentModel, String> entradaColumnHistorico;
    @FXML private TableColumn<HistoricApartmentModel, String> saidaColumnHistorico;
    private ObservableList<HistoricApartmentModel> listaHistorico;
    private HistoricService historicoService;
    
    
    @FXML private TextArea descricaoHistoricotextArea;
    @FXML private TextArea descricaoMemberTextArea;
    @FXML private TextField pisoField;
    @FXML private TextField numeroField;
    @FXML private TextArea descricaoApartamentoTextArea;
    @FXML private TextField tipoField;
    @FXML private TextField contactField;
    @FXML private TextField emailField;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user = DataTransfer.getUserData();
        build = DataTransfer.getBuildingData();
        client_member = new Object[2];
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
                .bind(tabelaCliente.widthProperty().multiply(0.05));
        nomeColumnCliente.prefWidthProperty()
                .bind(tabelaCliente.widthProperty().multiply(0.25));
        membrosColumnCliente.prefWidthProperty()
                .bind(tabelaCliente.widthProperty().multiply(0.15));
        estadoColumnCliente.prefWidthProperty()
                .bind(tabelaCliente.widthProperty().multiply(0.10));
        descricaoColumnCliente.prefWidthProperty()
                .bind(tabelaCliente.widthProperty().multiply(0.25));
        accoesColumnCliente.prefWidthProperty()
                .bind(tabelaCliente.widthProperty().multiply(0.20));
        
        
        idColumnMembro.prefWidthProperty()
                .bind(tabelaMembro.widthProperty().multiply(0.10));
        docIdColumnMembro.prefWidthProperty()
                .bind(tabelaMembro.widthProperty().multiply(0.30));
        nomeColumnMembro.prefWidthProperty()
                .bind(tabelaMembro.widthProperty().multiply(0.30));
        apelidoColumnMembro.prefWidthProperty()
                .bind(tabelaMembro.widthProperty().multiply(0.30));
        
        
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
        accoesColumnCliente.setCellFactory(column -> new TableCell<ClientModel, HBox>(){
            private final Button btnEditar = new Button("[ ✎ ]");
            private final Button btnEliminar = new Button("[ 🗑 ]");
            private final HBox container = new HBox(5);
            
            {
                container.getChildren().addAll(
                        btnEditar,
                        btnEliminar
                );
                
                btnEditar.setOnAction(e -> {
                    ClientModel x = getTableView()
                            .getItems().get(getIndex());
                    editarClient(e, x);
                });
                
                btnEliminar.setOnAction(e -> {
                    ClientModel x = getTableView()
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
    
    void editarClient(ActionEvent e, ClientModel client){
        if(client == null){
            return;
        }
        DataTransfer.setOtherData(client);
        LoadAndMoveUtilities.showAsPopUP(e, null);
        LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmmultigest/novo-cliente.fxml");
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
            client_member[0] = x;
            
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
                descricaoApartamentoTextArea.setText(model.getApartment().getDescription());
                pisoField.setText(model.getApartment().getFloor());
                numeroField.setText(model.getApartment().getNumber());
                tipoField.setText(model.getApartment().getType());
            }else{
                descricaoHistoricotextArea.clear();
                descricaoHistoricotextArea.setPromptText("Descrição...");
                descricaoApartamentoTextArea.clear();
                descricaoApartamentoTextArea.setPromptText("Descrição...");
                pisoField.clear();
                pisoField.setPromptText("Piso/Bloco");
                numeroField.clear();
                numeroField.setPromptText("Nr. AP");
                tipoField.clear();
                tipoField.setPromptText("Tipo de apartamento");
            }
            /*
            reset Client phone data
            */
            contactField.clear();
            emailField.clear();
            descricaoMemberTextArea.clear();
            contactField.setPromptText("Contacto...");
            emailField.setPromptText("E-mail...");
            descricaoMemberTextArea.setPromptText("Descrição...");
            
        }
    }
    
    @FXML
    void selecionaHistorico(MouseEvent event){
        HistoricApartmentModel x = tabelaHistorico.getSelectionModel().getSelectedItem();
        if(x != null){
            descricaoHistoricotextArea.setText(x.getHistoric().getDescription());
            pisoField.setText(x.getApartment().getFloor());
            numeroField.setText(x.getApartment().getNumber());
            tipoField.setText(x.getApartment().getType());
        }
    }
    
    @FXML
    void selecionamembro(MouseEvent e){
        MemberModel x = tabelaMembro.getSelectionModel().getSelectedItem();
        if(x != null){
            client_member[1] = x;
            DataTransfer.setOtherData(client_member);
            contactField.setText(x.getPhone());
            emailField.setText(x.getEmail());
            descricaoMemberTextArea.setText(x.getDescription());
        }else{
            contactField.setPromptText("Contacto...");
            emailField.setPromptText("E-mail...");
            descricaoMemberTextArea.setPromptText("Descrição...");
        }
    }
    private void fetchData() {
        Thread thread = new Thread(() -> {
            try {
                List<ClientModel> clientResult = build.getClients();
                Platform.runLater(() -> {
                    listaClientes.clear();
                    listaClientes.addAll(clientResult);
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
