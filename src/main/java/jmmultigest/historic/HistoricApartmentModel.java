/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.historic;

import jmmultigest.apartments.ApartmentModel;

/**
 *
 * @author JM-Tecnologias
 */
public class HistoricApartmentModel {
    private HistoricModel historic;
    private ApartmentModel apartment;

    public HistoricApartmentModel() {
    }

    public HistoricApartmentModel(HistoricModel historic, ApartmentModel apartment) {
        this.historic = historic;
        this.apartment = apartment;
    }

    public HistoricModel getHistoric() {
        return historic;
    }

    public void setHistoric(HistoricModel historic) {
        this.historic = historic;
    }

    public ApartmentModel getApartment() {
        return apartment;
    }

    public void setApartment(ApartmentModel apartment) {
        this.apartment = apartment;
    }
    
    
}
