/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.auth;

import jmmultigest.employee.EmployeeModel;

/**
 *
 * @author JM-Tecnologias
 */
public class UserEmployeeModel {
    private UserModel user;
    private EmployeeModel employee;

    public UserEmployeeModel() {
    }

    public UserEmployeeModel(UserModel user, EmployeeModel employee) {
        this.user = user;
        this.employee = employee;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public EmployeeModel getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeModel employee) {
        this.employee = employee;
    }
    
    
}
