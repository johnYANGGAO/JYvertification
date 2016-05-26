package com.johnson.jyvertification.Model;

import java.io.Serializable;

/**
 * Created by johnsmac on 4/11/16.
 */
public class LoginModel extends  BaseBean implements Serializable {
    /*

   "Result": 0,

    * */

    private String Login;
    private String Pwd;
    private int Roles;
    private int Status ;
    private String CompanyID;
    private String ID;

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPwd() {
        return Pwd;
    }

    public void setPwd(String pwd) {
        Pwd = pwd;
    }

    public int getRoles() {
        return Roles;
    }

    public void setRoles(int roles) {
        Roles = roles;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(String companyID) {
        CompanyID = companyID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }



}
