/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.controllers;

import java.io.IOException;
import java.net.URL;
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
        try{
            memberService = new MemberService();
        } catch (IOException ex) {
            System.out.println("Erro (IOException) ao inicializar servico: "+ex);
        } catch (InterruptedException ex) {
            System.out.println("Erro (InterruptedException) ao inicializar servico: "+ex);
        }
    }
    
    @FXML
    public void close(ActionEvent event) {
      if (LoadAndMoveUtilities.returnToBaseAnchor()) {
          DataTransfer.setOtherData(null);
          LoadAndMoveUtilities.setEstadoPopUP(false);
          LoadAndMoveUtilities.showAsPopUP(null, null);
          ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
      }
    }
    
    void populateCombos(){
        membroRelacaoCombo.setItems(FXCollections.observableArrayList("Pai","Mãe","Filho(a)","Esposo(a)", "Cunhado(a)","Sobrinho(a)","Avó", "Avô", "Neto(a)"));
    }
    
    @FXML
    void saveMember(ActionEvent e){
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
            ErroUtil.showError("Não foi possível registar o Membro",ex.getMessage());
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
}
