/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.utils;

import jmmultigest.auth.UserEmployeeModel;
import jmmultigest.building.BuildModel;

/**
 *
 * @author JM-Tecnologias
 */
public class DataTransfer {
    private static UserEmployeeModel userData;
    private static BuildModel buildingData;
    private static Object otherData;

    public static UserEmployeeModel getUserData() {
        return userData;
    }

    public static void setUserData(UserEmployeeModel userData) {
        DataTransfer.userData = userData;
    }

    public static BuildModel getBuildingData() {
        return buildingData;
    }

    public static void setBuildingData(BuildModel buildingData) {
        DataTransfer.buildingData = buildingData;
    }

    public static Object getOtherData() {
        return otherData;
    }

    public static void setOtherData(Object otherData) {
        DataTransfer.otherData = otherData;
    }
    
    
}
