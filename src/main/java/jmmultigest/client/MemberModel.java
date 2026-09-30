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
public class MemberModel {
    private int id;
    private int user_id;
    private int client_id;
    private int apartment_id;
    private String doc_id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String client_relationship;
    private String description;
    private boolean status;
    private String token;
    private String profile_pic_url;
    private String barcode_url;
    private List<VisitorModel> visitors;
    private List<MemberCheckInOutModel> checkedStatus;
    private String created_at;
    private String updated_at;

    public MemberModel() {
    }

    public MemberModel(int user_id, int client_id, int apartment_id, 
            String doc_id, String name, String surname, String phone,
            String email, String description, String client_relationship
    ) {
        this.user_id = user_id;
        this.client_id = client_id;
        this.apartment_id = apartment_id;
        this.doc_id = doc_id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.client_relationship = client_relationship;
        this.description = description;
//        this.visitors = visitors;
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

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getApartment_id() {
        return apartment_id;
    }

    public void setApartment_id(int apartment_id) {
        this.apartment_id = apartment_id;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClient_relationship() {
        return client_relationship;
    }

    public void setClient_relationship(String client_relationship) {
        this.client_relationship = client_relationship;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProfile_pic_url() {
        return profile_pic_url;
    }

    public void setProfile_pic_url(String profile_pic_url) {
        this.profile_pic_url = profile_pic_url;
    }

    public String getBarcode_url() {
        return barcode_url;
    }

    public void setBarcode_url(String barcode_url) {
        this.barcode_url = barcode_url;
    }

    public List<VisitorModel> getVisitors() {
        return visitors;
    }

    public void setVisitors(List<VisitorModel> visitors) {
        this.visitors = visitors;
    }

    public List<MemberCheckInOutModel> getCheckedStatus() {
        return checkedStatus;
    }

    public void setCheckedStatus(List<MemberCheckInOutModel> checkedStatus) {
        this.checkedStatus = checkedStatus;
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
