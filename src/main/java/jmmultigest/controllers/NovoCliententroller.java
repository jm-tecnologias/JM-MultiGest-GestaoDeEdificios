/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import jmmultigest.auth.LoginTokenModel;
import jmmultigest.auth.UserEmployeeModel;
import jmmultigest.building.BuildModel;
import jmmultigest.client.ClientModel;
import jmmultigest.client.ClientService;
import jmmultigest.member.MemberModel;
import jmmultigest.member.MemberService;
import jmmultigest.historic.HistoricModel;
import jmmultigest.historic.HistoricService;
import jmmultigest.utils.ApiException;
import jmmultigest.utils.DataTransfer;
import jmmultigest.utils.ErroUtil;
import jmmultigest.utils.LoadAndMoveUtilities;

/**
 *
 * @author JM-Tecnologias
 */
public class NovoCliententroller implements Initializable{
    private LoginTokenModel user;
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
        setDataToEdit();
        clientService = new ClientService();
        memberService = new MemberService();
        historicService = new HistoricService();
      
        
    }
    @FXML
    public void close(ActionEvent event) {
                    DataTransfer.setOtherData(null);
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
    void setDataToEdit(){
        if(DataTransfer.getOtherData() != null &&
                DataTransfer.getOtherData() instanceof ClientModel){
            ClientModel x = (ClientModel) DataTransfer.getOtherData();
             MemberModel m = x.getMembers().get(0);

            nomeField.setText(m.getName());
            apelidoField.setText(m.getSurname());
            clientObservacoesArea.setText(x.getDescription());
            agregadoField.setText(x.getNum_members()+"");

            telefoneField.setText(m.getPhone());
            emailField.setText(m.getEmail());
            docIDField.setText(m.getDoc_id());
            for(ApartmentModel ap :apartamentMap.values()){
                if(ap.getId() == m.getApartment_id()){
                    apartamentoCombo.setValue(ap.getNumber());
                }
            }

            if(x.getHistorical().get(0).getCreated_at().split("\\.")[0] == null ? m.getCreated_at().split("\\.")[0] == null : x.getHistorical().get(0).getCreated_at().split("\\.")[0].equals(m.getCreated_at().split("\\.")[0])){
                entradaDatePicker.setValue(LocalDate.parse(x.getHistorical().get(0).getOccupational_date()));
                saidaDatePicker.setValue(LocalDate.parse(x.getHistorical().get(0).getFinal_date()));
            }
            historicoObservacoesArea.setText(x.getHistorical().get(0).getDescription());
        }
    }
    
    @FXML
    void chooseAction(ActionEvent e){
        ClientModel cli = (ClientModel) DataTransfer.getOtherData();
        if(cli != null && 
               cli instanceof ClientModel){
            updateClient(cli);
        }else{
            /*
            Pode ser melhorado:
            Definir o periodo de execucao da funcao para 
            certo intervalo de segundos
            por exemplo, a funcao so pode ser executado do intervalo
            hh:mm:05s a hh:mm:55s da mesma hora e minutos
            */
            saveHistorico();
        }
    }
    
    
    void saveHistorico() {
        MemberModel member = saveMember();
        HistoricModel historic;
        try {
            historic = new HistoricModel();
            historic.setClient_id(member.getClient_id());
            historic.setUser_id(user.getUser().getUserModel().getId());
            historic.setApartment_id(member.getApartment_id());
            historic.setOccupational_date(String.valueOf((entradaDatePicker.getValue() == null ? "" : entradaDatePicker.getValue())));
            historic.setFinal_date(String.valueOf((saidaDatePicker.getValue() == null ? "2626-01-01" : saidaDatePicker.getValue())));
            historic.setNum_members(Integer.parseInt(agregadoField.getText()));
            historic.setDescription(historicoObservacoesArea.getText());
            historicService.create(historic);
        } catch (ApiException ex) {
            ErroUtil.showError("Erro ao registar historico ocupacional do Cliente",ex.getMessage());
        } catch (NumberFormatException ex) {
            ErroUtil.showError(
                "Dados inválidos",
                "O número de membros deve ser um valor numérico.");
        } catch (IOException ex) {
            ErroUtil.showError(
                "Erro de comunicação",
                "Não foi possível comunicar com a API.");
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            ErroUtil.showError(
                "Operação interrompida",
                "A comunicação com o servidor foi interrompida.");
        }
    }
    
    void updateHistorico(MemberModel member) {
//        MemberModel member = saveMember();
        HistoricModel historic;
        try {
            historic = new HistoricModel();
            historic.setClient_id(member.getClient_id());
            historic.setUser_id(user.getUser().getUserModel().getId());
            historic.setApartment_id(member.getApartment_id());
            historic.setOccupational_date(String.valueOf((entradaDatePicker.getValue() == null ? "" : entradaDatePicker.getValue())));
            historic.setFinal_date(String.valueOf((saidaDatePicker.getValue() == null ? "2626-01-01" : saidaDatePicker.getValue())));
            historic.setNum_members(Integer.parseInt(agregadoField.getText()));
            historic.setDescription(historicoObservacoesArea.getText());
            historicService.update(member.getCreated_at(), historic);
        } catch (ApiException ex) {
            ErroUtil.showError("Erro ao historico ocupacional do Cliente",ex.getMessage());
        } catch (NumberFormatException ex) {
            ErroUtil.showError(
                "Dados inválidos",
                "O número de membros deve ser um valor numérico.");
        } catch (IOException ex) {
            ErroUtil.showError(
                "Erro de comunicação",
                "Não foi possível comunicar com a API.");
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            ErroUtil.showError(
                "Operação interrompida",
                "A comunicação com o servidor foi interrompida.");
        }
    }
    
    ClientModel saveClient(){
        ClientModel client;
        ClientModel model = new ClientModel();
        try {
            client = new ClientModel();
            client.setUser_id(user.getUser().getUserModel().getId());
            client.setSurname(apelidoField.getText()+", "+nomeField.getText());
            client.setDescription(clientObservacoesArea.getText());
            client.setNum_members(Integer.parseInt(agregadoField.getText()));
            client.setBuilding_id(build.getId());
            model = clientService.create(client);
        } catch (ApiException ex) {
            ErroUtil.showError("Erro ao registar o Cliente",ex.getMessage());
        } catch (NumberFormatException ex) {
            ErroUtil.showError(
                "Dados inválidos",
                "O número de membros deve ser um valor numérico.");
        } catch (IOException ex) {
            ErroUtil.showError(
                "Erro de comunicação",
                "Não foi possível comunicar com a API.");
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            ErroUtil.showError(
                "Operação interrompida",
                "A comunicação com o servidor foi interrompida.");
        }
        return model;
    }
    
    ClientModel updateClient(ClientModel cli){
        ClientModel client;
        ClientModel model = new ClientModel();
        try {
            client = new ClientModel();
            client.setUser_id(user.getUser().getUserModel().getId());
            client.setSurname(apelidoField.getText()+", "+nomeField.getText());
            client.setDescription(clientObservacoesArea.getText());
            client.setNum_members(Integer.parseInt(agregadoField.getText()));
            client.setBuilding_id(build.getId());
            client.setStatus(cli.isStatus());
            model = clientService.update(cli.getId(), client);
            // To get the historical of the client
            DataTransfer.setOtherData(model);
        } catch (ApiException ex) {
            ErroUtil.showError("Erro ao registar o Cliente",ex.getMessage());
        } catch (NumberFormatException ex) {
            ErroUtil.showError(
                "Dados inválidos",
                "O número de membros deve ser um valor numérico.");
        } catch (IOException ex) {
            ErroUtil.showError(
                "Erro de comunicação",
                "Não foi possível comunicar com a API.");
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            ErroUtil.showError(
                "Operação interrompida",
                "A comunicação com o servidor foi interrompida.");
        }
        updateMember(model);
        return model;
    }
    
    MemberModel saveMember(){
        ClientModel client = saveClient();
        ApartmentModel ap = apartamentMap.get(apartamentoCombo.getValue());
        MemberModel member;
        MemberModel model = new MemberModel();
        try {
            member = new MemberModel();
            member.setUser_id(user.getUser().getUserModel().getId());
            member.setClient_id(client.getId());
            member.setDoc_id(docIDField.getText());
            member.setApartment_id(ap.getId());  
            member.setSurname(apelidoField.getText());
            member.setName(nomeField.getText());
            member.setPhone(telefoneField.getText());
            member.setEmail(emailField.getText());
            member.setClient_relationship("Parent");
            member.setDescription(clientObservacoesArea.getText()+"(Primeiro membro)");
            model = memberService.create(member);
        } catch (ApiException ex) {
            ErroUtil.showError("Erro ao registar o Cliente(Fase 2 - Membro)",ex.getMessage());
        } catch (IOException ex) {
            ErroUtil.showError(
                "Erro de comunicação",
                "Não foi possível comunicar com a API.");
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            ErroUtil.showError(
                "Operação interrompida",
                "A comunicação com o servidor foi interrompida.");
        }
        return model;
    }
    
    MemberModel updateMember(ClientModel cli){
//        ClientModel client = updateClient(cli);
        ApartmentModel ap = apartamentMap.get(apartamentoCombo.getValue());
        MemberModel member;
        MemberModel model = new MemberModel();
        try {
            member = new MemberModel();
            member.setUser_id(user.getUser().getUserModel().getId());
            member.setClient_id(cli.getId());
            member.setDoc_id(docIDField.getText());
            member.setApartment_id(ap.getId());  
            member.setSurname(apelidoField.getText());
            member.setName(nomeField.getText());
            member.setPhone(telefoneField.getText());
            member.setEmail(emailField.getText());
            member.setClient_relationship("Parent");
            member.setStatus(cli.getMembers().get(0).isStatus());
            member.setToken(cli.getMembers().get(0).getToken());
            member.setDescription(clientObservacoesArea.getText());
            model = memberService.update(cli.getMembers().get(0).getId(), member);
        } catch (ApiException ex) {
            ErroUtil.showError("Erro ao registar o Cliente(Fase 2 - Membro)",ex.getMessage());
        } catch (IOException ex) {
            ErroUtil.showError(
                "Erro de comunicação",
                "Não foi possível comunicar com a API.");
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            ErroUtil.showError(
                "Operação interrompida",
                "A comunicação com o servidor foi interrompida.");
        }
        //Update historic
        updateHistorico(model);
        return model;
    }
    
}
