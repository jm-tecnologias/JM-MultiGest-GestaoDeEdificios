/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.visitor;

import jmmultigest.apartments.ApartmentModel;
import jmmultigest.member.MemberModel;

/**
 *
 * @author JM-Tecnologias
 */
public class VisitorMemberApartmentModel {
    private VisitorModel visitor;
    private MemberModel member;
    private ApartmentModel apartment;

    public VisitorMemberApartmentModel() {
    }

    public VisitorMemberApartmentModel(VisitorModel visitor, MemberModel member, ApartmentModel apartment) {
        this.visitor = visitor;
        this.member = member;
        this.apartment = apartment;
    }

    public VisitorModel getVisitor() {
        return visitor;
    }

    public void setVisitor(VisitorModel visitor) {
        this.visitor = visitor;
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
