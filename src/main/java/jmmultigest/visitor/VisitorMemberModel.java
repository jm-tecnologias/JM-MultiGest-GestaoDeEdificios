/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.visitor;

import jmmultigest.member.MemberModel;

/**
 *
 * @author JM-Tecnologias
 */
public class VisitorMemberModel {
    private VisitorModel visitor;
    private MemberModel member;

    public VisitorMemberModel() {
    }

    public VisitorMemberModel(VisitorModel visitor, MemberModel member) {
        this.visitor = visitor;
        this.member = member;
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
    
    
}
