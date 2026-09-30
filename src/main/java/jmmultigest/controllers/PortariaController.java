/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import jmmultigest.auth.UserEmployeeModel;
import jmmultigest.building.BuildModel;
import jmmultigest.checkINOUT.MemberCheckInOutModel;
import jmmultigest.checkINOUT.VisitorCheckInOutModel;
import jmmultigest.member.MemberApartmentModel;
import jmmultigest.member.MemberService;
import jmmultigest.utils.ApiException;
import jmmultigest.utils.DataTransfer;
import jmmultigest.utils.ErroUtil;
import jmmultigest.visitor.VisitorMemberApartmentModel;
import jmmultigest.visitor.VisitorModel;
import jmmultigest.visitor.VisitorService;

/**
 *
 * @author JM-Tecnologias
 */
public class PortariaController implements Initializable{
    private UserEmployeeModel user;
    private BuildModel build;
    private MemberService memberService;
    private VisitorService visitorService;
    private MemberApartmentModel memberResponse;
    private VisitorMemberApartmentModel visitorResponse;
    private ObservableList<MemberCheckInOutModel> listaMembroChekInOut;
    private ObservableList<VisitorCheckInOutModel> listaVisitantes;
    
    @FXML
    private Label flagLabel;

    /*
    *
    * 
    */  
    @FXML private TextField nomeFieldMembro;
    @FXML private TextField telefoneFieldMembro;
    @FXML private TextField emailFieldMembro;
    @FXML private Label estadoLabelMembro;

    @FXML private TextField nomeFieldVisitante;
    @FXML private TextField dataFieldVisitante;
    @FXML private TextField horaFieldVisitante;
    @FXML private TextField telefoneFieldVisitante;
    @FXML private Label estadoLabelVisitante;
    
    @FXML private TextArea notasField;
    
    /*
    *
    * 
    */ 
    @FXML private TextField scanField;
    
    
    /*
    *
    * 
    */ 
    @FXML private TextField pisoBlocoField;
    @FXML private TextField numeroField;
    @FXML private TextArea descricaoField;
    
    /*
    *
    * 
    */ 
    @FXML private TableView<MemberCheckInOutModel> membroTabela;
    @FXML private TableColumn<MemberCheckInOutModel, String> estadoColumnMembro;
    @FXML private TableColumn<MemberCheckInOutModel, String> notasColumnMembro;

    /*
    *
    * 
    */ 
    @FXML private TableView<VisitorCheckInOutModel> visitanteTabela;
    @FXML private TableColumn<VisitorCheckInOutModel, String> estadoColumnVisitante;
    @FXML private TableColumn<VisitorCheckInOutModel, String> notasColumnVisitante;
    
    /*
    *
    * 
    */ 
    @FXML private TextField notaHistoricoField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user = DataTransfer.getUserData();
        build = DataTransfer.getBuildingData();
        memberService = new MemberService();
        visitorService = new VisitorService();
    }
    @FXML
    void chooseAction(ActionEvent e){
        String[] idScanned = scanField.getText().split("-");
        if(idScanned[0].toLowerCase().startsWith("m")){
            scanMember(Integer.parseInt(idScanned[1]));
            flagLabel.setText("MEMBRO");
        }
        if(idScanned[0].toLowerCase().startsWith("v")){
            scanVisitor(Integer.parseInt(idScanned[1]));
            flagLabel.setText("VISITANTE");
        }
    }
    
    void scanMember(int id){
        try{
            memberResponse = memberService.getMemberData(id);
            nomeFieldMembro.setText(memberResponse.getMember().getSurname()+", "+memberResponse.getMember().getName());
            telefoneFieldMembro.setText(memberResponse.getMember().getPhone());
            emailFieldMembro.setText(memberResponse.getMember().getEmail());
            estadoLabelMembro.setText(memberResponse.getMember().isStatus() == true ? "Activo" : "Desactivo");
            pisoBlocoField.setText("Piso/Bloco "+memberResponse.getApartment().getFloor());
            numeroField.setText(memberResponse.getApartment().getNumber());
            descricaoField.setText(memberResponse.getApartment().getDescription());
            
            showMemberCheckInOutHistoric(memberResponse.getMember().getCheckedStatus());
        } catch (ApiException ex) {
            ErroUtil.showError("Erro ao Consultar dados do Membro",ex.getMessage());
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
    
    void scanVisitor(int id){
        try{
            visitorResponse = visitorService.getVisitorData(id);
            nomeFieldVisitante.setText(visitorResponse.getVisitor().getName());
            dataFieldVisitante.setText(visitorResponse.getVisitor().getVisitor_date());
            horaFieldVisitante.setText(visitorResponse.getVisitor().getVisitor_time());
            telefoneFieldVisitante.setText(visitorResponse.getVisitor().getPhone());
            estadoLabelVisitante.setText(visitorResponse.getVisitor().getStatus());
            
            nomeFieldMembro.setText(visitorResponse.getMember().getSurname()+", "+visitorResponse.getMember().getName());
            telefoneFieldMembro.setText(visitorResponse.getMember().getPhone());
            emailFieldMembro.setText(visitorResponse.getMember().getEmail());
            estadoLabelMembro.setText(visitorResponse.getMember().isStatus() == true ? "Activo" : "Desactivo");
            
            pisoBlocoField.setText("Piso/Bloco "+visitorResponse.getApartment().getFloor());
            numeroField.setText(visitorResponse.getApartment().getNumber());
            descricaoField.setText(visitorResponse.getApartment().getDescription());
            
            showVisitorCheckInOutHistoric(visitorResponse.getVisitor().getVisitor_check_historic());
        } catch (ApiException ex) {
            ErroUtil.showError("Erro ao Consultar dados do Membro",ex.getMessage());
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
    
    void showMemberCheckInOutHistoric(List<MemberCheckInOutModel> lista){
        listaMembroChekInOut = FXCollections.observableArrayList(lista);
        estadoColumnMembro.setCellValueFactory(new PropertyValueFactory<>("status"));
        notasColumnMembro.setCellValueFactory(new PropertyValueFactory<>("note"));
        membroTabela.setItems(listaMembroChekInOut);
    }
    
    void showVisitorCheckInOutHistoric(List<VisitorCheckInOutModel> lista){
        listaVisitantes = FXCollections.observableArrayList(lista);
        System.out.println("Size: "+listaVisitantes.size());
        
        estadoColumnVisitante.setCellValueFactory(new PropertyValueFactory<>("status"));
        notasColumnVisitante.setCellValueFactory(new PropertyValueFactory<>("note"));
        visitanteTabela.setItems(listaVisitantes);
    }
}
