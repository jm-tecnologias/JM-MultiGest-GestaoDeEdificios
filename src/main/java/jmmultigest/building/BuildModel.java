/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.building;

import jmmultigest.building.contact.ContactBuildingModel;
import java.util.List;
import jmmultigest.apartments.ApartmentModel;
import jmmultigest.client.ClientModel;
import jmmultigest.department.DepartmentModel;
import jmmultigest.employee.EmployeeModel;
import jmmultigest.historic.HistoricModel;

/**
 *
 * @author JM-Tecnologias
 */
public class BuildModel {
    private int id;
    private String name;
    private String description;
    private boolean status;
    private List<ContactBuildingModel> contacts;
    private List<DepartmentModel> departments;
    private List<EmployeeModel> employees;
    private List<ApartmentModel> apartments;
    private List<ClientModel> clients;
    private String created_at;
    private String updated_at;

    public BuildModel() {
    }

    public BuildModel(String name, String description, boolean status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<ContactBuildingModel> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactBuildingModel> contacts) {
        this.contacts = contacts;
    }

    public List<DepartmentModel> getDepartments() {
        return departments;
    }

    public void setDepartments(List<DepartmentModel> departments) {
        this.departments = departments;
    }

    public List<EmployeeModel> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeModel> employees) {
        this.employees = employees;
    }

    public List<ApartmentModel> getApartments() {
        return apartments;
    }

    public void setApartments(List<ApartmentModel> apartments) {
        this.apartments = apartments;
    }

    public List<ClientModel> getClients() {
        return clients;
    }

    public void setClients(List<ClientModel> clients) {
        this.clients = clients;
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
