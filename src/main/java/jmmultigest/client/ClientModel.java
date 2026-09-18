/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.client;

import java.util.List;

/**
 *
 * @author JM-Tecnologias
 */
public class ClientModel {
    private int id;
    private int user_id;
    private String surname;
    private int num_members;
    private String description;
    private int building_id;
    private boolean status;
    private List<MemberModel> members;
    private String created_at;
    private String updated_at;

    public ClientModel() {
    }

    public ClientModel(int user_id, String surname, int num_members, 
            String description, int building_id, boolean status, List<MemberModel> members) {
        this.user_id = user_id;
        this.surname = surname;
        this.num_members = num_members;
        this.description = description;
        this.building_id = building_id;
        this.status = status;
        this.members = members;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getNum_members() {
        return num_members;
    }

    public void setNum_members(int num_members) {
        this.num_members = num_members;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(int building_id) {
        this.building_id = building_id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<MemberModel> getMembers() {
        return members;
    }

    public void setMembers(List<MemberModel> members) {
        this.members = members;
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
