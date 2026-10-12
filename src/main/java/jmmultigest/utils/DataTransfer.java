/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.utils;

import jmmultigest.auth.LoginTokenModel;
import jmmultigest.building.BuildModel;

/**
 *
 * @author JM-Tecnologias
 */
public class DataTransfer {

    private static LoginTokenModel userData;
    private static BuildModel buildingData;
    private static Object otherData;

    public static LoginTokenModel getUserData() {
        return userData;
    }

    public static void setUserData(LoginTokenModel userData) {
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

    public static String getAccessToken() {
        LoginTokenModel userData = getUserData();

        if (userData == null || userData.getAccess_token() == null
                || userData.getAccess_token().isBlank()) {
            throw new IllegalStateException(
                    "Utilizador não autenticado. Faça login novamente."
            );
        }

        return userData.getAccess_token();
    }

}
