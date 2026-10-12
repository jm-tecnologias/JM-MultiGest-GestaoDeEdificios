/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmmultigest.auth;

/**
 *
 * @author JM-Tecnologias
 */
public class LoginTokenModel {
    private String access_token;
    private String token_type;

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
    private UserEmployeeModel user;

    public LoginTokenModel() {
    }

    public LoginTokenModel(String access_token, String token_type, UserEmployeeModel user) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.user = user;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public UserEmployeeModel getUser() {
        return user;
    }

    public void setUser(UserEmployeeModel user) {
        this.user = user;
    }
}
