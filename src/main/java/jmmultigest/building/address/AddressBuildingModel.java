/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.building.address;

/**
 *
 * @author JM-Tecnologias
 */
public class AddressBuildingModel {
    private int id;
    private String province;
    private String city;
    private String nearby_hood;
    private String avenue;
    private int number;
    private int building_id;
    private boolean status;
    private String created_at;
    private String updated_at;

    public AddressBuildingModel() {
    }

    public AddressBuildingModel(String province, String city, String nearby_hood, String avenue, int number, int building_id, boolean status) {
        this.province = province;
        this.city = city;
        this.nearby_hood = nearby_hood;
        this.avenue = avenue;
        this.number = number;
        this.building_id = building_id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNearby_hood() {
        return nearby_hood;
    }

    public void setNearby_hood(String nearby_hood) {
        this.nearby_hood = nearby_hood;
    }

    public String getAvenue() {
        return avenue;
    }

    public void setAvenue(String avenue) {
        this.avenue = avenue;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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
