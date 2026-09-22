/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jmmultigest.apartments.ApartmentModel;
import jmmultigest.auth.UserEmployeeModel;
import jmmultigest.building.BuildModel;
import jmmultigest.client.ClientModel;
import jmmultigest.client.MemberModel;
import jmmultigest.client.MemberService;
import jmmultigest.utils.ApiException;
import jmmultigest.utils.DataTransfer;
import jmmultigest.utils.ErroUtil;
import jmmultigest.utils.LoadAndMoveUtilities;

/**
 *
 * @author JM-Tecnologias
 */
public class NovoMembroController implements Initializable{
    private UserEmployeeModel user;
    private BuildModel build;
    private MemberService memberService;

    @FXML private TextField nomeField;
    @FXML private TextField telefoneField;
    @FXML private TextField apelidoField;
    @FXML private TextField docIDField;
    @FXML private TextField emailField;
    @FXML private TextArea membroObservacoesArea;
    @FXML private ComboBox<String> membroRelacaoCombo;
    
    @FXML private Button guardarMembroButton;
    @FXML private Button cancelarMembroButton;

    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateCombos();
        user = DataTransfer.getUserData();
        build = DataTransfer.getBuildingData();
        setDataToEdit();
        try{
            memberService = new MemberService();
        } catch (IOException ex) {
            System.out.println("Erro (IOException) ao inicializar servico: "+ex);
        } catch (InterruptedException ex) {
            System.out.println("Erro (InterruptedException) ao inicializar servico: "+ex);
        }
    }
    
    @FXML
    void chooseAction(ActionEvent e){
        Object[] client_member = (Object[]) DataTransfer.getOtherData();
        ClientModel cli = (ClientModel) client_member[0];
        MemberModel mem = (MemberModel) client_member[1];
        if(cli != null && 
               cli instanceof ClientModel){
            updateMember(cli, mem);
        }else{
           
            saveMember();
        }
    }
    
    void setDataToEdit(){
        if(DataTransfer.getOtherData() != null){
            if(DataTransfer.getOtherData() instanceof Object[]){
                Object[] client_member = (Object[]) DataTransfer.getOtherData();
                if(client_member[0] != null && client_member[1] != null){
                    MemberModel m = (MemberModel) client_member[1];
                    nomeField.setText(m.getName());
                    apelidoField.setText(m.getSurname());
                    telefoneField.setText(m.getPhone());
                    emailField.setText(m.getEmail());
                    docIDField.setText(m.getDoc_id());
                    membroObservacoesArea.setText(m.getDescription());
                    membroRelacaoCombo.setValue(m.getClient_relationship());
                }
            }
            
        }
    }
    
    @FXML
    public void close(ActionEvent event) {
        DataTransfer.setOtherData(null);
      if (LoadAndMoveUtilities.returnToBaseAnchor()) {
          LoadAndMoveUtilities.setEstadoPopUP(false);
          LoadAndMoveUtilities.showAsPopUP(null, null);
          ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
      }
    }
    
    void populateCombos(){
        membroRelacaoCombo.setItems(FXCollections.observableArrayList("Pai","Mãe","Filho(a)","Esposo(a)", "Cunhado(a)","Sobrinho(a)","Avó", "Avô", "Neto(a)"));
    }
    
    
    void saveMember(){
        ClientModel client = (ClientModel) DataTransfer.getOtherData();
        
        MemberModel model;
        try{
            model = new MemberModel();
            model.setUser_id(user.getUser().getId());
            model.setClient_id(client.getId());
            model.setApartment_id(client.getMembers().get(0).getApartment_id());
            model.setDoc_id(docIDField.getText());
            model.setName(nomeField.getText());
            model.setSurname(apelidoField.getText());
            model.setPhone(telefoneField.getText());
            model.setEmail(emailField.getText());
            model.setClient_relationship((membroRelacaoCombo.getValue() == null ? "Parent" : membroRelacaoCombo.getValue()));
            model.setDescription(membroObservacoesArea.getText());
            memberService.create(model);
        } catch (ApiException ex) {
            ErroUtil.showError("Erro ao registar o Membro",ex.getMessage());
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
    
    void updateMember(ClientModel cli, MemberModel mem){
        MemberModel member;
        try {
            member = new MemberModel();
            member.setUser_id(user.getUser().getId());
            member.setClient_id(cli.getId());
            member.setDoc_id(docIDField.getText());
            member.setApartment_id(mem.getApartment_id());  
            member.setSurname(apelidoField.getText());
            member.setName(nomeField.getText());
            member.setPhone(telefoneField.getText());
            member.setEmail(emailField.getText());
            member.setClient_relationship(membroRelacaoCombo.getValue());
            member.setStatus(mem.isStatus());
            member.setToken(mem.getToken());
            member.setDescription(membroObservacoesArea.getText());
            memberService.update(mem.getId(), member);
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
    }
}
