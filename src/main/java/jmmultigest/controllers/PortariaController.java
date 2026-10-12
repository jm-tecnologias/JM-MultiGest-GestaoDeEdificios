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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import jmmultigest.auth.LoginTokenModel;
import jmmultigest.auth.UserEmployeeModel;
import jmmultigest.building.BuildModel;
import jmmultigest.checkINOUT.MemberCheckInOutModel;
import jmmultigest.checkINOUT.MemberCheckInOutService;
import jmmultigest.checkINOUT.VisitorCheckInOutModel;
import jmmultigest.checkINOUT.VisitorCheckInOutService;
import jmmultigest.member.MemberApartmentModel;
import jmmultigest.member.MemberModel;
import jmmultigest.member.MemberService;
import jmmultigest.utils.ApiException;
import jmmultigest.utils.DataTransfer;
import jmmultigest.utils.ErroUtil;
import jmmultigest.utils.LeitorSerialListener;
import jmmultigest.visitor.VisitorMemberApartmentModel;
import jmmultigest.visitor.VisitorModel;
import jmmultigest.visitor.VisitorService;

/**
 *
 * @author JM-Tecnologias
 */
public class PortariaController implements Initializable {

    private LoginTokenModel user;
    private BuildModel build;
    private MemberModel member;
    private VisitorModel visitor;
    private MemberService memberService;
    private VisitorService visitorService;
    private MemberCheckInOutService memberCheckInOutService;
    private VisitorCheckInOutService visitorCheckInOutService;
    private MemberApartmentModel memberResponse;
    private VisitorMemberApartmentModel visitorResponse;
    private ObservableList<MemberCheckInOutModel> listaMembroChekInOut;
    private ObservableList<VisitorCheckInOutModel> listaVisitantes;
    private LeitorSerialListener leitor;

    @FXML
    private ImageView imageCode_or_ProfileImage;

    @FXML
    private Label flagLabel;

    /*
    *
    * 
     */
    @FXML
    private TextField nomeFieldMembro;
    @FXML
    private TextField telefoneFieldMembro;
    @FXML
    private TextField emailFieldMembro;
    @FXML
    private Label estadoLabelMembro;

    @FXML
    private TextField nomeFieldVisitante;
    @FXML
    private TextField dataFieldVisitante;
    @FXML
    private TextField horaFieldVisitante;
    @FXML
    private TextField telefoneFieldVisitante;
    @FXML
    private Label estadoLabelVisitante;
    @FXML
    private TextArea descricaoVisitanteField;

    @FXML
    private TextArea notasField;

    /*
    *
    * 
     */
    @FXML
    private TextField scanField;

    /*
    *
    * 
     */
    @FXML
    private TextField pisoBlocoField;
    @FXML
    private TextField numeroField;
    @FXML
    private TextArea descricaoField;

    /*
    *
    * 
     */
    @FXML
    private TableView<MemberCheckInOutModel> membroTabela;
    @FXML
    private TableColumn<MemberCheckInOutModel, String> estadoColumnMembro;
    @FXML
    private TableColumn<MemberCheckInOutModel, String> notasColumnMembro;

    /*
    *
    * 
     */
    @FXML
    private TableView<VisitorCheckInOutModel> visitanteTabela;
    @FXML
    private TableColumn<VisitorCheckInOutModel, String> estadoColumnVisitante;
    @FXML
    private TableColumn<VisitorCheckInOutModel, String> notasColumnVisitante;

    /*
    *
    * 
     */
    @FXML
    private TextField notaHistoricoField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user = DataTransfer.getUserData();
        build = DataTransfer.getBuildingData();
        memberService = new MemberService();
        visitorService = new VisitorService();
        memberCheckInOutService = new MemberCheckInOutService();
        visitorCheckInOutService = new VisitorCheckInOutService();
        setTableWidth();

        leitor = new LeitorSerialListener("COM3", codigo -> {
            // System.out.println("Código lido via listener: " + codigo);
            // Executar operações da interface na thread JavaFX
            Platform.runLater(() -> {
                scanField.setText(codigo);
                chooseScanAction();
            });
        });

        leitor.iniciar();

    }

    void setTableWidth() {
        estadoColumnMembro.prefWidthProperty()
                .bind(membroTabela.widthProperty().multiply(0.25));
        notasColumnMembro.prefWidthProperty()
                .bind(membroTabela.widthProperty().multiply(0.75));

        estadoColumnVisitante.prefWidthProperty()
                .bind(visitanteTabela.widthProperty().multiply(0.25));
        notasColumnVisitante.prefWidthProperty()
                .bind(visitanteTabela.widthProperty().multiply(0.75));
    }

    @FXML
    void chooseScanAction() {
        String codigo = scanField.getText();

        if (codigo == null || codigo.trim().isEmpty()) {
            ErroUtil.showError(
                    "Leitura inválida",
                    "Nenhum código de barras foi recebido."
            );
            return;
        }

//        String[] partes = codigo.trim().split(" ");
//
//        if (partes.length < 1 || partes[1].trim().isEmpty()) {
//            ErroUtil.showError(
//                    "Código inválido",
//                    "O formato do código de barras não é válido."
//            );
//            return;
//        }

        char tipo = codigo.charAt(0);
        String id = codigo.substring(1);

        switch (tipo) {
            case 'M':
//                member = null;
//                visitor = null;

                flagLabel.setText("ANFITRIÃO");
                notasField.clear();
                notasField.setPromptText("Notas...");

                scanMember(id);
                break;

            case 'V':
//                member = null;
//                visitor = null;

                flagLabel.setText("VISITANTE");
                notasField.clear();
                notasField.setPromptText("Notas...");

                scanVisitor(id);
                break;

            default:
                ErroUtil.showError(
                        "Tipo desconhecido",
                        "O código deve começar por M- ou V-."
                );
                break;
        }
        leitor.parar();
    }

    @FXML
    void chooseCheckInAction(ActionEvent e) {
        String[] idScanned = scanField.getText().split("-");
        if (idScanned[0].toLowerCase().startsWith("m")) {
            saveCheckInMembro();
        }
        if (idScanned[0].toLowerCase().startsWith("v")) {
            saveCheckInVisitor();
        }
    }

    @FXML
    void chooseCheckOutAction(ActionEvent e) {
        String[] idScanned = scanField.getText().split("-");
        if (idScanned[0].toLowerCase().startsWith("m")) {
            saveCheckOutMembro();
        }
        if (idScanned[0].toLowerCase().startsWith("v")) {
            saveCheckOutVisitor();
        }
    }

    void saveCheckInMembro() {
        MemberCheckInOutModel model;
        try {
            model = new MemberCheckInOutModel(member.getId(), user.getUser().getUserModel().getId(),
                    "Check In", notasField.getText());
            memberCheckInOutService.create(model);
        } catch (ApiException ex) {
            ErroUtil.showError("Erro ao Registar Check In do Anfitrião", ex.getMessage());
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
        leitor.iniciar();
    }

    void saveCheckOutMembro() {
        MemberCheckInOutModel model;
        try {
            model = new MemberCheckInOutModel(member.getId(), user.getUser().getUserModel().getId(),
                    "Check Out", notasField.getText());
            memberCheckInOutService.create(model);
        } catch (ApiException ex) {
            ErroUtil.showError("Erro ao Registar Check Out do Anfitrião", ex.getMessage());
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
        leitor.iniciar();
    }

    void saveCheckInVisitor() {
        
        try {
            VisitorCheckInOutModel model = new VisitorCheckInOutModel(visitor.getId(), user.getUser().getUserModel().getId(),
                    "Check In", notasField.getText());
            visitorCheckInOutService.create(model);
        } catch (ApiException ex) {
            ErroUtil.showError("Erro ao Registar Check In do Visitante", ex.getMessage());
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
        leitor.iniciar();
    }

    void saveCheckOutVisitor() {
        VisitorCheckInOutModel model;
        try {
            model = new VisitorCheckInOutModel(visitor.getId(), user.getUser().getUserModel().getId(),
                    "Check Out", notasField.getText());
            visitorCheckInOutService.create(model);
        } catch (ApiException ex) {
            ErroUtil.showError("Erro ao Registar Check Out do Visitante", ex.getMessage());
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
        leitor.iniciar();
    }

    @FXML
    void saveVisitorBreak(ActionEvent e) {
        VisitorCheckInOutModel model;
        try {
            model = new VisitorCheckInOutModel(visitor.getId(), user.getUser().getUserModel().getId(),
                    "Visitante solicitou saida", notasField.getText());
            visitorCheckInOutService.create(model);
        } catch (ApiException ex) {
            ErroUtil.showError("Erro ao registar saida solicitada do Visitante", ex.getMessage());
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
        leitor.iniciar();
    }

    MemberModel scanMember(String id) {

        try {
            memberResponse = memberService.getMemberData(id);
            nomeFieldMembro.setText(memberResponse.getMember().getSurname() + ", " + memberResponse.getMember().getName());
            telefoneFieldMembro.setText(memberResponse.getMember().getPhone());
            emailFieldMembro.setText(memberResponse.getMember().getEmail());
            estadoLabelMembro.setText(memberResponse.getMember().isStatus() == true ? "Activo" : "Desactivo");
            pisoBlocoField.setText("Piso/Bloco " + memberResponse.getApartment().getFloor());
            numeroField.setText(memberResponse.getApartment().getNumber());
            descricaoField.setText(memberResponse.getApartment().getDescription());
            imageCode_or_ProfileImage.setImage(new Image(memberResponse.getMember().getBarcode_url()));

            showMemberCheckInOutHistoric(memberResponse.getMember().getCheckedStatus());
            member = memberResponse.getMember();
        } catch (ApiException ex) {
            ErroUtil.showError("Erro ao Consultar dados do Membro", ex.getMessage());
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
        return member;
    }

    VisitorModel scanVisitor(String id) {
        try {
            visitorResponse = visitorService.getVisitorData(id);
            nomeFieldVisitante.setText(visitorResponse.getVisitor().getName());
            dataFieldVisitante.setText(visitorResponse.getVisitor().getVisitor_date());
            horaFieldVisitante.setText(visitorResponse.getVisitor().getVisitor_time());
            telefoneFieldVisitante.setText(visitorResponse.getVisitor().getPhone());
            estadoLabelVisitante.setText(visitorResponse.getVisitor().getStatus());
            descricaoVisitanteField.setText(visitorResponse.getVisitor().getDescription());

            nomeFieldMembro.setText(visitorResponse.getMember().getSurname() + ", " + visitorResponse.getMember().getName());
            telefoneFieldMembro.setText(visitorResponse.getMember().getPhone());
            emailFieldMembro.setText(visitorResponse.getMember().getEmail());
            estadoLabelMembro.setText(visitorResponse.getMember().isStatus() == true ? "Activo" : "Desactivo");

            pisoBlocoField.setText("Piso/Bloco " + visitorResponse.getApartment().getFloor());
            numeroField.setText(visitorResponse.getApartment().getNumber());
            descricaoField.setText(visitorResponse.getApartment().getDescription());
            imageCode_or_ProfileImage.setImage(new Image(visitorResponse.getVisitor().getBarcode_url()));

            showVisitorCheckInOutHistoric(visitorResponse.getVisitor().getVisitor_check_historic());
            visitor = visitorResponse.getVisitor();
        } catch (ApiException ex) {
            ErroUtil.showError("Erro ao Consultar dados do Membro", ex.getMessage());
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
        return visitor;
    }

    void showMemberCheckInOutHistoric(List<MemberCheckInOutModel> lista) {
        listaMembroChekInOut = FXCollections.observableArrayList(lista);
        estadoColumnMembro.setCellValueFactory(new PropertyValueFactory<>("status"));
        notasColumnMembro.setCellValueFactory(new PropertyValueFactory<>("note"));
        membroTabela.setItems(listaMembroChekInOut);
    }

    void showVisitorCheckInOutHistoric(List<VisitorCheckInOutModel> lista) {
        listaVisitantes = FXCollections.observableArrayList(lista);
        System.out.println("Size: " + listaVisitantes.size());

        estadoColumnVisitante.setCellValueFactory(new PropertyValueFactory<>("status"));
        notasColumnVisitante.setCellValueFactory(new PropertyValueFactory<>("note"));
        visitanteTabela.setItems(listaVisitantes);
    }

    @FXML
    void linhaSelecionadaMembro(MouseEvent me) {
        MemberCheckInOutModel x = membroTabela.getSelectionModel().getSelectedItem();
        if (x != null) {
            notaHistoricoField.setText(x.getNote());
        }
    }

    @FXML
    void linhaSelecionadaVisitante(MouseEvent me) {
        VisitorCheckInOutModel x = visitanteTabela.getSelectionModel().getSelectedItem();
        if (x != null) {
            notaHistoricoField.setText(x.getNote());
        }
    }
}
