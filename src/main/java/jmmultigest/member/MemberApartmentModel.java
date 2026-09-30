/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.member;

import jmmultigest.apartments.ApartmentModel;

/**
 *
 * @author JM-Tecnologias
 */
public class MemberApartmentModel {
    private MemberModel member;
    private ApartmentModel apartment;

    public MemberApartmentModel() {
    }

    public MemberApartmentModel(MemberModel member, ApartmentModel apartment) {
        this.member = member;
        this.apartment = apartment;
    }

    public MemberModel getMember() {
        return member;
    }

    public void setMember(MemberModel member) {
        this.member = member;
    }

    public ApartmentModel getApartment() {
        return apartment;
    }

    public void setApartment(ApartmentModel apartment) {
        this.apartment = apartment;
    }
    
    
}
