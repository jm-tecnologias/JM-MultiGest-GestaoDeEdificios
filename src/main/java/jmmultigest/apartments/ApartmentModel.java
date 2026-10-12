/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.apartments;

/**
 *
 * @author JM-Tecnologias
 */
public class ApartmentModel {
    private int id;
    private String floor;
    private String number;
    private String type;
    private String description;
    private String occupation_state;
    private int building_id;
    private boolean status;
    private String created_at;
    private String updated_at;

    public ApartmentModel() {
    }

    public ApartmentModel(String floor, String number, String type, String description, int building_id) {
        this.floor = floor;
        this.number = number;
        this.type = type;
        this.description = description;
        this.building_id = building_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOccupation_state() {
        return occupation_state;
    }

    public void setOccupation_state(String occupation_state) {
        this.occupation_state = occupation_state;
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
