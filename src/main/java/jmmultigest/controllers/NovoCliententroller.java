/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
import jmmultigest.client.ClientModel;
import jmmultigest.client.ClientService;
import jmmultigest.client.MemberModel;
import jmmultigest.client.MemberService;
import jmmultigest.historic.HistoricModel;
import jmmultigest.historic.HistoricService;
import jmmultigest.utils.ApiException;
import jmmultigest.utils.DataTransfer;
import jmmultigest.utils.LoadAndMoveUtilities;

/**
 *
 * @author JM-Tecnologias
 */
public class NovoCliententroller implements Initializable{
    private UserEmployeeModel user;
    private BuildModel build;
    private ClientService clientService;
    private MemberService memberService;
    private HistoricService historicService;
    
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
        populateCombos();
        try {
            clientService = new ClientService();
            memberService = new MemberService();
            historicService = new HistoricService();
        } catch (IOException ex) {
            System.out.println("Erro (IOException) ao inicializar servico: "+ex);
        } catch (InterruptedException ex) {
            System.out.println("Erro (InterruptedException) ao inicializar servico: "+ex);
        }
        
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
    
    void populateCombos(){
        List<String> lista = new ArrayList<>();
        apartamentMap = new HashMap<>();
        for(ApartmentModel ap : build.getApartments()){
            lista.add(ap.getNumber());
            apartamentMap.put(ap.getNumber(), ap);
        }
        apartamentoCombo.setItems(FXCollections.observableArrayList(lista));
        estadoCombo.setItems(FXCollections.observableArrayList("Livre","Ocupado"));
    }
    
    @FXML
    void save(ActionEvent e) throws IOException, JsonProcessingException, InterruptedException{
        MemberModel member = saveMember();
        HistoricModel historic;
        try {
            historic = new HistoricModel();
            historic.setClient_id(member.getClient_id());
            historic.setUser_id(user.getUser().getId());
            historic.setApartment_id(member.getApartment_id());
            historic.setOccupational_date(String.valueOf((entradaDatePicker.getValue() == null ? "" : entradaDatePicker.getValue())));
            historic.setFinal_date(String.valueOf((saidaDatePicker.getValue() == null ? "2626-01-01" : saidaDatePicker.getValue())));
            historic.setNum_members(Integer.parseInt(agregadoField.getText()));
            historic.setDescription(historicoObservacoesArea.getText());
            historicService.create(historic);
        } catch (IOException ex) {
            System.out.println("Erro (IOException) ao registar Historico Ocupacional: "+ex);
        } catch (InterruptedException ex) {
            System.out.println("Erro (InterruptedException) ao registar Historico Ocupacional: "+ex);
        }
        
    }
    
    ClientModel saveClient(){
        ClientModel client;
        ClientModel model = new ClientModel();
        try {
            client = new ClientModel();
            client.setUser_id(user.getUser().getId());
            client.setSurname(apelidoField.getText()+", "+nomeField.getText());
            client.setDescription(clientObservacoesArea.getText());
            client.setNum_members(Integer.parseInt(agregadoField.getText()));
            client.setBuilding_id(build.getId());
            model = clientService.create(client);
        } catch (IOException ex) {
            System.out.println("Erro (IOException) ao registar cliente: "+ex);
        } catch (InterruptedException ex) {
            System.out.println("Erro (InterruptedException) ao registar cliente: "+ex);
        }
        return model;
    }
    
    MemberModel saveMember() throws IOException, JsonProcessingException, InterruptedException{
        ClientModel client = saveClient();
        ApartmentModel ap = apartamentMap.get(apartamentoCombo.getValue());
        MemberModel member;
        MemberModel model = new MemberModel();
        try {
            member = new MemberModel();
            member.setUser_id(user.getUser().getId());
            member.setClient_id(client.getId());
            member.setDoc_id(docIDField.getText());
            member.setApartment_id(ap.getId());  
            member.setSurname(apelidoField.getText());
            member.setName(nomeField.getText());
            member.setPhone(telefoneField.getText());
            member.setEmail(emailField.getText());
            member.setDescription(clientObservacoesArea.getText()+"(Primeiro membro)");
            model = memberService.create(member);
        } catch (ApiException ex) {
            System.out.println("Não foi possível registar o Membro: " + ex.getMessage());
        
        } catch (IOException ex) {
            System.out.println("Erro (IOException) ao registar membro: "+ex);
        } catch (InterruptedException ex) {
            System.out.println("Erro (InterruptedException) ao registar membro: "+ex);
        }
        return model;
    }
    
}
