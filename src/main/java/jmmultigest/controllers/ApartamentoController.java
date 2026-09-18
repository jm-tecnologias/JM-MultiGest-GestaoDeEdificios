/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Modality;
import jmmultigest.utils.LoadAndMoveUtilities;

/**
 *
 * @author JM-Tecnologias
 */

public class ApartamentoController {
    @FXML
    public void getView(ActionEvent e) {
        String path = "/jmmultigest/novo-apartamento.fxml";
        LoadAndMoveUtilities.showAsPopUP(e, null);
        LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, path);
    }
}
