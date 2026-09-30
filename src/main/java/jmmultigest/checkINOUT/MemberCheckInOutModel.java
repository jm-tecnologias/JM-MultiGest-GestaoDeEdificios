/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.checkINOUT;

/**
 *
 * @author JM-Tecnologias
 */
public class MemberCheckInOutModel {
    private int member_id;
    private int user_id;
    private String status;
    private String note;
    private String created_at;
    private String updated_at;

    public MemberCheckInOutModel() {
    }

    public MemberCheckInOutModel(int member_id, int user_id, String status, String note) {
        this.member_id = member_id;
        this.user_id = user_id;
        this.status = status;
        this.note = note;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
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

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
    
    
}
