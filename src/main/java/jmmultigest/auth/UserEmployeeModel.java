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
    private UserModel userModel;
    private EmployeeModel employeeModel;

    public UserEmployeeModel() {
    }

    public UserEmployeeModel(UserModel userModel, EmployeeModel employeeModel) {
        this.userModel = userModel;
        this.employeeModel = employeeModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public EmployeeModel getEmployeeModel() {
        return employeeModel;
    }

    public void setEmployeeModel(EmployeeModel employeeModel) {
        this.employeeModel = employeeModel;
    }
    
    
}
