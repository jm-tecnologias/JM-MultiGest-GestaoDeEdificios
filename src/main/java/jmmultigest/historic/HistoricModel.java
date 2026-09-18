/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.historic;

/**
 *
 * @author JM-Tecnologias
 */
public class HistoricModel {
    private int client_id;
    private int user_id;
    private int apartment_id;
    private int num_member;
    private String occupational_date;
    private String final_date;
    private String description;
    private String created_at;
    private String updated_at;

    public HistoricModel() {
    }

    public HistoricModel(int client_id, int user_id, int apartment_id, int num_member, String occupational_date, String final_date, String description) {
        this.client_id = client_id;
        this.user_id = user_id;
        this.apartment_id = apartment_id;
        this.num_member = num_member;
        this.occupational_date = occupational_date;
        this.final_date = final_date;
        this.description = description;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getApartment_id() {
        return apartment_id;
    }

    public void setApartment_id(int apartment_id) {
        this.apartment_id = apartment_id;
    }

    public int getNum_member() {
        return num_member;
    }

    public void setNum_member(int num_member) {
        this.num_member = num_member;
    }

    public String getOccupational_date() {
        return occupational_date;
    }

    public void setOccupational_date(String occupational_date) {
        this.occupational_date = occupational_date;
    }

    public String getFinal_date() {
        return final_date;
    }

    public void setFinal_date(String final_date) {
        this.final_date = final_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
