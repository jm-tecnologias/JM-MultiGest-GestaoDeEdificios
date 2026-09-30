/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.checkINOUT;

/**
 *
 * @author JM-Tecnologias
 */
public class VisitorCheckInOutModel {
    private int visitor_id;
    private int user_id;
    private String status;
    private String note;
    private String created_at;

    public VisitorCheckInOutModel() {
    }

    public VisitorCheckInOutModel(int visitor_id, int user_id, String status, String note) {
        this.visitor_id = visitor_id;
        this.user_id = user_id;
        this.status = status;
        this.note = note;
    }
    
    

    public int getVisitor_id() {
        return visitor_id;
    }

    public void setVisitor_id(int visitor_id) {
        this.visitor_id = visitor_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    
    
}
