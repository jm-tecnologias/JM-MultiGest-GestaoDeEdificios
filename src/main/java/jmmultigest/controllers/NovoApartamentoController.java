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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jmmultigest.apartments.ApartmentModel;
import jmmultigest.apartments.ApartmentService;
import jmmultigest.auth.LoginTokenModel;
import jmmultigest.auth.UserEmployeeModel;
import jmmultigest.building.BuildModel;
import jmmultigest.utils.ApiException;
import jmmultigest.utils.DataTransfer;
import jmmultigest.utils.ErroUtil;
import jmmultigest.utils.LoadAndMoveUtilities;

/**
 *
 * @author JM-Tecnologias
 */
public class NovoApartamentoController implements Initializable{
    private ApartmentService apartmentService;
    private LoginTokenModel user;
    private BuildModel build;
    
    @FXML private TextField apartamentoAndarField;
    @FXML private TextArea apartamentoDescricaoArea;
    @FXML private TextField apartamentoNumeroField;
    @FXML private ComboBox<String> apartamentoTipoCombo;

    
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user = DataTransfer.getUserData();
        build = DataTransfer.getBuildingData();
        apartmentService = new ApartmentService();
        apartamentoTipoCombo.setItems(FXCollections.observableArrayList("Tipo 1","Tipo 2","Tipo 3","Tipo 4","Tipo 5"));
        setDataToEdit();
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
    
    
    @FXML
    void chooseAction(ActionEvent e){
        if(DataTransfer.getOtherData() != null && 
                DataTransfer.getOtherData() instanceof ApartmentModel){
            updateApartment();
        }else{
            createApartment();           
        }
    }
    
    
    void createApartment(){
        try{
            apartmentService.create(new ApartmentModel(apartamentoAndarField.getText(), 
                    apartamentoNumeroField.getText(), apartamentoTipoCombo.getValue(), 
                    apartamentoDescricaoArea.getText(), build.getId()));
        } catch (ApiException ex) {
            ErroUtil.showError("Erro ao Registar Apartamento",ex.getMessage());
        } catch (NumberFormatException ex) {
            ErroUtil.showError(
                "Dados inválidos",
                "* Mensagem por ser melhorada...");
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
    
    
    void updateApartment(){
        ApartmentModel model = (ApartmentModel)DataTransfer.getOtherData();
        
        try{
            ApartmentModel ap = new ApartmentModel();
            ap.setFloor(apartamentoAndarField.getText());
            ap.setNumber(apartamentoNumeroField.getText());
            ap.setDescription(apartamentoDescricaoArea.getText());
            ap.setType(apartamentoTipoCombo.getValue());
            ap.setBuilding_id(build.getId());
            ap.setId(model.getId());
            ap.setOccupation_state(model.getOccupation_state());
            ap.setStatus(model.isStatus());
//            ap.setUpdated_at(model.getUpdated_at());
            apartmentService.update(model.getId(), ap);
        } catch (ApiException ex) {
            ErroUtil.showError("Erro ao actualizar Apartamento",ex.getMessage());
        } catch (NumberFormatException ex) {
            ErroUtil.showError(
                "Dados inválidos",
                "* Mensagem por ser melhorada...");
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
    
    void setDataToEdit(){
        if(DataTransfer.getOtherData() != null){
            if(DataTransfer.getOtherData() instanceof ApartmentModel){
                    ApartmentModel ap = (ApartmentModel) DataTransfer.getOtherData();
                    apartamentoAndarField.setText(ap.getFloor());
                    apartamentoNumeroField.setText(ap.getNumber());
                    apartamentoTipoCombo.setValue(ap.getType());
                    apartamentoDescricaoArea.setText(ap.getDescription());
            }
        }
    }
    
}
